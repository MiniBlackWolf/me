package com.example.home.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.home.R
import com.example.home.persenter.HomePersenter
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

@Route(path = "/home/searchactivity")
class SearchActivity:BaseMVPActivity<HomePersenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initinject()
       setContentView(R.layout.searchlayout)
    }
    fun initinject () {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)

    }
}