package com.example.home.Messges

import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMGroupTipsElem
import com.tencent.imsdk.TIMMessage

object GroupTipsMessge {
    fun showGroupTipsMessge(message: TIMMessage?, mview: HomeView) {
        for (i in 0 until message!!.getElementCount()) {
            val TIMGroupTipsElem = message.getElement(i.toInt()) as TIMGroupTipsElem
            mview.showgrouptipmsg(TIMGroupTipsElem,message.sender)
        }


    }
}