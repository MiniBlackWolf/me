package com.example.home.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.madenghelprlayout.*
import study.kotin.my.baselibrary.data.net.webtest
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.MyWebViewSettings

class madenghelprinfoactivity:BaseMVPActivity<HomePersenter>() {
    val id by lazy { intent.extras!!.getString("id") }
    val type by lazy { intent.extras!!.getString("type") }
    var trun = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.madenghelprlayout)
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(p0: WebView?, p1: String?) {
                if (trun) {
//                    val string = intent.extras!!.getString("code")
//                    val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                    p0!!.evaluateJavascript("javascript:aa('$id')") { its ->
                        Log.i("iiiiiiiiiiiii", its)
                    }
                    trun = false
                }
            }

        }

        val initWeb = MyWebViewSettings.initWeb(webview,this)
        if(type=="1"){
            initWeb.loadUrl("http://madengwang.com/admin/others/zs.html")
        }else{
            initWeb.loadUrl("http://madengwang.com/admin/others/active.html")
        }
        initWeb.addJavascriptInterface(webtest(this,""),"webtest")
    }
}