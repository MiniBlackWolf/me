package study.kotin.my.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.usercenter.R
import study.kotin.my.usercenter.common.PwdRegListener
import study.kotin.my.usercenter.common.PwdResetListener
import study.kotin.my.usercenter.injection.commponent.DaggerUserCommponent
import study.kotin.my.usercenter.injection.module.UserModule
import study.kotin.my.usercenter.persenter.registerPersenter
import javax.inject.Inject

class ResetFrament : BaseMVPFragmnet<registerPersenter>() {
    @Inject
    lateinit var pwdResetListener: PwdResetListener
    lateinit var registerbutton2: Button
    lateinit var setpassworld2: EditText
    lateinit var okpassworld2: EditText
    lateinit var fsyzm2: Button
    lateinit var yzm2: EditText
    lateinit var phonenmber2: EditText
    @Inject
    lateinit var RigsterFragment:RigsterFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        injectactivity()
        val view = inflater.inflate(R.layout.resetpassword, container, false)
        initlayout(view)
        return view
    }


    private fun passverify(){
        fsyzm2.setOnClickListener{
            if(yzm2.text.isEmpty()){
                activity!!.toast("验证码不能为空!")
            }
            RigsterFragment.tlsHelper.TLSPwdResetAskCode("86-${yzm2.text}", pwdResetListener)
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
    }
}

