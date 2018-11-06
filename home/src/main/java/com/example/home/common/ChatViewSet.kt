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
        val peer = "86-13068380650"  //获取与用户 "sample_user_1" 的会话
        val conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C, //会话类型：单聊
                peer)
        //构造一条消息
        val msg = TIMMessage()

//添加图片
        val elem = TIMImageElem()
        elem.path = Environment.getExternalStorageDirectory().absolutePath

//将 elem 添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d("iiiiiii", "addElement failed")
            return
        }

//发送消息
        conversation.sendMessage(msg, object : TIMValueCallBack<TIMMessage> {
            //发送消息回调
            override fun onError(code: Int, desc: String) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d("iiiiiii", "send message failed. code: $code errmsg: $desc")
            }

            override fun onSuccess(msg: TIMMessage) {//发送消息成功
                Log.e("iiiiiii", "SendMsg ok")
            }
        })
    }

    override fun sendPhoto() {
    }

    override fun sendText() {
        val peer = "86-13068380650"  //获取与用户 "sample_user_1" 的会话
        val conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C, //会话类型：单聊
                peer)
        val TIMMessage = TIMMessage()
        val timTextElem = TIMTextElem()
        timTextElem.text = "saasdsda"
        TIMMessage.addElement(timTextElem)
        conversation.sendMessage(TIMMessage, object : TIMValueCallBack<TIMMessage> {
            override fun onSuccess(p0: TIMMessage?) {
                context.toast("发送成功")
            }

            override fun onError(p0: Int, p1: String?) {
                context.toast("发送失败" + p1)
                Log.i("iiiiii", p1)
            }
        })           //会话对方用户帐号//对方ID
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