package com.example.home.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.home.R
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.example.home.persenter.HomePersenter
import org.jetbrains.anko.toast

class HomeActivity :BaseMVPActivity<HomePersenter>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homemain_layout)

    }

}