package com.example.home.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.PopupWindow
import com.example.home.R
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.chatlayout.*
import kotlinx.android.synthetic.main.personalchatsettingslayout.*
import org.jetbrains.anko.alert
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class PersonalChatSettingActivity:BaseMVPActivity<HomePersenter>(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.Messagenotification->{
                val view = layoutInflater.inflate(R.layout.messagenotificationitem, null)
                val popWindow =  PopupWindow(this)
                popWindow.contentView=view//显示的布局，还可以通过设置一个View // .size(600,400) //设置显示的大小，不设置就默认包裹内容
                popWindow.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)))
                popWindow.setFocusable(true)//是否获取焦点，默认为ture
                popWindow .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                popWindow .showAsDropDown(textView9,0,10)//显示PopupWindow
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalchatsettingslayout)
        Messagenotification.setOnClickListener(this)

    }
}