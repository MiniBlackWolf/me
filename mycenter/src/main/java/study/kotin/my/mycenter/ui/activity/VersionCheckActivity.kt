package study.kotin.my.mycenter.ui.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.versioncheck.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.persenter.Mypersenter

class VersionCheckActivity:BaseMVPActivity<Mypersenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.versioncheck)
        chf.setOnClickListener { finish() }
    }
}