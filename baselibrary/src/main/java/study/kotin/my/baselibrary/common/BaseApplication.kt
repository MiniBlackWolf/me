package study.kotin.my.baselibrary.common


import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.tencent.imsdk.*
import com.tencent.qalsdk.sdk.MsfSdkUtils
import com.tencent.qcloud.presentation.business.InitBusiness
import com.tencent.qcloud.tlslibrary.service.TlsBusiness
import study.kotin.my.baselibrary.R
import study.kotin.my.baselibrary.injection.commponent.AppCommpoent
import study.kotin.my.baselibrary.injection.commponent.DaggerAppCommpoent
import study.kotin.my.baselibrary.injection.module.AppModule
import study.kotin.my.baselibrary.utils.Foreground
import com.tencent.smtt.sdk.QbSdk
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.content.IntentFilter
import com.tencent.bugly.Bugly


class BaseApplication : MultiDexApplication() {
    lateinit var appCommpoen: AppCommpoent
    override fun onCreate() {
        super.onCreate()
        context = this
        initjection()
        Bugly.init(this, "0e73bf32cd", false)
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
        registerActivityLifecycleCallbacks(Foreground.get())
        QbSdk.initX5Environment(this,object: QbSdk.PreInitCallback{
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(p0: Boolean) {
                if(p0){
                    Log.i("X5初始化","成功")
                }else{
                    Log.i("X5初始化","失败")
                }
            }
        })
//        val NetBroadcastReceiver = NetBroadcastReceiver()
//        val filter = IntentFilter()
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(NetBroadcastReceiver, filter)
    }

    private fun initjection() {
        appCommpoen = DaggerAppCommpoent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var context: Context
    }


}