package com.example.home.Messges

import android.util.Log
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMElemType
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMTextElem

object TextMessge {

    fun showTextMessge(message: TIMMessage?, mview: HomeView){
        for (i in 0 until message!!.getElementCount()) {
            val elem = message.getElement(i.toInt())
            //获取当前元素的类型
            val elemType = elem.getType()
            Log.d("iiiii", "elem type: " + elemType.name)
            if (elemType == TIMElemType.Text) {
                mview.showtextmsg(elem as TIMTextElem,message.sender)
            }
        }

    }


}