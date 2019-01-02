package com.example.home.persenter.view

import android.graphics.Bitmap
import com.tencent.imsdk.TIMFileElem
import com.tencent.imsdk.TIMGroupTipsElem
import com.tencent.imsdk.TIMTextElem
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp
import java.io.FileInputStream

interface HomeView:BaseView {
    fun showtextmsg(TIMTextElem: TIMTextElem,id:String)
    fun showimgmsg(bitmap: Bitmap,id:String)
    fun showSoundmsg(path:String,time:Long,id:String)
    fun showFilemsg( TIMFileElem: TIMFileElem,id:String)
    fun showgrouptipmsg(TIMGroupTipsElem: TIMGroupTipsElem,id:String)
    fun sendmsg()
    fun sendSoundmsg()


}