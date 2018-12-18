package com.example.usercenter2.common

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.example.usercenter2.R
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import com.example.usercenter2.ui.activity.RegisterActivity
import tencent.tls.platform.TLSErrInfo
import tencent.tls.platform.TLSPwdRegListener
import tencent.tls.platform.TLSUserInfo

class PwdRegListener(val context: Context, val view: View) : TLSPwdRegListener {
    override fun OnPwdRegVerifyCodeSuccess() {
        context.toast("验证成功！")
        view.find<Button>(R.id.button4).isVisible = true
    }

    override fun OnPwdRegAskCodeSuccess(p0: Int, p1: Int) {
        context.toast("短信发送成功！")
    }

    override fun OnPwdRegCommitSuccess(p0: TLSUserInfo?) {
        context.toast("注册成功！")
        context.startActivity<RegisterActivity>()
    }

    override fun OnPwdRegFail(p0: TLSErrInfo?) {
        context.toast("发生错误！${p0!!.ErrCode}--${p0.Msg+"--"+p0.Title+"--"+p0.ExtraMsg}")
        Log.i("TLSPwdRegListenerError", "发生错误！${p0!!.ErrCode}--${p0.Msg + "--" + p0.Title + "--" + p0.ExtraMsg}")
    }

    override fun OnPwdRegTimeout(p0: TLSErrInfo?) {
        //context.toast("发生错误！Timeout!${p0!!.ErrCode}--${p0.Msg+"--"+p0.Title+"--"+p0.ExtraMsg}")
        Log.i("TLSPwdRegListenerError", "发生错误！Timeout!${p0!!.ErrCode}--${p0.Msg + "--" + p0.Title + "--" + p0.ExtraMsg}")
    }

    override fun OnPwdRegReaskCodeSuccess(p0: Int, p1: Int) {
        context.toast("短信发送成功！")
    }
}