package study.kotin.my.find.ui.Activity

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.recruitlayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter



class RecruitActivity:BaseMVPActivity<Findpresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recruitlayout)
        people.loadUrl("http://www.baidu.com")
        people.webViewClient= WebViewClient()
        people.webChromeClient= WebChromeClient()
        people.settings.javaScriptEnabled=true

    }
}

