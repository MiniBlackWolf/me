package com.example.home.ui.activity

import android.os.Bundle
import com.example.home.R
import com.example.home.persenter.HomePersenter
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class PersonalhomeActivity:BaseMVPActivity<HomePersenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalhomepage)
        val id=intent.extras?.getString("id")
    }
}