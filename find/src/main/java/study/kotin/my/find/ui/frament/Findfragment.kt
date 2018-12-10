package study.kotin.my.find.ui.frament

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import study.kotin.my.baselibrary.common.baseurl.Companion.url
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.presenter.view.Findview
import study.kotin.my.find.ui.Activity.RecruitActivity

class Findfragment : BaseMVPFragmnet<Findpresenter>(), Findview {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.findlayout, container, false)
        val WebView = view.find<WebView>(R.id.webview)
//        WebView.webViewClient= WebViewClient()
        val settings = WebView.settings
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setSupportZoom(true);//是否可以缩放，默认true
        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(true);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        WebView.webChromeClient =WebChromeClient()
        WebView.webViewClient=object :WebViewClient(){
            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
            }
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler!!.proceed()
              //  super.onReceivedSslError(view, handler, error)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.i("iiiiiiiii",url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Log.i("iiiiiiiii",error.toString())
            }
        }
        WebView.clearCache(true)
        WebView.clearHistory()
        WebView.loadUrl("http://www.baidu.com")
        WebView.reload()
        view.find<LinearLayout>(R.id.people).setOnClickListener {
            WebView.reload()
            startActivity<RecruitActivity>()
        }
        return view

    }
}