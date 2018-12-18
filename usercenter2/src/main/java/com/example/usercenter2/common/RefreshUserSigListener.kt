package com.example.usercenter2.common

import android.app.Activity
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMManager
import com.tencent.qcloud.presentation.event.MessageEvent
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.utils.PushUtil
import com.example.usercenter2.ui.activity.RegisterActivity
import tencent.tls.platform.TLSErrInfo
import tencent.tls.platform.TLSRefreshUserSigListener
import tencent.tls.platform.TLSUserInfo

class RefreshUserSigListener(val id:String,val usersig:String,val activity: Activity): TLSRefreshUserSigListener {

    override fun OnRefreshUserSigSuccess(p0: TLSUserInfo?) {
        TIMManager.getInstance().login(id, usersig, object : TIMCallBack {
            override fun onSuccess() {
                PushUtil.instance
                MessageEvent.getInstance()
                ARouter.getInstance().build("/App/Homepage").navigation()
                (activity as RegisterActivity).hideLoading()
                activity.finish()
            }

            override fun onError(p0: Int, p1: String?) {
                activity.toast("$p1")
                Log.i("iiiiiiiiiiiiii", p1)
                (activity as RegisterActivity).hideLoading()
            }
        })
    }

    override fun OnRefreshUserSigTimeout(p0: TLSErrInfo?) {
        Log.i("iiiiiiiiiii", p0!!.Msg)
    }

    override fun OnRefreshUserSigFail(p0: TLSErrInfo?) {
        Log.i("iiiiiiiiiii", p0!!.Msg)
    }
}