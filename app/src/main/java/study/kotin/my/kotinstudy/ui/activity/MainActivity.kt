package study.kotin.my.kotinstudy.ui.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.example.home.common.UpdateMessgeSizeEvent
import com.example.home.ui.Frament.HomeFarment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import study.kotin.my.address.ui.frament.AddressFrament
import study.kotin.my.find.ui.frament.Findfragment
import study.kotin.my.kotinstudy.R
import study.kotin.my.mycenter.ui.frament.MyFragment
import java.util.*

@Route(path = "/App/Homepage")
class MainActivity : AppCompatActivity() {
    private var pressTime:Long = 0
    //Fragment 栈管理
    private val mStack = Stack<Fragment>()
    //主界面Fragment
    private val mHomeFragment by lazy { HomeFarment() }
    //通讯主界面
    private val mAddressFrament by lazy { AddressFrament() }
    //发现主界面
    private val mFindfragment by lazy { Findfragment() }
   //我的主界面
   private val mMyFragment by lazy { MyFragment()  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }


        initFragment()
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack){
            manager.hide(fragment)
        }
        manager.show(mStack[0])
        manager.commit()
        initBottomNav()
        initObserve()
        loadCartSize(0)
    }
    private fun initObserve(){
        Bus.observe<UpdateMessgeSizeEvent>()
                .subscribe {
                    t:UpdateMessgeSizeEvent ->
                    loadCartSize(t.count)
                }.registerInBus(this)
    }
    private fun loadCartSize(count:Int){
        mBottomNavBar.checkMsgBadge(count)
    }
    /*
       初始化Fragment栈管理
    */
    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.myfarment,mHomeFragment)
        manager.add(R.id.myfarment,mAddressFrament)
        manager.add(R.id.myfarment,mFindfragment)
        manager.add(R.id.myfarment,mMyFragment)
        manager.commit()
        mStack.add(mHomeFragment)
        mStack.add(mAddressFrament)
        mStack.add(mFindfragment)
        mStack.add(mMyFragment)
    }
    private fun initBottomNav(){
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
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
        for (fragment in mStack){
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }


    override fun onBackPressed() {

        val time = System.currentTimeMillis()
        if (time - pressTime > 2000){
            toast("再按一次退出程序")
            pressTime = time
        } else{
//            AppManager.instance.exitApp(this)
            finish()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}
