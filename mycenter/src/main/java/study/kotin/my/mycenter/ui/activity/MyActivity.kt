package study.kotin.my.mycenter.ui.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.RegexUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tencent.imsdk.*
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import kotlinx.android.synthetic.main.my_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.injection.commponent.DaggerMyCommponent
import study.kotin.my.mycenter.injection.module.Mymodule
import jsc.kit.datetimepicker.widget.DateTimePicker
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.utils.GifSizeFilter
import study.kotin.my.baselibrary.utils.LocationUtils
import study.kotin.my.baselibrary.utils.SelectSchool
import study.kotin.my.mycenter.common.MyLocationListener
import study.kotin.my.mycenter.persenter.ChangeInfoperserter
import study.kotin.my.mycenter.persenter.view.ChangeInfoview
import java.io.File
import kotlin.collections.HashMap


@Route(path = "/mycenter/MyActivity")
class MyActivity : BaseMVPActivity<ChangeInfoperserter>(), ChangeInfoview, View.OnClickListener {
    var trun = true
    var faceurl = ""
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
    override fun uploadimg(result: BaseResp<String>) {
        hideLoading()
        if (result.success) {
            faceurl = result.message
            Glide.with(this).load(faceurl).into(sss)
        } else {
            Log.e("eeeeeeeeee", result.message)
        }
    }

    override fun Synchronizeinfo(result: BaseResp<String>) {
        hideLoading()
        if (result.success) {
            ARouter.getInstance().build("/App/Homepage").navigation()
            finish()
            toast("修改成功")
        } else {
            toast(result.message)
            Log.e("eeeeeeeeee", result.message)
        }
    }
var datas=0L
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if(data==null){
                return
            }
            showLoading()
            //获取图片路径
            val obtainPathResult = Matisse.obtainPathResult(data)
            val bitmap = BitmapFactory.decodeFile(obtainPathResult.get(0))
            //构筑文件
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.z10 -> {
            }
            R.id.z9 -> {
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
            R.id.z1 -> {
                setdatadialog("修改个性签名", z1_1)
            }
            R.id.z2 -> {
                setdatadialog("修改昵称", z2_1)
            }
            R.id.z3 -> {
                val builder = AlertDialog.Builder(this)
                val view = layoutInflater.inflate(R.layout.sexcheckitem, null)
                val radioGroup = view.find<RadioGroup>(R.id.sexradio)
                builder.setTitle("选择性别")
                builder.setView(view)
                builder.setPositiveButton("确定") { dialog, which ->
                    val checkedRadioButtonId = radioGroup.checkedRadioButtonId
                    val find = view.find<RadioButton>(checkedRadioButtonId)
                    z3_1.text = find.text.toString()
                    dialog!!.dismiss()
                }
                builder.setNegativeButton("取消") { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
                // setdatadialog("修改性别", z3_1)
            }
            R.id.z4 -> {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, 1918)
                calendar.set(Calendar.MONTH, 0)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                val startDate = calendar.time
                val TBuilder = DateTimePicker.Builder(this)
                        .setTitle("选择年月日")
                        .setCancelTextColor(Color.BLACK)
                        .setOkTextColor(resources.getColor(R.color.colorPrimary))
                        .setTitleTextColor(-0x666667)
                        .setSelectedTextColor(resources.getColor(R.color.colorAccent))
                        .setKeepLastSelected(true)
                        .setShowYMDHMLabel(true)
                        .setShowType(DateTimePicker.ShowType.DAY)
                DateTimePicker(this, object : DateTimePicker.ResultHandler {
                    override fun handle(date: Date) {
                        datas=date.time
                        val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
                        val format = simpleDateFormat.format(datas)
                        z4_1.text = format
                    }
                }, startDate, Calendar.getInstance().time, TBuilder).show(Date(946709507000))
            }

            R.id.z5 -> {
                setdatadialog("修改职业", z5_1)
            }
            R.id.z6 -> {
                selectSchool.mPopWindow.showAtLocation(window.decorView, Gravity.CENTER, 0, 0)
            }
            R.id.z7 -> {
                alert {
                    title = "修改邮箱"
                    val editText = EditText(this@MyActivity)
                    customView = editText
                    positiveButton("确定") {
                        if (editText.text.toString() == "") {
                            toast("不能为空")
                            return@positiveButton
                        }
                        if (!RegexUtils.isEmail(editText.text.toString())) {
                            toast("邮箱格式错误")
                            return@positiveButton
                        }
                        z7_1.text = editText.text.toString()
                        it.dismiss()
                    }
                    negativeButton("取消") {
                        it.dismiss()
                    }
                    show()
                }

            }
            R.id.z8 -> {
                setdatadialog("修改个人标签", z8_1)
            }
            R.id.done -> {

                if (z1_1.text == null) {
                    z1_1.text = " "
                }
                if (z2_1.text.toString() == "" || z2_1.text.toString() == "不明") {
                    toast("请填写昵称")
                    return
                }
                if (z6_1.text.toString() == "" || z6_1.text.toString() == "不明") {
                    toast("请填写学校")
                    return
                }
                if (z7_1.text.toString() == "" || z7_1.text.toString() == "不明") {
                    toast("请填写邮箱")
                    return
                }
                showLoading()
                val param = TIMFriendshipManager.ModifyUserProfileParam()
                param.setSelfSignature(z1_1.text.toString())
                param.setNickname(z2_1.text.toString())
                param.setAllowType(TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM)
                if (faceurl != "") {
                    param.setFaceUrl(faceurl)
                }
                when {
                    z3_1.text.toString() == "男" -> param.setGender(TIMFriendGenderType.Male)
                    z3_1.text.toString() == "女" -> param.setGender(TIMFriendGenderType.Female)
                    else -> param.setGender(TIMFriendGenderType.Unknow)
                }
                if(datas!=0L){
                    param.setBirthday(datas/1000)
                }
                val map = HashMap<String, ByteArray>()
                if(Latitude!=""&&Longitude!=""){
                    map.put("Tag_Profile_Custom_lat", Latitude.toByteArray())
                    map.put("Tag_Profile_Custom_Long", Longitude.toByteArray())
                }
                map.put("Tag_Profile_Custom_work", z5_1.text.toString().toByteArray())
                map.put("Tag_Profile_Custom_school", z6_1.text.toString().toByteArray())
                map.put("Tag_Profile_Custom_email", z7_1.text.toString().toByteArray())
                map.put("Tag_Profile_Custom_Label", z8_1.text.toString().toByteArray())
                param.setCustomInfo(map)
                TIMFriendshipManager.getInstance().modifyProfile(param, object : TIMCallBack {
                    override fun onSuccess() {
                        val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                        if (jwt == "") {
                            return
                        }
                        mpersenter.Synchronizeinfo(this@MyActivity,"Bearer " + jwt!!)
                    }

                    override fun onError(p0: Int, p1: String?) {
                        toast("修改失败，请重试")
                        hideLoading()
                    }
                })
            }
            R.id.fh -> finish()

        }

    }

    private fun setdatadialog(name: String, text: TextView) {
        alert {
            title = name
            val editText = EditText(this@MyActivity)
            customView = editText
            positiveButton("确定") {
                if (editText.text.toString() == "") {
                    toast("不能为空")
                    return@positiveButton
                }
                text.text = editText.text.toString()
                it.dismiss()
            }
            negativeButton("取消") {
                it.dismiss()
            }
            show()
        }
    }
    fun loadper() {
        mpersenter.loadProvince()
    }
    fun loadschool(url:String){
        mpersenter.loadschool("http://www.hisihi.com/app.php?s=/school/school/provinceid/$url")
    }

    lateinit var selectSchool: SelectSchool
    var Latitude=""
    var Longitude=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_main)
        initinject()
        val granted = PermissionUtils.isGranted(PermissionConstants.LOCATION)
        if(!granted){
            PermissionUtils.permission(PermissionConstants.LOCATION).request()
        }
//        ActivityCompat.requestPermissions(this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        mpersenter.mView = this
        z1.setOnClickListener(this)
        z2.setOnClickListener(this)
        z3.setOnClickListener(this)
        z4.setOnClickListener(this)
        z5.setOnClickListener(this)
        z6.setOnClickListener(this)
        z7.setOnClickListener(this)
        z8.setOnClickListener(this)
        z9.setOnClickListener(this)
        z10.setOnClickListener(this)
        done.setOnClickListener(this)
        fh.setOnClickListener(this)
        selectSchool = SelectSchool(z6_1, this,{loadper() },{loadschool(selectSchool.provinceId)})
        selectSchool.initPopView()
        z6_1.isCursorVisible = false
        z6_1.isFocusable = false
        z6_1.isFocusableInTouchMode = false
        LocationUtils().getLocation(this,object : MyLocationListener(){
            override fun onLocationChanged(location: Location?) {
                Latitude=location!!.latitude.toString()
                Longitude=location.longitude.toString()
            }

        })
        TIMFriendshipManager.getInstance().getSelfProfile(object : TIMValueCallBack<TIMUserProfile> {
            override fun onSuccess(p0: TIMUserProfile?) {
                if (p0 == null) return
                //     Glide.with(this@MyActivity).load("").preload()
                z1_1.text = p0.selfSignature
                z2_1.text = p0.nickName
                if (p0.faceUrl != "") {
                    val options = RequestOptions()
                            .error(R.drawable.a4_2)
                    Glide.with(this@MyActivity).load(p0.faceUrl).apply(options).into(sss)
                }
                when {
                    p0.gender == TIMFriendGenderType.Male -> z3_1.text = "男"
                    p0.gender == TIMFriendGenderType.Female -> z3_1.text = "女"
                    else -> z3_1.text = "不明"
                }
                val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
                val format = simpleDateFormat.format(p0.birthday*1000)
                z4_1.text = format
                if (p0.customInfo["Tag_Profile_Custom_work"] == null) {
                    z5_1.text = "不明"
                } else {
                    z5_1.text = String(p0.customInfo["Tag_Profile_Custom_work"]!!)
                }
                if (p0.customInfo["Tag_Profile_Custom_school"] == null) {
                    z6_1.setText( "不明")
                } else {
                    z6_1.setText(String(p0.customInfo["Tag_Profile_Custom_school"]!!))
                }
                if (p0.customInfo["Tag_Profile_Custom_email"] == null) {
                    z7_1.text = "不明"
                } else {
                    z7_1.text = String(p0.customInfo["Tag_Profile_Custom_email"]!!)
                }
                if (p0.customInfo["Tag_Profile_Custom_Label"] == null) {
                    z8_1.text = "不明"
                } else {
                    z8_1.text = String(p0.customInfo["Tag_Profile_Custom_Label"]!!)
                }
                if (z2_1.text.toString() == "" || z6_1.text.toString() == "不明" || z7_1.text.toString() == "不明") {
                    fh.isVisible = false
                    changeuser.isVisible=true
                    changeuser.setOnClickListener {
                        ARouter.getInstance().build("/usercenter/RegisterActivity").navigation()
                        finish()
                    }
                    trun = true
                    return
                } else {
                    trun = false
                    fh.isVisible = true
                    changeuser.isVisible=false
                }
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && trun) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun initinject() {
        DaggerMyCommponent.builder().activityCommpoent(activityCommpoent).mymodule(Mymodule()).build().inject(this)
        mpersenter.mView = this
    }

}