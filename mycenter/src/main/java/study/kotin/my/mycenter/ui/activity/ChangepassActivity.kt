package study.kotin.my.mycenter.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.changepasslayout.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.passverify
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.common.SoftHideKeyBoardUtil
import study.kotin.my.mycenter.injection.commponent.DaggerMyCommponent
import study.kotin.my.mycenter.injection.module.Mymodule
import study.kotin.my.mycenter.persenter.Mypersenter
import study.kotin.my.mycenter.persenter.view.MyView

class ChangepassActivity : BaseMVPActivity<Mypersenter>(), MyView {
    override fun Logoutreslut(t: BaseResp<String>) {

    }

    override fun changePasswordreslut(t: BaseResp<String>) {
        if (t.success) {
            toast("密码修改成功!")
            getSharedPreferences("UserAcc", Context.MODE_PRIVATE).edit().clear().apply()
            ARouter.getInstance().build("/usercenter/RegisterActivity").navigation()
            finish()
        } else {
            toast(t.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.changepasslayout)
        initinject()
        chf.setOnClickListener { finish() }
        ok.setOnClickListener {
            val oldpass = oldpass.text.toString()
            val newpass = newpass.text.toString()
            if (oldpass == "") {
                toast("旧密码不能是空")
                return@setOnClickListener
            }
            if (newpass == "") {
                toast("新密码不能是空")
                return@setOnClickListener
            }
            if (!it.passverify(newpass, this)) {
                return@setOnClickListener
            }
            val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
            if (jwt == "") {
                toast("修改密码失败")
                return@setOnClickListener
            }
            mpersenter.changePassword("Bearer " + jwt!!, oldpass, newpass)
        }
    }

    fun initinject() {
        DaggerMyCommponent.builder().activityCommpoent(activityCommpoent).mymodule(Mymodule()).build().inject(this)
        mpersenter.mView = this
    }
}