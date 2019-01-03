package com.example.home.persenter.view

import com.example.home.data.articledata
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface articleView:BaseView {
    fun article(baseResp: BaseResp<String>)
    fun findarticle(r:List<articledata>)
}