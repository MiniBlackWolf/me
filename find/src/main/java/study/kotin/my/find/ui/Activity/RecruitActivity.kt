package study.kotin.my.find.ui.Activity

import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.recruitlayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.R

import study.kotin.my.find.presenter.Findpresenter
import android.webkit.JavascriptInterface



class RecruitActivity:BaseMVPActivity<Findpresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recruitlayout)
        people.loadUrl("http://192.168.1.101:9002/admin/job/jobFind.html")
        people.webViewClient= WebViewClient()
        people.settings.javaScriptEnabled=true
        people.addJavascriptInterface(JSInterface(),"")
    }
}

class JSInterface {

    @JavascriptInterface
    fun methodA() {
    }

    @JavascriptInterface
    fun methodB(webMessage: String) {
    }
}