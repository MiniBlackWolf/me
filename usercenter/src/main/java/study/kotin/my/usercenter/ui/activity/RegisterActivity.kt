package study.kotin.my.usercenter.ui.activity



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
import com.alibaba.android.arouter.launcher.ARouter


class RegisterActivity : BaseMVPActivity<registerPersenter>(), registerView {
    var count=0
    @Inject
    lateinit var  RigsterFragment:RigsterFragment


    override fun LoginResult(result: Boolean) {
        if (result) {
            toast("my").show()

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        injectactivity()
        changFragment()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        //注册方法
        register.setOnClickListener{
            val Transaction = supportFragmentManager.beginTransaction()
            Transaction.show(RigsterFragment)
            Transaction.commit()
            loginbutton.visibility=View.INVISIBLE

        }
        //登陆方法
        loginbutton.setOnClickListener{
            mpersenter.mView=this
            mpersenter.Login(username.text.toString(),passworld.text.toString())
            ARouter.getInstance().build("/App/Homepage").navigation()
            this.finish()
        }


    }
//切换页面
private fun changFragment() {
    val Transaction = supportFragmentManager.beginTransaction()
        Transaction.add(R.id.s, RigsterFragment, "RigsterFragment")
        Transaction.hide(RigsterFragment)
        Transaction.commit()
    }

    private fun injectactivity() {
        DaggerUserCommponent.builder().activityCommpoent(activityCommpoent).userModule(UserModule()).build().inject(this)
        mpersenter.mView = this
    }

    override fun onBackPressed() {

        if(RigsterFragment.tag=="RigsterFragment"){
            if(!RigsterFragment.isVisible){
                toast("再次点击退出程序")
                count++
                Handler().postDelayed({ count=0 },2000)
                if(count==2){
                    finish()
            }
        }else {
                val Transaction = supportFragmentManager.beginTransaction()
                Transaction.hide(RigsterFragment)
                Transaction.commit()
                loginbutton.visibility=View.VISIBLE
            }

        }

    }
}