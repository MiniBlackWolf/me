package study.kotin.my.address.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.view.get
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.RegexUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tencent.imsdk.TIMGroupManager
import com.tencent.imsdk.TIMGroupMemberInfo
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import kotlinx.android.synthetic.main.addgrouplayout.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.Addresspersenter.view.AddressView
import study.kotin.my.address.R
import study.kotin.my.address.injection.commponent.DaggerAddressCommponent
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.GifSizeFilter
import study.kotin.my.baselibrary.utils.SelectSchool
import java.io.File
import java.util.ArrayList

class AddGroupActivity : BaseMVPActivity<Addresspresenter>(), View.OnClickListener, AddressView {

    override fun loadProvince(result: List<ProvinceList.DataBean>) {
        if (result.isEmpty()) return
        selectSchool.mProvinceListView.removeAllViewsInLayout()
        selectSchool.mProvinceAdapter.data.clear()
        selectSchool.mProvinceAdapter.addData(result)
        selectSchool.mProvinceAdapter.notifyDataSetChanged()
    }

    override fun loadschool(result: List<SchoolList.DataBean>) {
        if (result.isEmpty()) return
        selectSchool.mSchoolListView.removeAllViewsInLayout()
        selectSchool.mSchoolAdapter.data.clear()
        selectSchool.mSchoolAdapter.addData(result)
        selectSchool.mSchoolAdapter.notifyDataSetChanged()
    }


    var headurl = ""
    override fun reslut() {

    }

    override fun uploadimg(result: BaseResp<String>) {
        hideLoading()
        if (result.success) {
            headurl = result.message
        } else {
            Log.e("eeeeeeeeee", result.message)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.grouphead -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(1)
                        .capture(false)
                        .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(study.kotin.my.baselibrary.common.GlideEngine())
                        .forResult(1)
            }
            R.id.newgroupbutton -> {
                if(groupname.text.toString()==""||school.text.toString()==""){
                    toast("学校和社团名不能是空")
                    return
                }
                val createGroupParam = TIMGroupManager.CreateGroupParam("Public", groupname.text.toString())
                createGroupParam.setFaceUrl(headurl)
                createGroupParam.setMaxMemberNum(100)
                createGroupParam.setCustomInfo("school", school.text.toString().toByteArray())
                createGroupParam.setCustomInfo("Authentication", "false".toByteArray())
                val info = TIMGroupMemberInfo(TIMManager.getInstance().loginUser)
                createGroupParam.setMembers(arrayListOf(info))
                TIMGroupManager.getInstance().createGroup(createGroupParam, object : TIMValueCallBack<String> {
                    override fun onSuccess(p0: String?) {
                        toast("创建成功,社团id:$p0")
                        TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(p0), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                            override fun onSuccess(p1: MutableList<TIMGroupDetailInfo>?) {
                                if (p1?.size == null) return
                                val edit = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                                edit.putString(p0 + "Gname", p1.get(0).groupName)
                                edit.putString(p0 + "Gheadurl", p1.get(0).faceUrl)
                                edit.putString(p0 + "Gtype", p1[0].groupType)
                                edit.apply()
                            }

                            override fun onError(p0: Int, p1: String?) {
                            }

                        })
                        finish()
                        ActivityUtils.finishActivity(PublicGroupActivity::class.java)
                        startActivity<PublicGroupActivity>()
                    }

                    override fun onError(p0: Int, p1: String?) {
                        toast("创建失败:$p1")
                    }
                })
            }
            R.id.fh -> {
                finish()
            }
            R.id.school -> {
                val view = window.peekDecorView()
                if (view != null) {
                    val inputmanger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputmanger.hideSoftInputFromWindow(view.windowToken, 0)
                }
                selectSchool.mPopWindow.showAtLocation(window.decorView, Gravity.CENTER, 0, 0)
            }
            R.id.sfzok->{
                if(sfzid.text.toString()==""){
                    toast("身份证不能为空")
                    return
                }
                if(sfzname.text.toString()==""){
                    toast("姓名不能为空")
                    return
                }
//                if(RegexUtils.isIDCard18(sfzname.text.toString())){
//                    toast("身份证格式错误")
//                    return
//                }
                sfzok.setBackgroundResource(R.drawable.nobuttonssss)
                sfzok.isClickable=false
                toast("申请认证成功")
            }
        }
    }

    fun initinject() {
        DaggerAddressCommponent.builder().activityCommpoent(activityCommpoent).addressmodule(Addressmodule()).build().inject(this)
        mpersenter.mView = this
    }

    lateinit var selectSchool: SelectSchool
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addgrouplayout)
        initinject()
        newgroupbutton.setOnClickListener(this)
        grouphead.setOnClickListener(this)
        fh.setOnClickListener(this)
        sfzok.setOnClickListener(this)
        school.setOnClickListener(this)
        school.isCursorVisible = false;
        school.isFocusable = false
        school.isFocusableInTouchMode = false
        selectSchool = SelectSchool(school, this,{loadper() },{loadschool(selectSchool.provinceId)})
        selectSchool.initPopView()

    }

    fun loadper() {
        mpersenter.loadProvince()
    }
    fun loadschool(url:String){
        mpersenter.loadschool("http://www.hisihi.com/app.php?s=/school/school/provinceid/$url")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if(data==null){
                return
            }
            showLoading()
            //获取图片路径
            val obtainPathResult = Matisse.obtainPathResult(data)
            val bitmap = BitmapFactory.decodeFile(obtainPathResult.get(0))
            grouphead.setImageBitmap(bitmap)
            val file = File(obtainPathResult[0])
            val Builder = MultipartBody.Builder()
            Builder.setType(MultipartBody.FORM)
            val create = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val addFormDataPart = Builder.addFormDataPart("file", file.name, create)
            val parts = addFormDataPart.build().parts()
            val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
            mpersenter.uploadimg(this, "Bearer $jwt", parts)
            Log.d("Matisse", "mSelected: $obtainPathResult")
        }
    }


}