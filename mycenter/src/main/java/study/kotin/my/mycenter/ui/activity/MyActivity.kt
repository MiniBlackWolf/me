package study.kotin.my.mycenter.ui.activity

import android.os.Bundle
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.injection.commponent.DaggerMyCommponent
import study.kotin.my.mycenter.injection.module.Mymodule
import study.kotin.my.mycenter.persenter.Mypersenter
import study.kotin.my.mycenter.persenter.view.MyView
import javax.inject.Inject

class MyActivity : BaseMVPActivity<Mypersenter>(),MyView {
    override fun reslut() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_main)
        initinject()
        mpersenter.mView=this
        mpersenter.s()

    }
    fun initinject(){
        DaggerMyCommponent.builder().activityCommpoent(activityCommpoent).mymodule(Mymodule()).build().inject(this)

    }

}