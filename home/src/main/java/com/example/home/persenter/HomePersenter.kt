package com.example.home.persenter

import android.os.Environment
import android.util.Log
import com.example.home.Messges.*
import com.example.home.common.ChatViewSet
import study.kotin.my.baselibrary.presenter.Basepersenter
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.*
import javax.inject.Inject

class HomePersenter @Inject constructor() : Basepersenter<HomeView>() {

    fun showmessge(id:String) {
        //消息监听
        TIMManager.getInstance().loginUser
        TIMManager.getInstance().addMessageListener {
           for(i in it){
               if(i.conversation.peer!=id){
                   return@addMessageListener false
               }

           }
            if (it.size == 1) {
                ChatViewSet(mView).showMessage(it.get(0))
            } else {
                ChatViewSet(mView).showMessage(it)
            }

            return@addMessageListener false
        }

    }

    fun sendmessge(id: String, data: TIMElem) {
        when (data.type) {
            TIMElemType.Text, TIMElemType.Face -> {
                val TIMTextMessage = TIMMessage()
                TIMTextMessage.addElement(data as TIMTextElem)
                SendTextMsg.sendtextmsg(id, TIMTextMessage)
            }
            TIMElemType.Image -> {
                val msg = TIMMessage()
                //将 elem 添加到消息
                if (msg.addElement(data as TIMImageElem) != 0) {
                    Log.d("iiiiiii", "addElement failed")
                    return
                }
                SendImgMsg.sendimgmsg(id, msg)
            }
            TIMElemType.Sound -> {
                val TIMsoundMessage = TIMMessage()
                if (TIMsoundMessage.addElement(data as TIMSoundElem) != 0) {
                    Log.d("iiiiiii", "addElement failed")
                    return
                }
                SendSoundMsg.sendsoundmsg(id, TIMsoundMessage)
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