package com.example.home.Messges

import android.util.Log
import com.tencent.imsdk.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication

object SendSoundMsg {

    fun sendsoundmsg(id: String, data: TIMMessage){
        val conversation: TIMConversation
        if(id.substring(0,5)=="@TGS#"){
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.Group, //会话类型：群聊
                    id)
        }else {
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.C2C, //会话类型：单聊
                    id)
        }
        conversation.sendMessage(data,object : TIMValueCallBack<TIMMessage>{
            override fun onSuccess(p0: TIMMessage?) {
             //   BaseApplication.context.toast("发送成功")
            }

            override fun onError(p0: Int, p1: String?) {
                BaseApplication.context.toast("发送失败" + p1)
                Log.i("iiiiii", p1)
            }
        })
    }
}