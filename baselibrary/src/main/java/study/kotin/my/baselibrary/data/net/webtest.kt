package study.kotin.my.baselibrary.data.net

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import com.alibaba.android.arouter.launcher.ARouter

class webtest(val context: Activity, val path: String) {
    @JavascriptInterface
    fun t1() {
        Log.i("iiiiiiiiii", "iiiiiiiiiii")
        // val s= Intent(context,mainsail::class.java)
        // ActivityUtils.startActivities(arrayOf(s))
        // ARouter.getInstance().build(path).navigation()
        context.finish()
    }


}