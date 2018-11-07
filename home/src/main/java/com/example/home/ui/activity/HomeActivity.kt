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
import kotlinx.android.synthetic.main.chatlayout.*
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import kotlinx.android.synthetic.main.chatitem2.*
import study.kotin.my.baselibrary.common.BaseApplication
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.example.home.Messges.SendImgMsg
import com.oden.syd_camera.SydCameraActivity
import com.oden.syd_camera.camera.CameraParaUtil
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.message.TIMConversationExt
import study.kotin.my.baselibrary.utils.MediaUtil
import java.io.File
import java.io.FileInputStream
import java.util.*


class HomeActivity : BaseMVPActivity<HomePersenter>(), HomeView, View.OnClickListener {

    val SEND_MSG_TYPE = 1
    val SHOW_MSG_TYPE = 0
    lateinit var id: String
    lateinit var chatadapter: chatadapter
    //发送消息
    override fun sendmsg() {
        val timTextElem = TIMTextElem()
        timTextElem.text = chatsendview.editText.text.toString()
        mpersenter.sendmessge(id, timTextElem)
        var msglists = ArrayList<Msg>()
        msglists.add(Msg(chatsendview.editText.text.toString(), SEND_MSG_TYPE, 1))
        chatadapter.addData(msglists)
        chatadapter.notifyDataSetChanged()
        chatsendview.editText.setText("")
        chatrecyclerview.scrollToPosition(msglist.size - 1)

    }

    //文本信息
    var msglist = ArrayList<Msg>()

    override fun showtextmsg(TIMTextElem: TIMTextElem) {
        var msglists = ArrayList<Msg>()
        msglists.add(Msg((TIMTextElem).text, SHOW_MSG_TYPE, 1))
        chatadapter.addData(msglists)
        chatadapter.notifyDataSetChanged()
    }

    //图片信息
    override fun showimgmsg(bitmap: Bitmap) {
        msglist.add(Msg(bitmap, SHOW_MSG_TYPE, 2))
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
        getlangtimemsg()
    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)

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
                        .maxSelectable(1)
//                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(GlideEngine())
                        .forResult(1)
            }
            R.id.btn_file -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(intent, 99)
            }
        }
    }

    lateinit var mSelected: List<Uri>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //获取图片路径
            mSelected = Matisse.obtainResult(data!!)
            val obtainPathResult = Matisse.obtainPathResult(data)
            val bitmap = BitmapFactory.decodeFile(obtainPathResult.get(0))
            //构筑消息
            val timImageElem = TIMImageElem()
            timImageElem.path=obtainPathResult.get(0)
            mpersenter.sendmessge(id, timImageElem)
            //显示界面
            msglist.add(Msg(bitmap, SEND_MSG_TYPE, 2))
            chatadapter.addData(msglist)
            chatadapter.notifyDataSetChanged()
            chatrecyclerview.scrollToPosition(chatadapter.itemCount-1)
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
            val timimgElem = TIMImageElem()
            timimgElem.path = picturePath
            mpersenter.sendmessge(id, timimgElem)
        }

        if (requestCode == 99) {
            val uri = data!!.getData()//得到uri，后面就是将uri转化成file的过程。
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val actualimagecursor = managedQuery(uri, proj, null, null, null)
            val actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualimagecursor.moveToFirst()
            val img_path = actualimagecursor.getString(actual_image_column_index)
            val file = File(img_path)
            val length = file.length()
            Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun getlangtimemsg(){
        val userconversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, id)
        TIMConversationExt(userconversation).getLocalMessage(10, null, object :
                TIMValueCallBack<MutableList<TIMMessage>> {
            override fun onSuccess(p0: MutableList<TIMMessage>?) {
                p0!!.reverse()
                for (i in 0 until p0.size) {
                    val element  = p0.get(i).getElement(0)
                    when (element.type) {
                        TIMElemType.Text, TIMElemType.Face -> {
                            if(p0.get(i).isSelf){
                                val msglists = ArrayList<Msg>()
                                msglists.add(Msg((element as TIMTextElem).text, SEND_MSG_TYPE, 1))
                                chatadapter.addData(msglists)
                                chatadapter.notifyDataSetChanged()
                                chatrecyclerview.scrollToPosition(i)
                            }else {
                                showtextmsg(element as TIMTextElem)
                            }
                        }
                        TIMElemType.Image -> {
                            val bitmap = BitmapFactory.decodeFile((element as TIMImageElem).path)
                            if(p0.get(i).isSelf){
                                val msglists = ArrayList<Msg>()
                                msglists.add(Msg(bitmap, SEND_MSG_TYPE, 2))
                                chatadapter.addData(msglists)
                                chatadapter.notifyDataSetChanged()
                                chatrecyclerview.scrollToPosition(i)
                            }else {

                                showimgmsg(bitmap)
                            }

                        }
                        TIMElemType.Sound -> {
                        }
                        TIMElemType.Video -> {
                        }
                        TIMElemType.GroupTips -> return
                        //  return new GroupTipMessage(message);
                        TIMElemType.File -> {
                        }
                        TIMElemType.UGC -> return
                        else -> return
                    }

                }

            }

            override fun onError(p0: Int, p1: String?) {
                Log.i("iiiiiiiiiiiiii", p1)
            }
        })

    }
}