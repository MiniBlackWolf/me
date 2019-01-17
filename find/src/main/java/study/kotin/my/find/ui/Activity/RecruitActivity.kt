package study.kotin.my.find.ui.Activity

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.recruitlayout.*
import okhttp3.Cookie
import okhttp3.HttpUrl
import study.kotin.my.baselibrary.data.net.webtest
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.MyWebViewSettings
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter
import android.content.ClipData
import android.app.Activity
import android.content.Intent
import android.annotation.TargetApi
import android.os.Build
import study.kotin.my.baselibrary.data.net.webset


class RecruitActivity : BaseMVPActivity<Findpresenter>() {
    var trun = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recruitlayout)
        people.webViewClient = object : WebViewClient() {
            override fun onPageFinished(p0: WebView?, p1: String?) {
                if (trun) {
                    val httpUrl=HttpUrl.get(p1)
                    val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                    val string1 = getSharedPreferences("userCookie", Context.MODE_PRIVATE).getString(httpUrl.host(), "")
                    val gson = Gson()
                    val list = gson.fromJson(string1,object: TypeToken<List<Cookie>>(){}.type) as List<Cookie>
                    if(jwt==""&&list[0].value()==""){
                        return
                    }
                    val edit = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    val string = edit.getBoolean("status", true)
                    val j=if(string)"1" else "2"
                    p0!!.evaluateJavascript("javascript:setdata('${list[0].value()}','$jwt','3',$j)") { its ->
                        Log.i("iiiiiiiiiiiii", its)
                    }
                    trun = false
                }
            }

        }

        val initWeb = MyWebViewSettings.initWeb(people,this)
        initWeb.loadUrl(resources.getString(R.string.jobFind))
        initWeb.addJavascriptInterface(webtest(this,""),"webtest")
        initWeb.addJavascriptInterface(webset(),"webset")

    }
    override fun onBackPressed() {
        if(people.url=="http://madengwang.com/admin/job/jobFind.html"){
            super.onBackPressed()
        }else {
            people.goBack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (null == MyWebViewSettings.uploadMessage && null == MyWebViewSettings.uploadMessageAboveL) return
            val result = if (data == null || resultCode != Activity.RESULT_OK) null else data.data
            if (MyWebViewSettings.uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data)
            } else if (MyWebViewSettings.uploadMessage != null) {
                MyWebViewSettings.uploadMessage!!.onReceiveValue(result)
                MyWebViewSettings.uploadMessage = null
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onActivityResultAboveL(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != 1 || MyWebViewSettings.uploadMessageAboveL == null) return
        var results: Array<Uri>? = null
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                val dataString = intent.dataString
                val clipData = intent.clipData
                if (clipData != null) {
                    results = arrayOf(clipData.itemCount) as Array<Uri>
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        results[i] = item.uri
                    }
                }
                if (dataString != null)
                    results = arrayOf(Uri.parse(dataString))
            }
        }
        MyWebViewSettings.uploadMessageAboveL?.onReceiveValue(results)
        MyWebViewSettings.uploadMessageAboveL = null
    }

}

