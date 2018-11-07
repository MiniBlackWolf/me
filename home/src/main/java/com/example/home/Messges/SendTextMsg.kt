package com.example.home.Messges

import android.util.Log
import com.tencent.imsdk.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication.Companion.context

object SendTextMsg {

    fun sendtextmsg(id: String, data: TIMMessage) {
        val conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C, //会话类型：单聊
                id)
        conversation.sendMessage(data, object : TIMValueCallBack<TIMMessage> {
            override fun onSuccess(p0: TIMMessage?) {
                context.toast("发送成功")
            }

            override fun onError(p0: Int, p1: String?) {
                context.toast("发送失败" + p1)
                Log.i("iiiiii", p1)
            }
        })
    }
}