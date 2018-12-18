package com.example.usercenter2.injection.commponent

import dagger.Component
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import com.example.usercenter2.injection.UsercommponerScope
import com.example.usercenter2.injection.module.UserModule
import com.example.usercenter2.ui.activity.RegisterActivity
import com.example.usercenter2.ui.fragment.ResetFrament
import com.example.usercenter2.ui.fragment.RigsterFragment

@UsercommponerScope
@Component(dependencies = arrayOf(ActivityCommpoent::class),modules = arrayOf(UserModule::class))
interface UserCommponent {
    fun inject(registerActivity: RegisterActivity)
    fun inject(RigsterFragment: RigsterFragment)
    fun inject(ResetFrament: ResetFrament)
}