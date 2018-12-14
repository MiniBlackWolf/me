package study.kotin.my.baselibrary.utils

import android.R
import android.graphics.Bitmap
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import study.kotin.my.baselibrary.common.BaseApplication

object MyWebViewSettings {
    fun initWeb(webView: WebView): WebView {
        val webSettings = webView.getSettings()

        // 修改ua使得web端正确判断(加标识+++++++++++++++++++++++++++++++++++++++++++++++++++++)
//        String ua = webSettings.getUserAgentString();
//        webSettings.setUserAgentString(ua + "这里是增加的标识");

        // 网页内容的宽度是否可大于WebView控件的宽度
        webSettings.setLoadWithOverviewMode(false);
        // 保存表单数据
        webSettings.setSaveFormData(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);

        webView.requestFocus(); //此句可使html表单可以接收键盘输入
        webView.setFocusable(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        // 启动应用缓存
        webSettings.setAppCacheEnabled(false);
        // 设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置此属性，可任意比例缩放。
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //  页面加载好以后，再放开图片
        //mSettings.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
        // 排版适应屏幕
        webSettings.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        webSettings.setSupportMultipleWindows(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);//JS在HTML里面设置了本地存储localStorage，java中使用localStorage则必须打开
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true); //自适应屏幕

        //以下接口禁止(直接或反射)调用，避免视频画面无法显示：
        //webView.setLayerType();
        webView.setDrawingCacheEnabled(true)
        val mProgressBar = ProgressBar(BaseApplication.context, null, R.attr.progressBarStyleHorizontal)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10)
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setProgress(0)
        webView.addView(mProgressBar)
        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(p0: WebView?, p1: Int) {
                if (p1 == 100) {
                    mProgressBar.isVisible=false
                } else {
                    if (mProgressBar.isVisible==false) mProgressBar.isVisible=true
                    mProgressBar.progress = p1
                }

            }
        }
        return webView


    }

}