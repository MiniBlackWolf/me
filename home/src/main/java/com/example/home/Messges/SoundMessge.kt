package com.example.home.Messges

import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMSoundElem
import study.kotin.my.baselibrary.utils.FileUtil
import java.io.FileInputStream

object SoundMessge {
    fun showSoundMsg(message: TIMMessage?, mview: HomeView) {
        for (i in 0 until message!!.getElementCount()) {
            val element = message.getElement(i.toInt()) as TIMSoundElem
            val tempAudio = FileUtil.getTempFile(FileUtil.FileType.AUDIO)
            element.getSoundToFile(tempAudio.absolutePath,object: TIMCallBack{
                override fun onSuccess() {
                    val file=FileInputStream(tempAudio)
                    mview.showSoundmsg(file)
                }

                override fun onError(p0: Int, p1: String?) {

                }
            })
        }

}


}