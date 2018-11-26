package com.example.home.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.home.R
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.personalhomepage.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

@Route(path = "/home/PersonalhomeActivity")
class PersonalhomeActivity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.chatfh -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalhomepage)
        val id = intent.extras?.getString("id")
        chatfh.setOnClickListener(this)
    }
}