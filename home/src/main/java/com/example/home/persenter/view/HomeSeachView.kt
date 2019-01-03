package com.example.home.persenter.view

import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import io.reactivex.Observable
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface HomeSeachView:BaseView {
     fun search(searchuserdata:searchuserdata )
     fun GroupSearch(searchgroupdata:searchgroupdata)

}