package study.kotin.my.address.ui.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.publicgroup.*
import org.jetbrains.anko.startActivity
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.Addresspersenter.view.AddressView
import study.kotin.my.address.R
import study.kotin.my.address.injection.commponent.DaggerAddressCommponent
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class AddressActivity : BaseMVPActivity<Addresspresenter>(), AddressView,View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.newgroup->startActivity<AddGroupActivity>()
        }
    }

    override fun reslut() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grouplayout)
        iniinject()
        mpersenter.ss()
        newgroup.setOnClickListener(this)
    }

    private fun iniinject() {
        DaggerAddressCommponent.builder().activityCommpoent(activityCommpoent).addressmodule(Addressmodule()).build().inject(this)
        mpersenter.mView=this
    }

}