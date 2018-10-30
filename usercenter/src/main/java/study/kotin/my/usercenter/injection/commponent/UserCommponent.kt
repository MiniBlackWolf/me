package study.kotin.my.usercenter.injection.commponent

import dagger.Component
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.usercenter.injection.UsercommponerScope
import study.kotin.my.usercenter.injection.module.UserModule
import study.kotin.my.usercenter.ui.activity.RegisterActivity

@UsercommponerScope
@Component(dependencies = arrayOf(ActivityCommpoent::class),modules = arrayOf(UserModule::class))
interface UserCommponent {
    fun inject(registerActivity: RegisterActivity)
}