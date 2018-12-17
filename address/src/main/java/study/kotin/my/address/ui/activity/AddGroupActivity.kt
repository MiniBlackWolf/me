package study.kotin.my.address.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
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
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.GifSizeFilter
import java.io.File

class AddGroupActivity : BaseMVPActivity<Addresspresenter>(), View.OnClickListener,AddressView {
    var headurl=""
    override fun reslut() {

    }

    override fun uploadimg(result: BaseResp<String>) {
        hideLoading()
        if (result.success) {
            headurl=result.message
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
                        .imageEngine( study.kotin.my.baselibrary.common.GlideEngine())
                        .forResult(1)
            }
            R.id.newgroupbutton -> {
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
        }
    }
    fun initinject() {
        DaggerAddressCommponent.builder().activityCommpoent(activityCommpoent).addressmodule(Addressmodule()).build().inject(this)
        mpersenter.mView=this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addgrouplayout)
        initinject()
        newgroupbutton.setOnClickListener(this)
        grouphead.setOnClickListener(this)
        fh.setOnClickListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
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