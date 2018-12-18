package com.example.usercenter2.common


import android.app.Activity
import android.content.Intent
import com.tencent.qcloud.sdk.Constant
import com.tencent.qcloud.tlslibrary.activity.ImgCodeActivity
import com.tencent.qcloud.tlslibrary.service.Constants
import org.jetbrains.anko.toast
import com.example.usercenter2.ui.activity.RegisterActivity
import tencent.tls.platform.*
import javax.inject.Inject

class PwdLoginListener @Inject constructor(val context: Activity) : TLSPwdLoginListener {
     var tlsHelper: TLSHelper
    init {
        tlsHelper = TLSHelper.getInstance().init(context, Constant.SDK_APPID.toLong())
    }
    var identifier: String = ""
    override fun OnPwdLoginNeedImgcode(p0: ByteArray?, p1: TLSErrInfo?) {
        /* 用户需要进行图片验证码的验证，需要把验证码图片展示给用户，并引导用户输入；如果验证码输入错误，仍然会到达此回调并更新图片验证码*/
        val intent = Intent(context, ImgCodeActivity::class.java)
        intent.putExtra(Constants.EXTRA_IMG_CHECKCODE, p0)
        intent.putExtra(Constants.EXTRA_LOGIN_WAY, Constants.USRPWD_LOGIN)
        context.startActivity(intent)
    }

    override fun OnPwdLoginSuccess(p0: TLSUserInfo?) {
        /* 登录成功了，在这里可以获取用户票据*/
        context.toast("登陆成功")
        identifier = p0!!.identifier
        tlsHelper.TLSRefreshUserSig(identifier, RefreshUserSigListener(identifier,tlsHelper.getUserSig(identifier),context))
       // RegisterActivity().tlsHelper.TLSRefreshUserSig(identifier,  RegisterActivity().refreshUserSigListener)


    }

    override fun OnPwdLoginReaskImgcodeSuccess(p0: ByteArray?) {
        /* 请求刷新图片验证码成功，此时需要用picData 更新验证码图片，原先的验证码已经失效*/
        ImgCodeActivity.fillImageview(p0)
    }

    override fun OnPwdLoginFail(p0: TLSErrInfo?) {
        /* 登录失败，比如说密码错误，用户帐号不存在等，通过errInfo.ErrCode, errInfo.Title, errInfo.Msg等可以得到更具体的错误信息*/
        context.toast("${p0!!.Msg}")
        (context as RegisterActivity).hideLoading()
    }

    override fun OnPwdLoginTimeout(p0: TLSErrInfo?) {
// 密码登录过程中任意一步都可以到达这里，顾名思义，网络超时，可能是用户网络环境不稳定
        context.toast("登陆失败请检查网络连接!")
        (context as RegisterActivity).hideLoading()
}

}