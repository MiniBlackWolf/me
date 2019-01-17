package study.kotin.my.baselibrary.ui.activity


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.*
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SnackbarUtils
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.baselibrary.injection.commponent.AppCommpoent
import study.kotin.my.baselibrary.injection.commponent.DaggerActivityCommpoent
import study.kotin.my.baselibrary.injection.module.ActivityModule
import study.kotin.my.baselibrary.injection.module.LifecycleProviderModule
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import javax.inject.Inject
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
import org.jetbrains.anko.contentView
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.widgets.BottomNavBar


@Suppress("DEPRECATION")
open class BaseMVPActivity<T : Basepersenter<*>> : BaseActivity(), BaseView {

    val dialog = ZLoadingDialog(this)
    override fun showLoading() {
        dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                .setLoadingColor(Color.WHITE)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16f) // 设置字体大小 dp
                .setHintTextColor(Color.WHITE)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#00000000")) // 设置背景色，默认白色
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .show()
    }

    override fun hideLoading() {
        dialog.dismiss()
    }

    override fun onError(text: String) {
        toast("网络错误")
        dialog.dismiss()
    }


    @Inject
    lateinit var mpersenter: T

    lateinit var activityCommpoent: ActivityCommpoent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseView = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        activityCommpoent = DaggerActivityCommpoent.builder().appCommpoent((application as BaseApplication).appCommpoen).activityModule(ActivityModule(this)).lifecycleProviderModule(LifecycleProviderModule(this)).build()
    }


    companion object {
     lateinit var BaseView: BaseView
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}