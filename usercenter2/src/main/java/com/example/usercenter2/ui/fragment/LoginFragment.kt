package com.example.usercenter2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.usercenter2.R
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet

import com.example.usercenter2.persenter.registerPersenter
import com.example.usercenter2.service.Userservice

import javax.inject.Inject

class LoginFragment @Inject constructor():BaseMVPFragmnet<registerPersenter>() {


    @Inject
    lateinit var userserviceimpl: Userservice
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.loginpage, container, false)
        return view
    }

    }

