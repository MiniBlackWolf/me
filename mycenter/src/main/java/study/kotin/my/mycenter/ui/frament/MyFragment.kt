package study.kotin.my.mycenter.ui.frament

import android.content.Context
import android.media.Image
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
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.util.Log
import android.view.Display
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tencent.imsdk.*
import org.jetbrains.anko.support.v4.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.injection.commponent.DaggerMyCommponent
import study.kotin.my.mycenter.injection.module.Mymodule
import study.kotin.my.mycenter.persenter.view.MyView
import study.kotin.my.mycenter.ui.activity.*
import java.util.ArrayList


class MyFragment : BaseMVPFragmnet<Mypersenter>(), View.OnClickListener, MyView {
    override fun changePasswordreslut(t: BaseResp<String>) {
    }

    override fun Logoutreslut(t: BaseResp<String>) {
        if (t.success) {
            TIMManager.getInstance().logout(object : TIMCallBack {
                override fun onError(p0: Int, p1: String?) {
                    toast("退出失败")
                }

                override fun onSuccess() {
                    activity!!.getSharedPreferences("UserAcc", Context.MODE_PRIVATE).edit().clear().apply()
                    activity!!.getSharedPreferences("LongTimeData", Context.MODE_PRIVATE).edit().clear().apply()
                    ARouter.getInstance().build("/usercenter/RegisterActivity").navigation()
                }
            })
        } else {
            toast(t.message)
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
    lateinit var m11: TextView
    lateinit var pot: ImageView
    lateinit var name: TextView
    lateinit var sige: TextView
    override fun onClick(v: View?) {
        if (TIMManager.getInstance().loginUser == "") {
            toast("请先登录")
            ARouter.getInstance().build("/usercenter/RegisterActivity").navigation()
            return
        }
        when (v!!.id) {
            R.id.m1 -> activity!!.startActivity<MyClassActivity>()
            R.id.m2 -> ARouter.getInstance().build("/address/PublicGroupActivity").navigation()
            R.id.m3 -> activity!!.startActivity<ResumeActivity>()
            R.id.m4 -> activity!!.startActivity<AllSettingActivity>()
            R.id.m5 -> activity!!.startActivity<VersionCheckActivity>()
//            R.id.m6 ->activity!!.startActivity<>()
            R.id.m7 -> switchid()
            R.id.m8 -> activity!!.startActivity<MyActivity>()
            R.id.m9 -> {
                mpersenter.Logout()
            }
            //   R.id.m11 -> activity!!.startActivity<PersonnelActivity>()
            //          R.id.m10 -> ActivityUtils.finishAllActivities()
            R.id.pot -> {
                ARouter.getInstance().build("/home/PersonalhomeActivity").withString("id", TIMManager.getInstance().loginUser).navigation()
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val View = inflater.inflate(R.layout.mylayout, container, false)
        initdagger()
        initOnClickListener(View)
        if (TIMManager.getInstance().loginUser == "") {
            name.text = "点击登录"
            sige.text = "登录后享受精彩世界"
            return View
        }
        changid()
        TIMFriendshipManager.getInstance().getSelfProfile(object : TIMValueCallBack<TIMUserProfile> {
            override fun onError(p0: Int, p1: String?) {

            }

            override fun onSuccess(p0: TIMUserProfile?) {
                val edit = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                if (p0 == null) return
                edit.putString("myname", p0.nickName)
                edit.putString("myface", p0.faceUrl)
                edit.putString("mselfSignature", p0.selfSignature)
                edit.apply()
                name.text = p0.nickName
                sige.text = p0.selfSignature
                val options = RequestOptions()
                        .error(R.drawable.a4_2)
                Glide.with(this@MyFragment)
                        .load(p0.faceUrl)
                        .apply(options)
                        .into(pot)
            }
        })
        val edit = activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val string = edit.getBoolean("status", true)
        if (string == true) {
            m7.setText("切换身份-求职者")
            m3.setOnClickListener(this)

        } else {
            m7.setText("切换身份-企业")
            m3.setOnClickListener { activity!!.startActivity<PersonnelActivity>() }
        }
        return View
    }

    fun initdagger() {
        DaggerMyCommponent.builder().activityCommpoent(mActivityComponent).mymodule(Mymodule()).build().inject(this)
        mpersenter.mView = this

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
        //    m3.setOnClickListener(this)
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
//        m11 = View.find(R.id.m11)
//        m11.setOnClickListener(this)
        pot = View.find(R.id.pot)
        pot.setOnClickListener(this)
        name = View.find(R.id.name)
        sige = View.find(R.id.sige)
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
        val edit = activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
        view.find<ImageView>(R.id.q1).setOnClickListener {
            Bus.send(UpdateChangIdEvent(true))
            edit.putBoolean("status", false)
            edit.apply()
            Alert.dismiss()
        }
        view.find<ImageView>(R.id.q2).setOnClickListener {
            Bus.send(UpdateChangIdEvent(false))
            edit.putBoolean("status", true)
            edit.apply()
            Alert.dismiss()
        }

    }

    override fun onResume() {
        super.onResume()
        if (TIMManager.getInstance().loginUser == "") {
            name.text = "点击登录"
            sige.text = "登录后享受精彩世界"
            pot.setImageResource(R.drawable.a4_2)
        }

    }
}