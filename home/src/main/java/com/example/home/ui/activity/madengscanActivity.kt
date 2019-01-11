package com.example.home.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.madengscan.*
import okhttp3.Cookie
import okhttp3.HttpUrl
import study.kotin.my.baselibrary.data.net.webtest
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.MyWebViewSettings

class madengscanActivity:BaseMVPActivity<HomePersenter>() {
    var trun = true
    val id by lazy { intent.extras!!.getString("id") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.madengscan)
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(p0: WebView?, p1: String?) {
                if (trun) {
                    val string = intent.extras!!.getString("code")
                    val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                    p0!!.evaluateJavascript("javascript:sweep('${intent.extras!!.getString("code")}','$jwt','$id')") { its ->
                        Log.i("iiiiiiiiiiiii", its)
                    }
                    trun = false
                }
            }

        }

        val initWeb = MyWebViewSettings.initWeb(webview,this)
        initWeb.loadUrl("http://192.168.1.105:9201/admin/button.html")
        initWeb.addJavascriptInterface(webtest(this,""),"webtest")
    }
}