package study.kotin.my.baselibrary.common

import android.app.Application
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import study.kotin.my.baselibrary.injection.commponent.AppCommpoent
import study.kotin.my.baselibrary.injection.commponent.DaggerAppCommpoent
import study.kotin.my.baselibrary.injection.module.AppModule


class BaseApplication: MultiDexApplication() {
    lateinit var appCommpoen:AppCommpoent
    override fun onCreate() {
        super.onCreate()
        initjection()
        ARouter.openLog()    // 打印日志
        ARouter.openDebug()
        ARouter.init(this)
        MultiDex.install(this)
    }

    private fun initjection() {
        appCommpoen=DaggerAppCommpoent.builder().appModule(AppModule(this)).build()
    }

}