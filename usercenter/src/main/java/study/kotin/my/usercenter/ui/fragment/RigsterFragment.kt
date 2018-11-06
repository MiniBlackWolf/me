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
import androidx.core.view.isVisible
import com.tencent.qalsdk.QALSDKManager
import com.tencent.qcloud.sdk.Constant
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.passverify
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.usercenter.R
import study.kotin.my.usercenter.common.PwdRegListener
import study.kotin.my.usercenter.common.TextWatchers
import study.kotin.my.usercenter.injection.commponent.DaggerUserCommponent
import study.kotin.my.usercenter.injection.module.UserModule
import study.kotin.my.usercenter.persenter.registerPersenter
import study.kotin.my.usercenter.ui.activity.RegisterActivity
import tencent.tls.platform.TLSHelper


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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        injectactivity()
        val view = inflater.inflate(R.layout.registerpage, container, false)
        pwdRegListener = PwdRegListener(mpersenter.context, view)
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
        var a = 0
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
            Log.i("iiiiiiiii", "$a")
            tlsHelper.TLSPwdRegAskCode("86-${phonenmber.text}", pwdRegListener)
        }
        yzm.onFocusChangeListener = TextWatchers(tlsHelper, pwdRegListener, yzm, 1,mpersenter.context)
        registerbutton.setOnClickListener {


            if (it.passverify(setpassworld.text.toString(), activity)) {
                if (okpassworld.text.toString() == setpassworld.text.toString()) {
                    view.find<Button>(R.id.button3).isVisible = true
                    tlsHelper.TLSPwdRegCommit(okpassworld.text.toString(), pwdRegListener);
                } else activity!!.toast("两次密码不正确")
            }

        }
    }

    private fun injectactivity() {
        DaggerUserCommponent.builder().activityCommpoent(mActivityComponent).userModule(UserModule()).build().inject(this)
        QALSDKManager.getInstance().setEnv(0)
        QALSDKManager.getInstance().init(mpersenter.context, Constant.SDK_APPID)
        tlsHelper = TLSHelper.getInstance().init(mpersenter.context, Constant.SDK_APPID.toLong())
    }
}
