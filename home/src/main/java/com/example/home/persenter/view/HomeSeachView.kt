package com.example.home.persenter.view

import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import study.kotin.my.baselibrary.presenter.view.BaseView

interface HomeSeachView:BaseView {
    fun searchuser(searchuserdata: searchuserdata)
}