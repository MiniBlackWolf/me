package study.kotin.my.baselibrary.ui.activity


import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.baselibrary.injection.commponent.AppCommpoent
import study.kotin.my.baselibrary.injection.commponent.DaggerActivityCommpoent
import study.kotin.my.baselibrary.injection.module.ActivityModule
import study.kotin.my.baselibrary.injection.module.LifecycleProviderModule
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import javax.inject.Inject

open class BaseMVPActivity<T:Basepersenter<*>>:BaseActivity(),BaseView {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError() {
    }
    @Inject
    lateinit var mpersenter:T

    lateinit var activityCommpoent:ActivityCommpoent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        activityCommpoent= DaggerActivityCommpoent.builder().appCommpoent((application as BaseApplication).appCommpoen).activityModule(ActivityModule(this)).lifecycleProviderModule(LifecycleProviderModule(this)).build()

    }

}