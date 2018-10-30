package study.kotin.my.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.passverify
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.usercenter.R
import study.kotin.my.usercenter.persenter.registerPersenter


import javax.inject.Inject

class RigsterFragment @Inject constructor() : BaseMVPFragmnet<registerPersenter>() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.registerpage, container, false)
        val s = view.find<EditText>(R.id.setpassworld)
        val s1 = view.find<EditText>(R.id.okpassworld)
        view.find<Button>(R.id.registerbutton).setOnClickListener{
          if(it.passverify(s.text.toString(),activity)){
              if (s1.text.toString()==s.text.toString()) activity!!.toast("注册成功")
            else activity!!.toast("两次密码不正确")
          }
        }
        return view
    }


}

