package com.example.home.common

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




class ChatViewSet constructor(val mView:HomeView):ChatView {
    val context=BaseApplication.context
    override fun showMessage(message: TIMMessage?) {
        MessageFactory.getMessage(message,mView)
    }

    override fun showMessage(messages: MutableList<TIMMessage>?) {
        for (d in 0..messages!!.size) {
            MessageFactory.getMessage(messages[d],mView)
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
    }

    override fun endSendVoice() {
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