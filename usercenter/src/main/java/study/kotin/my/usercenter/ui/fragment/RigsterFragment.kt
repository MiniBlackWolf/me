package study.kotin.my.usercenter.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.tencent.bugly.imsdk.Bugly.applicationContext
import com.tencent.qalsdk.QALSDKManager
import com.tencent.qcloud.sdk.Constant
import com.tencent.qcloud.tlslibrary.service.PhonePwdRegisterService
import kotlinx.android.synthetic.main.registerpage.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.passverify
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.usercenter.R
import study.kotin.my.usercenter.common.PwdRegListener
import study.kotin.my.usercenter.injection.commponent.DaggerUserCommponent
import study.kotin.my.usercenter.injection.module.UserModule
import study.kotin.my.usercenter.persenter.registerPersenter
import tencent.tls.platform.TLSErrInfo
import tencent.tls.platform.TLSHelper
import tencent.tls.platform.TLSPwdRegListener
import tencent.tls.platform.TLSUserInfo


import javax.inject.Inject


class RigsterFragment @Inject constructor() : BaseMVPFragmnet<registerPersenter>() {
    lateinit var tlsHelper: TLSHelper
    lateinit var registerbutton: Button
    lateinit var setpassworld: EditText
    lateinit var okpassworld: EditText
    lateinit var pwdRegListener: PwdRegListener
    lateinit var fsyzm: Button
    lateinit var yzm: EditText
    lateinit var phonenmber: EditText
    private fun inits() {
        injectactivity()
        pwdRegListener = PwdRegListener(mpersenter.context)
        // 初始化TLSSDK
        QALSDKManager.getInstance().setEnv(0)
        QALSDKManager.getInstance().init(mpersenter.context, Constant.SDK_APPID)
        tlsHelper = TLSHelper.getInstance().init(mpersenter.context, Constant.SDK_APPID.toLong())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inits()
        val view = inflater.inflate(R.layout.registerpage, container, false)
        initlayout(view)
        passverify(view)
        return view
    }

    private fun initlayout(view: View) {
        phonenmber = view.find(R.id.phonenmber)
        yzm = view.find(R.id.yzm)
        fsyzm = view.find(R.id.fsyzm)
        registerbutton = view.find(R.id.registerbutton)
        setpassworld = view.find(R.id.setpassworld)
        okpassworld = view.find(R.id.okpassworld)
    }

    private fun passverify(view: View) {
        var a=0
        fsyzm.setOnClickListener {
            a++
            object : CountDownTimer(60000, 1000) {
                override fun onFinish() {
                    fsyzm.isEnabled = true
                    fsyzm.isClickable = true
                    fsyzm.setText("发送验证码")
                }

                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    fsyzm.setText("${millisUntilFinished / 1000} 秒后可以再次发送")
                }
            }.start()
            fsyzm.isEnabled = false
            fsyzm.isClickable = false
            Log.i("iiiiiiiii","$a")
            tlsHelper.TLSPwdRegAskCode("86-${phonenmber.text}", pwdRegListener)
        }
        registerbutton.setOnClickListener {
            val yzmcode = tlsHelper.TLSPwdRegVerifyCode(yzm.text.toString(), pwdRegListener)
            if (yzm.text.isEmpty()) {
                activity!!.toast("请输入验证码")
                return@setOnClickListener
            }
             else {
                if (it.passverify(setpassworld.text.toString(), activity)) {
                    if (okpassworld.text.toString() == setpassworld.text.toString()) {
                        tlsHelper.TLSPwdRegCommit(okpassworld.text.toString(), pwdRegListener);
                    } else activity!!.toast("两次密码不正确")
                }
            }
        }
    }

    private fun injectactivity() {
        DaggerUserCommponent.builder().activityCommpoent(mActivityComponent).userModule(UserModule()).build().inject(this)
    }
}

