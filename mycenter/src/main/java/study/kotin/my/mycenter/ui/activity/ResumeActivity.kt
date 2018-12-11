package study.kotin.my.mycenter.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.resumelayout.*
import okhttp3.Cookie
import okhttp3.HttpUrl
import study.kotin.my.baselibrary.data.net.webtest
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.MyWebViewSettings
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.persenter.Mypersenter

class ResumeActivity:BaseMVPActivity<Mypersenter>() {
    var trun = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resumelayout)
        webview.webViewClient= object : WebViewClient() {
            override fun onPageFinished(p0: WebView?, p1: String?) {
                 if (trun) {
                    val httpUrl= HttpUrl.get(p1)
                    val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                    val string1 = getSharedPreferences("userCookie", Context.MODE_PRIVATE).getString(httpUrl.host(), "")
                    val gson = Gson()
                    val list = gson.fromJson(string1,object: TypeToken<List<Cookie>>(){}.type) as List<Cookie>
                    p0!!.evaluateJavascript("javascript:setdata('${list[0].value()}','$jwt','1')") { its ->
                        Log.i("iiiiiiiiiiiii", its)
                    }
                    trun=false
                }
            }

        }
        val initWeb = MyWebViewSettings.initWeb(webview)
        initWeb.loadUrl(resources.getString(R.string.jobFind))
        initWeb.addJavascriptInterface(webtest(this,""),"webtest")

    }
}