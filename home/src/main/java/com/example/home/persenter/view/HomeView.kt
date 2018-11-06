package com.example.home.persenter.view

import android.graphics.Bitmap
import com.tencent.imsdk.TIMTextElem
import study.kotin.my.baselibrary.presenter.view.BaseView
import java.io.FileInputStream

interface HomeView:BaseView {
    fun showtextmsg(TIMTextElem: TIMTextElem)
    fun showimgmsg(bitmap: Bitmap)
    fun showSoundmsg(soundfile: FileInputStream)
    fun showFilemsg(path:String)
}