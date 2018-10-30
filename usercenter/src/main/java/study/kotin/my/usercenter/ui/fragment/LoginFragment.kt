package study.kotin.my.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.usercenter.R
import study.kotin.my.usercenter.persenter.registerPersenter
import study.kotin.my.usercenter.persenter.view.registerView
import study.kotin.my.usercenter.service.Userservice

import javax.inject.Inject

class LoginFragment @Inject constructor():BaseMVPFragmnet<registerPersenter>() {


    @Inject
    lateinit var userserviceimpl: Userservice
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.loginpage, container, false)
        return view
    }

    }

