package study.kotin.my.address.ui.activity

import android.os.Bundle
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.Addresspersenter.view.AddressView
import study.kotin.my.address.R
import study.kotin.my.address.injection.commponent.DaggerAddressCommponent
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class AddressActivity : BaseMVPActivity<Addresspresenter>(), AddressView {
    override fun reslut() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addressmain)
        iniinject()
        mpersenter.ss()
    }

    private fun iniinject() {
        DaggerAddressCommponent.builder().activityCommpoent(activityCommpoent).addressmodule(Addressmodule()).build().Addressinject(this)
        mpersenter.mView=this
    }

}