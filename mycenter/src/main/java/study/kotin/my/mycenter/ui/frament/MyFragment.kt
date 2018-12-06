package study.kotin.my.mycenter.ui.frament

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import org.jetbrains.anko.AlertBuilder
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.common.UpdateChangIdEvent
import study.kotin.my.mycenter.persenter.Mypersenter
import study.kotin.my.mycenter.ui.activity.MyActivity
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.Display
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.injection.commponent.DaggerMyCommponent
import study.kotin.my.mycenter.injection.module.Mymodule
import study.kotin.my.mycenter.persenter.view.MyView
import study.kotin.my.mycenter.ui.activity.AllSettingActivity
import study.kotin.my.mycenter.ui.activity.MyClassActivity
import study.kotin.my.mycenter.ui.activity.VersionCheckActivity


class MyFragment : BaseMVPFragmnet<Mypersenter>(), View.OnClickListener,MyView {
    override fun Logoutreslut(t: BaseResp<String>) {
        if(t.success){
            activity!!.getSharedPreferences("UserAcc",Context.MODE_PRIVATE).edit().clear().apply()
            ARouter.getInstance().build("/usercenter/RegisterActivity").navigation()
            activity!!.finish()
        }
    }

    lateinit var m1: TextView
    lateinit var m2: TextView
    lateinit var m3: TextView
    lateinit var m4: TextView
    lateinit var m5: TextView
    lateinit var m6: TextView
    lateinit var m7: TextView
    lateinit var m8: TextView
    lateinit var m9: TextView
    lateinit var m10: TextView
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.m1 -> activity!!.startActivity<MyClassActivity>()
            R.id.m2 -> ARouter.getInstance().build("/address/PublicGroupActivity").navigation()
//            R.id.m3 ->activity!!.startActivity<>()
            R.id.m4 -> activity!!.startActivity<AllSettingActivity>()
            R.id.m5 -> activity!!.startActivity<VersionCheckActivity>()
//            R.id.m6 ->activity!!.startActivity<>()
            R.id.m7 -> switchid()
            R.id.m8 -> activity!!.startActivity<MyActivity>()
            R.id.m9 -> {
                mpersenter.Logout()
            }
            //          R.id.m10 -> ActivityUtils.finishAllActivities()

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val View = inflater.inflate(R.layout.mylayout, container, false)
        initdagger()
        initOnClickListener(View)
        changid()

        return View
    }

    fun initdagger() {
        DaggerMyCommponent.builder().activityCommpoent(mActivityComponent).mymodule(Mymodule()).build().inject(this)
        mpersenter.mView=this

    }

    fun changid() {
        Bus.observe<UpdateChangIdEvent>()
                .subscribe { t: UpdateChangIdEvent ->
                    if (t.idname) m7.setText("切换身份-企业")
                    else m7.setText("切换身份-求职者")
                }.registerInBus(this)
    }

    private fun initOnClickListener(View: View) {
        m1 = View.find(R.id.m1)
        m1.setOnClickListener(this)
        m2 = View.find(R.id.m2)
        m2.setOnClickListener(this)
        m3 = View.find(R.id.m3)
        m3.setOnClickListener(this)
        m4 = View.find(R.id.m4)
        m4.setOnClickListener(this)
        m5 = View.find(R.id.m5)
        m5.setOnClickListener(this)
        m6 = View.find(R.id.m6)
        m6.setOnClickListener(this)
        m7 = View.find(R.id.m7)
        m7.setOnClickListener(this)
        m8 = View.find(R.id.m8)
        m8.setOnClickListener(this)
        m9 = View.find(R.id.m9)
        m9.setOnClickListener(this)
        m10 = View.find(R.id.m10)
        m10.setOnClickListener(this)
    }

    private fun switchid() {
        val view = layoutInflater.inflate(R.layout.my_main_item, null)
        val builder = AlertDialog.Builder(activity as Context)
        val Alert = builder.create()
        val manager = activity!!.getWindowManager()
        val display = manager.getDefaultDisplay()
        val width = display.getWidth()
        val height = display.getHeight()
        Alert.show()
        Alert.window.setLayout(width / 1.2.toInt(), height / 3)
        Alert.window.setContentView(view)
        view.find<ImageView>(R.id.q1).setOnClickListener {
            Bus.send(UpdateChangIdEvent(true))
            Alert.dismiss()
        }
        view.find<ImageView>(R.id.q2).setOnClickListener {
            Bus.send(UpdateChangIdEvent(false))
            Alert.dismiss()
        }


    }
}