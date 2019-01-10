package study.kotin.my.baselibrary.data.net

import android.util.Log
import android.webkit.JavascriptInterface
import com.alibaba.android.arouter.launcher.ARouter

class webset {
    @JavascriptInterface
    fun yz(id: String) {
        Log.i("iiiiii",id)
        ARouter.getInstance().build("/home/HomeActivity").withString("id", id).navigation()
    }
}