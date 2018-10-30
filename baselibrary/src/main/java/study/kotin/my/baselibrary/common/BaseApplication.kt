package study.kotin.my.baselibrary.common

import android.app.Application
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.imsdk.*
import com.tencent.qalsdk.sdk.MsfSdkUtils
import com.tencent.qcloud.presentation.business.InitBusiness
import com.tencent.qcloud.tlslibrary.service.TlsBusiness
import study.kotin.my.baselibrary.R
import study.kotin.my.baselibrary.injection.commponent.AppCommpoent
import study.kotin.my.baselibrary.injection.commponent.DaggerAppCommpoent
import study.kotin.my.baselibrary.injection.module.AppModule


class BaseApplication : MultiDexApplication() {
    lateinit var appCommpoen: AppCommpoent
    override fun onCreate() {
        super.onCreate()
        initjection()
        ARouter.openLog()    // 打印日志
        ARouter.openDebug()
        ARouter.init(this)
        MultiDex.install(this)
        InitBusiness.start(applicationContext, TIMLogLevel.DEBUG.ordinal)
        //初始化TLS
        TlsBusiness.init(applicationContext)
        Log.d("tencentim", "初始化腾讯云Im")
        if (MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener { notification ->
                if (notification.groupReceiveMsgOpt == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                    //消息被设置为需要提醒
                    notification.doNotify(applicationContext, R.drawable.a4)
                }
            }
        }

    }

    private fun initjection() {
        appCommpoen = DaggerAppCommpoent.builder().appModule(AppModule(this)).build()
    }

}