package com.example.home.Messges

import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMSoundElem

object SoundMessge {
    fun showSoundMsg(message: TIMMessage?, mview: HomeView) {
        for (i in 0 until message!!.getElementCount()) {
            val element = message.getElement(i.toInt()) as TIMSoundElem
            val soundpath="/storage/emulated/0/SoundFile"
            element.getSoundToFile(soundpath,object: TIMCallBack{
                override fun onSuccess() {
                    mview.showSoundmsg(soundpath)
                }

                override fun onError(p0: Int, p1: String?) {

                }
            })
        }

}


}