package com.example.home.ui.activity

import android.os.Bundle
import com.example.home.R
import com.example.home.persenter.HomePersenter
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class AddActivity:BaseMVPActivity<HomePersenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroupfarment_3_activity)

    }
}