package study.kotin.my.baselibrary.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.ui.activity.BaseActivity
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class NetBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
            BaseApplication.context.longToast("网络连接已断开")
        }

    }
}