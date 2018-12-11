package study.kotin.my.find.ui.Activity

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
                    p0!!.evaluateJavascript("javascript:setdata('${list[0].value()}','$jwt','3')") { its ->
                        Log.i("iiiiiiiiiiiii", its)
                    }
                    trun = false
                }
            }

        }
        val initWeb = MyWebViewSettings.initWeb(people)
        initWeb.loadUrl(resources.getString(R.string.jobFind))
        initWeb.addJavascriptInterface(webtest(this,""),"webtest")

    }

    override fun onBackPressed() {
        if(people.url=="http://madengwang.com:9200/admin/job/jobFind.html"){
            super.onBackPressed()
        }else {
            people.goBack()
        }
    }
}

