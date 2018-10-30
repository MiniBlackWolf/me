package study.kotin.my.usercenter.common

import android.content.Context
import org.jetbrains.anko.toast
import tencent.tls.platform.TLSErrInfo
import tencent.tls.platform.TLSPwdResetListener
import tencent.tls.platform.TLSUserInfo
import javax.inject.Inject

class PwdResetListener @Inject constructor(val context: Context): TLSPwdResetListener {
    override fun OnPwdResetReaskCodeSuccess(p0: Int, p1: Int) { /* 重新请求下发短信成功，可以跳转到输入验证码进行校验的界面，并开始倒计时，(reaskDuration 秒内不可以再次请求重发，在expireDuration 秒之后仍然没有进行短信验证，则应该回到第一步，重新开始流程)；在用户输入收到的短信验证码之后，可以调用PwdResetVerifyCode 进行验证。*/

    }

    override fun OnPwdResetVerifyCodeSuccess() { /* 短信验证成功，接下来可以引导用户输入密码，然后调用PwdResetCommit 完成重置密码流程*/
    }

    override fun OnPwdResetAskCodeSuccess(p0: Int, p1: Int) {/* 请求下发短信成功，可以跳转到输入验证码进行校验的界面，同时可以开始倒计时, (reaskDuration 秒内不可以重发短信，如果在expireDuration 秒之后仍然没有进行短信验证，则应该回到上一步，重新开始流程)；在用户输入收到的短信验证码之后，可以调用PwdResetVerifyCode 进行验证。*/

    }

    override fun OnPwdResetTimeout(p0: TLSErrInfo?) { /* 重置密码过程中任意一步都可以到达这里，顾名思义，网络超时，可能是用户网络环境不稳定，一般让用户重试即可*/
        context.toast(p0!!.Msg)
    }

    override fun OnPwdResetCommitSuccess(p0: TLSUserInfo?) { /* 重置密码成功，接下来可以引导用户进行新密码登录了，登录流程请查看相应章节*/
    }

    override fun OnPwdResetFail(p0: TLSErrInfo?) { /* 重置密码过程中任意一步都可以到达这里，可以根据tlsErrInfo 中ErrCode, Title, Msg 给用户弹提示语，引导相关操作*/
        context.toast(p0!!.Msg)
    }
}