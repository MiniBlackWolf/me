package com.example.home.common

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.home.HomeAdapter.chatadapter
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMElemType
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMTextElem
import study.kotin.my.baselibrary.common.BaseApplication

object TextMessge {

    fun showTextMessge(message: TIMMessage?, mview: HomeView){
        for (i in 0 until message!!.getElementCount()) {
            val elem = message.getElement(i.toInt())
            //获取当前元素的类型
            val elemType = elem.getType()
            Log.d("iiiii", "elem type: " + elemType.name)
            if (elemType == TIMElemType.Text) {
                mview.showmsg(elem as TIMTextElem)

            } else if (elemType == TIMElemType.Image) {
                //处理图片消息
            }//...处理更多消息
        }

    }


}