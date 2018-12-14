package com.example.home.Messges

import android.os.Environment
import android.util.Log
import com.tencent.imsdk.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication

object SendImgMsg {

    fun sendimgmsg(id: String, data: TIMMessage) {
        val conversation:TIMConversation
        if(id.substring(0,5)=="@TGS#"){
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.Group, //会话类型：群聊
                    id)
        }else {
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.C2C, //会话类型：单聊
                    id)
        }
        //发送消息
        conversation.sendMessage(data, object : TIMValueCallBack<TIMMessage> {
            //发送消息回调
            override fun onError(code: Int, desc: String) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                BaseApplication.context.toast("发送失败")
                Log.e("iiiiiii", "send message failed. code: $code errmsg: $desc")
            }
            override fun onSuccess(msg: TIMMessage) {//发送消息成功
             //   BaseApplication.context.toast("发送消息成功")
                Log.i("iiiiiii", "SendMsg ok")
            }
        })

    }
}