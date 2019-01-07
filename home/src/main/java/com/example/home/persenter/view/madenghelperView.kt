package com.example.home.persenter.view

import com.example.home.data.madengdata
import study.kotin.my.baselibrary.presenter.view.BaseView

interface madenghelperView:BaseView {
    fun helper(t:List<madengdata>)
    fun helpererror(e: Throwable)
}