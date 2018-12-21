package com.example.home.ui.activity

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.home.R
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.publicgroupfarment_3_activity.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class AddActivity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ctf -> finish()
            R.id.textedit -> {

            }
            R.id.addimg -> {
            }
            R.id.textView24->{
                textedit.isVisible=false
                addimg.isVisible=false
            }
            R.id.constraintLayout6->{
                if(!textedit.isVisible){
                    textedit.isVisible=true
                    addimg.isVisible=true
                    view3.isVisible=true
                }else{
                    textedit.isVisible=false
                    addimg.isVisible=false
                    view3.isVisible=false
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroupfarment_3_activity)
        ctf.setOnClickListener(this)
        textedit.setOnClickListener(this)
        addimg.setOnClickListener(this)
        textView24.setOnClickListener(this)
        constraintLayout6.setOnClickListener(this)
    }
}