package study.kotin.my.kotinstudy.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.example.home.common.UpdateMessgeSizeEvent
import com.example.home.ui.Frament.HomeFarment
import com.tencent.imsdk.*
import com.tencent.qcloud.presentation.event.FriendshipEvent
import com.tencent.qcloud.presentation.event.GroupEvent
import com.tencent.qcloud.presentation.event.MessageEvent
import com.tencent.qcloud.presentation.event.RefreshEvent
import com.tencent.qcloud.ui.NotifyDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import study.kotin.my.address.ui.frament.AddressFrament
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.Base64Utils
import study.kotin.my.baselibrary.utils.PushUtil
import study.kotin.my.find.ui.frament.Findfragment
import study.kotin.my.kotinstudy.R
import study.kotin.my.mycenter.ui.frament.MyFragment
import study.kotin.my.usercenter.ui.activity.RegisterActivity
import java.util.*

@Route(path = "/App/Homepage")
class MainActivity : BaseMVPActivity<Mainpersenter>() {
    private var pressTime: Long = 0
    //Fragment 栈管理
    private val mStack = Stack<Fragment>()
    //主界面Fragment
    private val mHomeFragment by lazy { HomeFarment() }
    //通讯主界面
    private val mAddressFrament by lazy { AddressFrament() }
    //发现主界面
    private val mFindfragment by lazy { Findfragment() }
    //我的主界面
    private val mMyFragment by lazy { MyFragment() }

    private fun TIMlogin(user: String, sig: String) {
        TIMManager.getInstance().login(user, sig, object : TIMCallBack {
            override fun onSuccess() {
                val edit = BaseApplication.context.getSharedPreferences("UserAcc", Context.MODE_PRIVATE).edit()
                edit.putString("user", Base64Utils.getBase64(user))
                //  edit.putString("pass", Base64Utils.getBase64(passworld.text.toString()))
                edit.apply()
                PushUtil.instance
                MessageEvent.getInstance()
                ARouter.getInstance().build("/App/Homepage").navigation()
                hideLoading()
                finish()
            }

            override fun onError(p0: Int, p1: String?) {
                Log.i("iiiiiiii", "eeeeeee")
                TIMlogin(user, sig)
                hideLoading()
                toast("登录失败请重试")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navToHome()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        initFragment()
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }
        manager.show(mStack[0])
        manager.commit()
        initBottomNav()
        initObserve()
        loadCartSize(0)
        //自动登录
        if (TIMManager.getInstance().loginUser == "") {
            val sharedPreferences = getSharedPreferences("UserAcc", Context.MODE_PRIVATE)
            val user = sharedPreferences.getString("user", "")
            val sig = sharedPreferences.getString("sig", "")
            if (user != "" && sig != "") {
                showLoading()
                TIMlogin(Base64Utils.getFromBase64(user!!), sig!!)
            }
            // showLoading()
        } else {
            myinfock()
        }
    }

    private fun myinfock() {
        TIMFriendshipManager.getInstance().getSelfProfile(object : TIMValueCallBack<TIMUserProfile> {
            override fun onError(p0: Int, p1: String?) {

            }

            override fun onSuccess(p0: TIMUserProfile?) {
                if (p0 == null) return
                if (p0.nickName == "" || p0.customInfo["Tag_Profile_Custom_school"] == null || p0.customInfo["Tag_Profile_Custom_email"] == null) {
                    toast("清先完善资料")
                    ARouter.getInstance().build("/mycenter/MyActivity").navigation()
                }
            }
        })
    }

    private fun initObserve() {
        Bus.observe<UpdateMessgeSizeEvent>()
                .subscribe { t: UpdateMessgeSizeEvent ->
                    loadCartSize(t.count)
                }.registerInBus(this)
    }

    private fun loadCartSize(count: Int) {
        mBottomNavBar.checkMsgBadge(count)
    }

    /*
       初始化Fragment栈管理
    */
    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.myfarment, mHomeFragment)
        manager.add(R.id.myfarment, mAddressFrament)
        manager.add(R.id.myfarment, mFindfragment)
        manager.add(R.id.myfarment, mMyFragment)
        manager.commit()
        mStack.add(mHomeFragment)
        mStack.add(mAddressFrament)
        mStack.add(mFindfragment)
        mStack.add(mMyFragment)
    }

    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
        })


    }

    /*
        切换Tab，切换对应的Fragment
     */
    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }


    override fun onBackPressed() {

        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
//            AppManager.instance.exitApp(this)
            finish()
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

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}
