package study.kotin.my.find.ui.frament

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import study.kotin.my.baselibrary.common.baseurl.Companion.url
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.baselibrary.utils.MyWebViewSettings
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.presenter.view.Findview
import study.kotin.my.find.ui.Activity.FriendDtActivity
import study.kotin.my.find.ui.Activity.RecruitActivity

class Findfragment : BaseMVPFragmnet<Findpresenter>(), Findview {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.findlayout, container, false)
        val WebView = view.find<WebView>(R.id.webview)
//        WebView.webViewClient= WebViewClient()
        val initWeb = MyWebViewSettings.initWeb(WebView)
        initWeb.webViewClient=WebViewClient()
        initWeb.loadUrl("http://www.baidu.com")
        view.find<LinearLayout>(R.id.people).setOnClickListener {
            startActivity<RecruitActivity>()
        }
        view.find<LinearLayout>(R.id.frienddt).setOnClickListener {
            startActivity<FriendDtActivity>()
        }
        return view

    }
}