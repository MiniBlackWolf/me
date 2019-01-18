package study.kotin.my.find.ui.Activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.nearbylayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.R
import study.kotin.my.find.adapter.nearbyadapter
import study.kotin.my.find.data.neardata
import study.kotin.my.find.injection.commponent.DaggerFindCommponent
import study.kotin.my.find.injection.module.findmodule
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.presenter.view.Findview

/**
 * Creat by blackwolf
 * 2019/1/18
 * system username : Administrator
 */
class nearbyActivity : BaseMVPActivity<Findpresenter>(), Findview {
    override fun findByNear(t: List<neardata>) {
        nearbyadapter.addData(t)
    }

    val nearbyadapter by lazy { nearbyadapter(ArrayList()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nearbylayout)
        initinject()
        rc.adapter = nearbyadapter
        rc.layoutManager = LinearLayoutManager(this)
        val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
        if (jwt != "") {
            mpersenter.findByNear("Bearer $jwt")
        }
        chf.setOnClickListener{
            finish()
        }
        nearbyadapter.setOnItemClickListener { adapter, view, position ->
            val data = adapter.data as List<neardata>
            ARouter.getInstance().build("/home/PersonalhomeActivity").withString("id",data[position].userid).navigation()
        }
        sm.setEnableRefresh(false)
        sm.setEnableOverScrollDrag(true)
    }

    fun initinject() {
        DaggerFindCommponent.builder().activityCommpoent(activityCommpoent).findmodule(findmodule()).build().inject(this)
        mpersenter.mView = this
    }
}