package study.kotin.my.find.ui.Activity

import android.graphics.Bitmap
import android.os.Bundle
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.recruitlayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.MyWebViewSettings
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter



class RecruitActivity:BaseMVPActivity<Findpresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recruitlayout)
        people.webViewClient= object:WebViewClient(){
            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
            }

            override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                super.onPageStarted(p0, p1, p2)
            }

        }
        val initWeb = MyWebViewSettings.initWeb(people)
        initWeb.loadUrl("http://www.baidu.com")
    }

    override fun onBackPressed() {
        people.goBack()
    }
}

