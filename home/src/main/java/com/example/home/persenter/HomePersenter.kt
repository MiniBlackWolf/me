package com.example.home.persenter

import com.example.home.common.ChatViewSet
import com.example.home.common.TextMessge
import study.kotin.my.baselibrary.presenter.Basepersenter
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMMessage
import study.kotin.my.baselibrary.ext.MessageFactory
import javax.inject.Inject

class HomePersenter @Inject constructor(): Basepersenter<HomeView>() {

    fun showtextmessge(){
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
}