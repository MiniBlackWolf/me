package com.example.home.persenter

import com.example.home.Messges.FileMessge
import com.example.home.Messges.ImgMessge
import com.example.home.Messges.SoundMessge
import com.example.home.Messges.TextMessge
import com.example.home.common.ChatViewSet
import study.kotin.my.baselibrary.presenter.Basepersenter
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMElem
import com.tencent.imsdk.TIMElemType
import com.tencent.imsdk.TIMManager
import javax.inject.Inject

class HomePersenter @Inject constructor() : Basepersenter<HomeView>() {

    fun showmessge() {
        //消息监听
        TIMManager.getInstance().loginUser
        TIMManager.getInstance().addMessageListener {
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
                

            }
            TIMElemType.Image -> {
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