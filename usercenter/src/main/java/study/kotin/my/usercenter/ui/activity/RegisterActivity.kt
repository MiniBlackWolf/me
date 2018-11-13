package study.kotin.my.usercenter.ui.activity


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
import study.kotin.my.usercenter.common.PwdLoginListener
import study.kotin.my.usercenter.common.RefreshUserSigListener
import study.kotin.my.usercenter.ui.fragment.ResetFrament
import tencent.tls.platform.TLSErrInfo
import tencent.tls.platform.TLSHelper
import tencent.tls.platform.TLSRefreshUserSigListener
import tencent.tls.platform.TLSUserInfo


class RegisterActivity : BaseMVPActivity<registerPersenter>(), registerView {
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



    override fun LoginResult(result: Boolean) {
        if (result) {
            toast("my").show()

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        injectactivity()
        navToHome()
        //----------------------
        try {
            userInfo = tlsHelper.lastUserInfo
            val hasLogin = userInfo != null && !tlsHelper.needLogin(userInfo.identifier)
            if (hasLogin) {
                usersig = tlsHelper.getUserSig(userInfo.identifier)
                tlsHelper.TLSRefreshUserSig(userInfo.identifier, RefreshUserSigListener(userInfo.identifier,usersig,this@RegisterActivity))

            }
        } catch (e: IllegalStateException) {
            Log.e("erorr", "userInfo is null")
           val userConfig  = TIMUserConfig()
                   .groupSettings
        }

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

            val passByte = passworld.text.toString().toByteArray(Charsets.UTF_8)
            tlsHelper.TLSPwdLogin("86-${username.text}", passByte, pwdLoginListener)

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