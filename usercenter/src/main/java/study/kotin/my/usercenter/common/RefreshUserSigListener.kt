package study.kotin.my.usercenter.common

import android.app.Activity
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMManager
import org.jetbrains.anko.toast
import tencent.tls.platform.TLSErrInfo
import tencent.tls.platform.TLSRefreshUserSigListener
import tencent.tls.platform.TLSUserInfo

class RefreshUserSigListener(val id:String,val usersig:String,val activity: Activity): TLSRefreshUserSigListener {

    override fun OnRefreshUserSigSuccess(p0: TLSUserInfo?) {
        TIMManager.getInstance().login(id, usersig, object : TIMCallBack {
            override fun onSuccess() {
                ARouter.getInstance().build("/App/Homepage").navigation()
                activity.finish()
            }

            override fun onError(p0: Int, p1: String?) {
                activity.toast("$p1")
                Log.i("iiiiiiiiiiiiii", p1)
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