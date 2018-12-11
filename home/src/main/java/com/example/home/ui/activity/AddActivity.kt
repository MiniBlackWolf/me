package com.example.home.ui.activity

import android.os.Bundle
import android.view.View
import com.example.home.R
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.publicgroupfarment_3_activity.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class AddActivity:BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ctf->finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroupfarment_3_activity)
        ctf.setOnClickListener(this)
    }
}