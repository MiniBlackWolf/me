package study.kotin.my.usercenter.ui.activity


import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.usercenter.R
import study.kotin.my.usercenter.injection.commponent.DaggerUserCommponent
import study.kotin.my.usercenter.injection.module.UserModule
import study.kotin.my.usercenter.persenter.registerPersenter
import study.kotin.my.usercenter.persenter.view.registerView
import study.kotin.my.usercenter.ui.fragment.RigsterFragment
import javax.inject.Inject
import android.view.WindowManager
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.core.widget.toast
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.imsdk.*
import com.tencent.qalsdk.QALSDKManager
import com.tencent.qcloud.presentation.business.LoginBusiness
import com.tencent.qcloud.presentation.event.FriendshipEvent
import com.tencent.qcloud.presentation.event.GroupEvent
import com.tencent.qcloud.presentation.event.MessageEvent
import com.tencent.qcloud.presentation.event.RefreshEvent
import com.tencent.qcloud.sdk.Constant
import com.tencent.qcloud.ui.NotifyDialog
import org.json.JSONObject
import retrofit2.Response
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.utils.Base64Utils
import study.kotin.my.baselibrary.utils.PushUtil
import study.kotin.my.usercenter.common.PwdLoginListener
import study.kotin.my.usercenter.common.RefreshUserSigListener
import study.kotin.my.usercenter.ui.fragment.ResetFrament
import tencent.tls.platform.TLSErrInfo
import tencent.tls.platform.TLSHelper
import tencent.tls.platform.TLSRefreshUserSigListener
import tencent.tls.platform.TLSUserInfo


class RegisterActivity : BaseMVPActivity<registerPersenter>(), registerView {
    override fun RegistResult(result: BaseResp<String>) {
    }

    override fun sendSms(result: BaseResp<String>) {
    }


    lateinit var userInfo: TLSUserInfo
    // val publickey = "nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEEwPS+nyBWgVYxxUbNcI5bQtN33OZ\\n9JjpUbmotPfkfGty3R4I9j4KoiVLXfY2m986TTK5w1yWbB3AURvSVnPOtA=="
    lateinit var tlsHelper: TLSHelper
    var count = 0
    @Inject
    lateinit var RigsterFragment: RigsterFragment
    @Inject
    lateinit var ResetFrament: ResetFrament
    @Inject
    lateinit var pwdLoginListener: PwdLoginListener
    var usersig: String = ""


    override fun LoginResult(result: Response<BaseResp<String>>) {
        if(result.body()==null){
            toast("账号和密码错误")
            hideLoading()
            return
        }
        val edit = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).edit()
        edit.putString("sig", Base64Utils.getBase64(result.body()!!.sig))
        edit.putString("jwt", Base64Utils.getBase64(result.body()!!.jwt))
        edit.apply()
        val fromBase64 = Base64Utils.getFromBase64(result.body()!!.jwt)
        val length = fromBase64.length
        val indexOf = fromBase64.indexOf("user_name")
        val substring = fromBase64.substring(fromBase64.indexOf("name")+7, fromBase64.indexOf("name") + 13)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        injectactivity()
        navToHome()
        val sharedPreferences = getSharedPreferences("UserAcc", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("user", "")
        val sig = sharedPreferences.getString("sig", "")
        if (user != "" && sig != "") {
            showLoading()
            TIMlogin(Base64Utils.getFromBase64(user!!),Base64Utils.getFromBase64(sig))
        }

        //----------------------
//        try {
//            userInfo = tlsHelper.lastUserInfo
//            val hasLogin = userInfo != null && !tlsHelper.needLogin(userInfo.identifier)
//            if (hasLogin) {
//                showLoading()
//                usersig = tlsHelper.getUserSig(userInfo.identifier)
        //            tlsHelper.TLSRefreshUserSig(userInfo.identifier, RefreshUserSigListener(userInfo.identifier,usersig,this@RegisterActivity))
//
//            }
//        } catch (e: IllegalStateException) {
//            Log.e("erorr", "userInfo is null")
//           val userConfig  = TIMUserConfig()
//                   .groupSettings
//        }
        //tlsHelper.TLSRefreshUserSig("admin", RefreshUserSigListener("test","eJw1j8FygjAURf*FbTslCJHQHS3U4kgt1UHoJhPNi43UgBhngE7-vTYj23MW99wfa71YPbCmkZwyTd2WW48Wsu4Nhq6RLVAmNLRX7GCMJwiNVnJQWgppHONHqW7iLPdXksblc5JFNiKnbVGJzRxSBEF*l0-LxfD9Usenp1mBQxX37*ewJ5kdJl-hktS*Wwp4zfJP1R2qw9ZO3tZzkXqD2s*yFQ*Gj01XLKPoMo7xipr8-0APIQeTieffpJZHMOGeG-hk6ozpbLerL0pT3Tdg-v7*AQwrUBo_",this@RegisterActivity))
        // 获取所有已登录用户
        //----------------
        changFragment()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        //注册方法
        register.setOnClickListener {
            val Transaction = supportFragmentManager.beginTransaction()
            Transaction.show(RigsterFragment)
            Transaction.commit()
            loginbutton.visibility = View.INVISIBLE

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
            //          val passByte = passworld.text.toString().toByteArray(Charsets.UTF_8)
            mpersenter.Login(username.text.toString(), passworld.text.toString())
//            tlsHelper.TLSPwdLogin("86-${username.text}", passByte, pwdLoginListener)

            //            mpersenter.mView=this
//            mpersenter.Login(username.text.toString(),passworld.text.toString())
//            ARouter.getInstance().build("/App/Homepage").navigation()
//            this.finish()
        }

        romve.setOnClickListener {
            loginbutton.visibility = View.INVISIBLE
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
                toast("再次点击退出程序")
                count++
                Handler().postDelayed({ count = 0 }, 2000)
                if (count == 2) {
                    finish()
                }
            } else {
                val Transaction = supportFragmentManager.beginTransaction()
                Transaction.hide(RigsterFragment)
                Transaction.hide(ResetFrament)
                Transaction.commit()
                loginbutton.visibility = View.VISIBLE
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