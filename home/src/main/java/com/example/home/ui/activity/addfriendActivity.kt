package com.example.home.ui.activity

import android.os.Bundle
import com.example.home.R
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.addfriendlayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class addfriendActivity : BaseMVPActivity<HomePersenter>() {
    val id by lazy { intent.extras?.getString("id") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addfriendlayout)
        sendfdmsg.setOnClickListener{
            
        }
    }
}