package com.example.home.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.home.R
import com.example.home.persenter.HomePersenter
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.publicgroupfarment_3_article.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class PublicGroupFarment_3_Article_Activity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    lateinit var mEditor: RichEditor
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ctf -> {
                finish()
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroupfarment_3_article)
        ctf.setOnClickListener(this)
    }
}