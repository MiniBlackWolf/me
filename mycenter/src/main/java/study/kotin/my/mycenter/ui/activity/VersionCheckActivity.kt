package study.kotin.my.mycenter.ui.activity

import android.os.Bundle
import android.os.Handler
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.versioncheck.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.persenter.Mypersenter

class VersionCheckActivity : BaseMVPActivity<Mypersenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.versioncheck)
        ckversion.setOnClickListener {
            showLoading()
            Handler().postDelayed({
                Beta.checkUpgrade()
                hideLoading()
                if (Beta.getUpgradeInfo() == null) {
                    ckversion_1.text = "已经是最新版本"
                    ckversion.isClickable=false
                }
            }, 2000)
        }
        //获取APP版本versionName
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = packageInfo.versionName
        version.text = versionName
        chf.setOnClickListener { finish() }
    }
}