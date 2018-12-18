package com.example.usercenter2.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import com.example.usercenter2.R
import com.tencent.qalsdk.QALSDKManager
import com.tencent.qcloud.sdk.Constant
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Response
import study.kotin.my.baselibrary.ext.passverify
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import com.example.usercenter2.common.PwdResetListener
import com.example.usercenter2.injection.commponent.DaggerUserCommponent
import com.example.usercenter2.injection.module.UserModule
import com.example.usercenter2.persenter.registerPersenter
import com.example.usercenter2.persenter.view.registerView
import tencent.tls.platform.TLSHelper
import javax.inject.Inject

class ResetFrament @Inject constructor(): BaseMVPFragmnet<registerPersenter>(), registerView {
    override fun resetpassResult(result: BaseResp<String>) {
        if (result.success) {
            toast("重置密码成功")
            val Transaction = activity!!.supportFragmentManager.beginTransaction()
            Transaction.hide(this)
            Transaction.commit()
            activity!!.loginbutton.isVisible=true
        } else {
            toast(result.message)
        }
    }

    override fun LoginResult(result: Response<BaseResp<String>>) {

    }

    override fun RegistResult(result: BaseResp<String>) {

    }

    override fun sendSms(result: BaseResp<String>) {
        if (result.success) {
            toast("发送验证码成功")
        } else {
            toast(result.message)
        }
    }

    lateinit var tlsHelper: TLSHelper
    lateinit var pwdResetListener: PwdResetListener
    lateinit var registerbutton2: Button
    lateinit var setpassworld2: EditText
    lateinit var okpassworld2: EditText
    lateinit var fsyzm2: Button
    lateinit var yzm2: EditText
    lateinit var phonenmber2: EditText
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        injectactivity()
        val view = inflater.inflate(R.layout.resetpassword, container, false)
        pwdResetListener= PwdResetListener(mpersenter.context,view)
        initlayout(view)
        passverify()
        return view
    }


    private fun passverify(){
        fsyzm2.setOnClickListener{
            object : CountDownTimer(60000, 1000) {
                override fun onFinish() {
                    fsyzm2.isEnabled = true
                    fsyzm2.isClickable = true
                    fsyzm2.setText("发送验证码")
                }

                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    fsyzm2.setText("${millisUntilFinished / 1000} 秒后可以再次发送")
                }
            }.start()
            fsyzm2.isEnabled = false
            fsyzm2.isClickable = false
            mpersenter.sendsms(phonenmber2.text.toString())
        }
        registerbutton2.setOnClickListener{
            if(yzm2.text.isEmpty()){
                activity!!.toast("验证码不能为空!")
                return@setOnClickListener
            }
            else {
                if (it.passverify(setpassworld2.text.toString(), activity)) {
                    if (okpassworld2.text.toString() == setpassworld2.text.toString()) {
                        mpersenter.resetpass(phonenmber2.text.toString(),yzm2.text.toString(),okpassworld2.text.toString())
                    } else activity!!.toast("两次密码不正确")
                }
            }
        }
    }
    private fun initlayout(view: View) {
        phonenmber2 = view.find(R.id.phonenmber)
        yzm2 = view.find(R.id.yzm)
        fsyzm2 = view.find(R.id.fsyzm)
        registerbutton2 = view.find(R.id.registerbutton)
        setpassworld2 = view.find(R.id.setpassworld)
        okpassworld2 = view.find(R.id.okpassworld)
    }

    private fun injectactivity() {
        DaggerUserCommponent.builder().activityCommpoent(mActivityComponent).userModule(UserModule()).build().inject(this)
        QALSDKManager.getInstance().setEnv(0)
        QALSDKManager.getInstance().init(mpersenter.context, Constant.SDK_APPID)
        tlsHelper = TLSHelper.getInstance().init(mpersenter.context, Constant.SDK_APPID.toLong())
        mpersenter.mView=this
    }

}

