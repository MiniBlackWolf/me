package com.example.home.persenter.view

import com.example.home.data.articledata
import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface articleView : BaseView {
    fun article(baseResp: BaseResp<String>)
    fun findarticle(r: List<articledata>)
    fun addactive(t: BaseResp<String>)//发布活动
    fun findactive(t: List<articledata>)//查找活动
    fun uploadimg(t: BaseResp<String>) //上传图片
    fun join(t: BaseResp<String>)
    fun quit(t: BaseResp<String>)
}