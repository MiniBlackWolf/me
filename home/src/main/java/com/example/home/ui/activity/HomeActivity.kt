package com.example.home.ui.activity

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.home.HomeAdapter.chatadapter
import com.example.home.R
import com.example.home.common.ChatViewSet
import com.example.home.common.Msg
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.example.home.persenter.HomePersenter
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMConversation
import kotlinx.android.synthetic.main.chatlayout.*
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMTextElem
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo
import com.tencent.qcloud.presentation.presenter.ConversationPresenter
import com.tencent.qcloud.presentation.viewfeatures.ConversationView
import kotlinx.android.synthetic.main.chatitem2.*
import study.kotin.my.baselibrary.common.BaseApplication
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import com.oden.syd_camera.SydCameraActivity
import com.oden.syd_camera.camera.CameraParaUtil
import study.kotin.my.baselibrary.utils.MediaUtil
import java.io.FileInputStream


class HomeActivity : BaseMVPActivity<HomePersenter>(), HomeView, View.OnClickListener {

    lateinit var id: String
    lateinit var chatadapter: chatadapter
    //文本信息
    var msglist = ArrayList<Msg>()

    override fun showtextmsg(TIMTextElem: TIMTextElem) {
        initview()
        chatmsg2.isVisible = true
        msglist.add(Msg((TIMTextElem).text, 0, 2))
        chatadapter.addData(msglist)
        chatadapter.notifyDataSetChanged()
    }

    //图片信息
    override fun showimgmsg(bitmap: Bitmap) {
        initview()
        showimgmsg.isVisible = true
        msglist.add(Msg(bitmap, 0, 2))
        chatadapter.addData(msglist)
        chatadapter.notifyDataSetChanged()
    }

    //语音消息
    override fun showSoundmsg(soundfile: FileInputStream) {
        try {
            MediaUtil.getInstance().play(soundfile)
            MediaUtil.getInstance().setEventListener(object : MediaUtil.EventListener {
                override fun onStop() {

                }
            })
        } catch (e: Exception) {

        }

//        initview()
//        showimgmsg.isVisible = true
//        msglist.add(Msg("1", 0, 2))
//        chatadapter.addData(msglist)
//        chatadapter.notifyDataSetChanged()
    }

    //文件消息
    override fun showFilemsg(path: String) {
        Log.i("iiiiiiiiii", path)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatlayout)
        initinject()
        //用户名获取
        val extras = intent.extras
        id = extras.get("id") as String
        //对话消息页面初始化
        chatadapter = chatadapter(msglist, BaseApplication.context)
        chatrecyclerview.adapter = chatadapter
        chatrecyclerview.layoutManager = LinearLayoutManager(BaseApplication.context)
        mpersenter.mView = this
        chatsendview.setChatView(ChatViewSet(mpersenter.mView), R.id.btn_photo, R.id.btn_image, R.id.btn_file)
        btn_photo.setOnClickListener(this)
        btn_image.setOnClickListener(this)
        btn_file.setOnClickListener(this)
        chatsendview.btnAdd.setOnClickListener {
            morePanel.isVisible = !morePanel.isVisible
        }
        mpersenter.showmessge()


    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)

    }

    fun initview() {
        showimgmsg.isVisible = false
        chatmsg2.isVisible = false
        palyer.isVisible = false
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        //以下代码为处理Android6.0、7.0动态权限所需
//        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        PermissionManager.handlePermissionsResult(this, type, TakePhotoUt.invokeParam, TakePhotoUt)
//
//    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_photo -> {
                val intent = Intent(this, SydCameraActivity::class.java)
                intent.putExtra(CameraParaUtil.picQuality, 70) //图片质量0~100
                intent.putExtra(CameraParaUtil.picWidth, 1536)  //照片最小宽度配置，高度根据屏幕比例自动配置
                intent.putExtra(CameraParaUtil.previewWidth, 1280)  //相机预览界面最小宽度配置，高度根据屏幕比例自动配置
                startActivityForResult(intent, CameraParaUtil.REQUEST_CODE_FROM_CAMERA)
            }
            R.id.btn_image -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(9)
//                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(GlideEngine())
                        .forResult(1)
            }
            R.id.btn_file -> {

            }
        }
    }

    lateinit var mSelected: List<Uri>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainResult(data!!)

            val obtainPathResult = Matisse.obtainPathResult(data)
            Log.d("Matisse", "mSelected: $obtainPathResult")
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("iiiiii", "拍照取消!")
            return;
        }
        if (resultCode != Activity.RESULT_OK) {
            Log.w("iiiiii", "拍照失败!")
            return;
        }

        if (requestCode == CameraParaUtil.REQUEST_CODE_FROM_CAMERA) {

            val picturePath = data!!.getStringExtra(CameraParaUtil.picturePath)
            val bitmap = BitmapFactory.decodeFile(picturePath)
            Log.d("iiiii", "onActivityResult picturePath: " + picturePath)
        }
    }

}