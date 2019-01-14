package study.kotin.my.mycenter.ui.activity

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NotificationCompat
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask
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
        Beta.registerDownloadListener(object: DownloadListener{
            override fun onFailed(p0: DownloadTask?, p1: Int, p2: String?) {

            }

            override fun onReceive(p0: DownloadTask) {
                val systemService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val builder = NotificationCompat.Builder(this@VersionCheckActivity, "download")
                builder.setSmallIcon(R.drawable.downloading)
                builder.setContentTitle("下载进度")
                builder.setContentText("${p0.savedLength}/${p0.totalLength}")
                systemService.notify(99,builder.build())
            }

            override fun onCompleted(p0: DownloadTask?) {
            }
        })
        //获取APP版本versionName
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = packageInfo.versionName
        version.text = versionName
        chf.setOnClickListener { finish() }
    }
}