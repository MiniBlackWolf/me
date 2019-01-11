package study.kotin.my.find.ui.frament

import android.app.Activity
import android.content.Context
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.tencent.imsdk.TIMManager
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import study.kotin.my.baselibrary.common.baseurl.Companion.url
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.baselibrary.utils.MyWebViewSettings
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.presenter.view.Findview
import study.kotin.my.find.ui.Activity.FriendDtActivity
import study.kotin.my.find.ui.Activity.RecruitActivity
import study.kotin.my.find.ui.common.MyScrollView
import study.kotin.my.find.ui.common.mywebview
import com.tencent.smtt.sdk.QbSdk.getSettings
import okhttp3.Cookie
import okhttp3.HttpUrl
import study.kotin.my.baselibrary.data.net.webset


class Findfragment : BaseMVPFragmnet<Findpresenter>(), Findview,View.OnClickListener {
    override fun onClick(v: View?) {
        if(TIMManager.getInstance().loginUser==""){
            toast("请先登录")
            ARouter.getInstance().build("/usercenter/RegisterActivity").navigation()
            return
        }
        when(v!!.id){
            R.id.people->{startActivity<RecruitActivity>()}
            R.id.frienddt->{startActivity<FriendDtActivity>()}
            R.id.publicgroups->{ ARouter.getInstance().build("/address/PublicGroupActivity").navigation() }
        }
    }
    lateinit var WebView:WebView
    var trun = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.findlayout, container, false)
//         WebView = view.find<WebView>(R.id.webview)
//       WebView.webViewClient= object:WebViewClient(){
//           override fun onPageFinished(p0: WebView?, p1: String?) {
//               if (trun) {
//                   val httpUrl= HttpUrl.get(p1)
//                   val jwt = activity!!.getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
//                   val string1 = activity!!.getSharedPreferences("userCookie", Context.MODE_PRIVATE).getString("madengwang.com", "")
//                   val gson = Gson()
//                   val list = gson.fromJson(string1,object: TypeToken<List<Cookie>>(){}.type) as List<Cookie>
//                   if(jwt==""&&list[0].value()==""){
//                       return
//                   }
//                   val edit = activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
//                   val string = edit.getBoolean("status", true)
//                   val j=if(string)"1" else "2"
//                   p0!!.evaluateJavascript("javascript:setdata('${list[0].value()}','$jwt','3',$j)") { its ->
//                       Log.i("iiiiiiiiiiiii", its)
//                   }
//                   trun = false
//               }
//           }
//       }
//        val initWeb = MyWebViewSettings.initWeb(WebView,activity as Activity)
//        initWeb.loadUrl(resources.getString(R.string.jobFind))
        view.find<LinearLayout>(R.id.people).setOnClickListener (this)
        view.find<LinearLayout>(R.id.frienddt).setOnClickListener (this)
        view.find<LinearLayout>(R.id.publicgroups).setOnClickListener (this)
        val myscrol = view.find<SmartRefreshLayout>(R.id.myscrol)
//        initWeb.addJavascriptInterface(webset(),"webset")
        myscrol.setEnableRefresh(false)
        myscrol.setEnableOverScrollDrag(true)
        return view

    }
}