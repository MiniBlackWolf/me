package com.example.usercenter2.ui.activity


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.example.usercenter2.injection.module.UserModule
import com.example.usercenter2.persenter.registerPersenter
import com.example.usercenter2.persenter.view.registerView
import com.example.usercenter2.ui.fragment.RigsterFragment
import javax.inject.Inject
import android.support.v4.app.ActivityCompat
import android.util.Base64
import android.util.Log
import android.widget.Button
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.usercenter2.R
import com.tencent.imsdk.*
import com.tencent.qalsdk.QALSDKManager
import com.tencent.qcloud.presentation.event.FriendshipEvent
import com.tencent.qcloud.presentation.event.GroupEvent
import com.tencent.qcloud.presentation.event.MessageEvent
import com.tencent.qcloud.presentation.event.RefreshEvent
import com.tencent.qcloud.sdk.Constant
import com.tencent.qcloud.ui.NotifyDialog
import org.jetbrains.anko.find
import retrofit2.Response
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.utils.Base64Utils
import study.kotin.my.baselibrary.utils.PushUtil
import com.example.usercenter2.common.PwdLoginListener
import com.example.usercenter2.injection.commponent.DaggerUserCommponent
import com.example.usercenter2.ui.fragment.ResetFrament
import tencent.tls.platform.TLSHelper
import tencent.tls.platform.TLSUserInfo

@Route(path = "/usercenter/RegisterActivity")
class RegisterActivity : BaseMVPActivity<registerPersenter>(), registerView {
    override fun resetpassResult(result: BaseResp<String>) {

    }

    override fun RegistResult(result: BaseResp<String>) {

    }

    override fun sendSms(result: BaseResp<String>) {
    }


    lateinit var userInfo: TLSUserInfo
    lateinit var tlsHelper: TLSHelper
    var count = 0
    @Inject
    lateinit var RigsterFragment: RigsterFragment
    @Inject
    lateinit var ResetFrament: ResetFrament
    @Inject
    lateinit var pwdLoginListener: PwdLoginListener

    val loginbutton by lazy{find<Button>(R.id.loginbutton)}
    var usersig: String = ""


    override fun LoginResult(result: Response<BaseResp<String>>) {
        if (result.body() == null) {
            toast("账号和密码错误")
            hideLoading()
            return
        }
        val edit = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).edit()
        edit.putString("sig", result.body()!!.sig)
        edit.putString("jwt", result.body()!!.jwt)
        edit.apply()
        var substring = ""
        try {
            val fromBase64 = Base64.decode(result.body()!!.jwt, Base64.URL_SAFE)
            val from = String(fromBase64)
            substring = from.substring(from.indexOf("name") + 7, from.indexOf("name") + 13)
        } catch (e: IllegalArgumentException) {
            toast("发生未知错误请重试")
            hideLoading()
            e.printStackTrace()
            return
        }
       // ARouter.getInstance().build("/App/Homepage").navigation()
        TIMlogin(substring, result.body()!!.sig)
    }

    private fun TIMlogin(user: String, sig: String) {
        TIMManager.getInstance().login(user, sig, object : TIMCallBack {
            override fun onSuccess() {
                val edit = BaseApplication.context.getSharedPreferences("UserAcc", Context.MODE_PRIVATE).edit()
                edit.putString("user", Base64Utils.getBase64(user))
                edit.putString("pass", Base64Utils.getBase64(passworld.text.toString()))
                edit.apply()
                PushUtil.instance
                MessageEvent.getInstance()
                ARouter.getInstance().build("/App/Homepage").navigation()
                hideLoading()
                finish()
            }

            override fun onError(p0: Int, p1: String?) {
                Log.i("iiiiiiii", "eeeeeee")
                hideLoading()
                toast("登录失败请重试")
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==1){
            for(i in 0 until  grantResults.size){
                if(grantResults[i]== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(this,
                            permissions, 1)
                }
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA), 1)
        injectactivity()
     //   navToHome()
        val sharedPreferences = getSharedPreferences("UserAcc", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("user", "")
        val sig = sharedPreferences.getString("sig", "")
        if (user != "" && sig != "") {
            showLoading()
            TIMlogin(Base64Utils.getFromBase64(user!!), sig!!)
        }

        //----------------
        changFragment()
        //注册方法
        register.setOnClickListener {
            val Transaction = supportFragmentManager.beginTransaction()
            Transaction.show(RigsterFragment)
            Transaction.commit()
            loginbutton.isVisible=false
        }
        //登陆方法
        loginbutton.setOnClickListener {
            if (passworld.text.isEmpty()) {
                toast("密码不能为空")
                return@setOnClickListener
            }
            if (username.text.isEmpty()) {
                toast("用户名不能为空")
                return@setOnClickListener
            }
            showLoading()
            mpersenter.Login(username.text.toString(), passworld.text.toString())
        }

        romve.setOnClickListener {
            loginbutton.isVisible=false
            val Transaction = supportFragmentManager.beginTransaction()
            Transaction.hide(RigsterFragment)
            Transaction.show(ResetFrament)
            Transaction.commit()
        }


    }

    //切换页面
    private fun changFragment() {
        val Transaction = supportFragmentManager.beginTransaction()
        Transaction.add(R.id.s, RigsterFragment, "RigsterFragment")
        Transaction.add(R.id.s, ResetFrament, "ResetFrament")
        Transaction.hide(RigsterFragment)
        Transaction.hide(ResetFrament)
        Transaction.commit()
    }

    private fun injectactivity() {
        DaggerUserCommponent.builder().activityCommpoent(activityCommpoent).userModule(UserModule()).build().inject(this)
        mpersenter.mView = this
        QALSDKManager.getInstance().setEnv(0)
        QALSDKManager.getInstance().init(mpersenter.context, Constant.SDK_APPID)
        tlsHelper = TLSHelper.getInstance().init(mpersenter.context, Constant.SDK_APPID.toLong())
    }

    override fun onBackPressed() {
        if (RigsterFragment.tag == "RigsterFragment" || ResetFrament.tag == "ResetFrament") {
            if (!RigsterFragment.isVisible && !ResetFrament.isVisible) {
//                toast("再次点击退出程序")
//                count++
//                Handler().postDelayed({ count = 0 }, 2000)
//                if (count == 2) {
//                    finish()
//                }
                super.onBackPressed()
            } else {
                val Transaction = supportFragmentManager.beginTransaction()
                Transaction.hide(RigsterFragment)
                Transaction.hide(ResetFrament)
                Transaction.commit()
                loginbutton.isVisible=true
            }

        }


    }

    fun navToHome() {
        //登录之前要初始化群和好友关系链缓存
        var userConfig = TIMUserConfig()
        userConfig.setUserStatusListener(object : TIMUserStatusListener {
            override fun onForceOffline() {
                Log.d("iiiiii", "receive force offline message")

            }

            override fun onUserSigExpired() {
                //票据过期，需要重新登录
                NotifyDialog().show(getString(study.kotin.my.baselibrary.R.string.tls_expire), supportFragmentManager) { dialog, which ->
                    //                             logout()
                }
            }
        }).connectionListener = object : TIMConnListener {
            override fun onConnected() {
                Log.i("iiiiiiii", "onConnected")
            }

            override fun onDisconnected(code: Int, desc: String) {
                Log.i("iiiiiiiiiiiiiiiii", "onDisconnected")
            }

            override fun onWifiNeedAuth(name: String) {
                Log.i("iiiiiiiiiiiiiiiii", "onWifiNeedAuth")
            }
        }

        //设置刷新监听
        RefreshEvent.getInstance().init(userConfig)
        userConfig = FriendshipEvent.getInstance().init(userConfig)
        userConfig = GroupEvent.getInstance().init(userConfig)
        userConfig = MessageEvent.getInstance().init(userConfig)
        TIMManager.getInstance().userConfig = userConfig
    }
}