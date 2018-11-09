package com.example.home.common

import android.content.Context.MODE_PRIVATE
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import androidx.core.widget.toast
import com.example.home.HomeAdapter.chatadapter
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.message.TIMMessageDraft
import com.tencent.imsdk.ext.message.TIMMessageLocator
import com.tencent.qcloud.presentation.viewfeatures.ChatView
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.MessageFactory
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMValueCallBack
import android.os.Environment.getExternalStorageDirectory
import com.tencent.imsdk.TIMImageElem
import android.provider.MediaStore
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import com.example.home.ui.activity.HomeActivity
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.LOG_TAG
import android.media.MediaRecorder
import android.os.Handler
import android.text.format.DateFormat
import android.view.WindowManager
import android.widget.Toast
import com.example.home.Utils.RecordingService
import com.tencent.open.utils.Global.getSharedPreferences
import study.kotin.my.baselibrary.utils.FileUtil
import java.io.File
import java.io.IOException
import java.nio.file.Files.isDirectory
import java.nio.file.Files.exists
import java.util.*
import com.oden.syd_camera.utils.SDCardUtils.getSDCardPath


class ChatViewSet constructor(val mView: HomeView) : ChatView {
    val context = BaseApplication.context
    lateinit var intent: Intent
    override fun showMessage(message: TIMMessage?) {
        MessageFactory.getMessage(message, mView)
    }

    override fun showMessage(messages: MutableList<TIMMessage>?) {
        for (d in 0..messages!!.size) {
            MessageFactory.getMessage(messages[d], mView)
        }

    }

    override fun showRevokeMessage(timMessageLocator: TIMMessageLocator?) {
    }

    override fun clearAllMessage() {
    }

    override fun onSendMessageSuccess(message: TIMMessage?) {
    }

    override fun onSendMessageFail(code: Int, desc: String?, message: TIMMessage?) {
    }

    override fun sendImage() {
        mView.sendmsg()
    }

    override fun sendPhoto() {

    }

    override fun sendText() {
        mView.sendmsg()
    }

    override fun sendFile() {
    }

    override fun startSendVoice() {
        try {
             intent = Intent(context, RecordingService::class.java)
            context.startService(intent)
        } catch (e: IOException) {
            context.toast("录音失败")
        }

    }

    override fun endSendVoice() {
        try {
            context.stopService(intent)
            Handler().postDelayed(object :Runnable{
                override fun run() {
                    mView.sendSoundmsg()
                }
            },500).run{}
        } catch (e: IOException) {
            context.toast("录音失败")
        }
    }

    override fun sendVideo(fileName: String?) {
    }

    override fun cancelSendVoice() {
    }

    override fun sending() {
    }

    override fun showDraft(draft: TIMMessageDraft?) {
    }

    override fun videoAction() {
    }

    override fun showToast(msg: String?) {
    }


}