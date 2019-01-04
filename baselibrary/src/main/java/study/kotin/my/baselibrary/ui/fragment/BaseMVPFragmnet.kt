package study.kotin.my.baselibrary.ui.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ActivityUtils
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.baselibrary.injection.commponent.DaggerActivityCommpoent
import study.kotin.my.baselibrary.injection.module.ActivityModule
import study.kotin.my.baselibrary.injection.module.LifecycleProviderModule
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.ui.activity.BaseActivity
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import javax.inject.Inject

open class BaseMVPFragmnet<T: Basepersenter<*>>:BaseFragment(),BaseView {


    lateinit var mActivityComponent: ActivityCommpoent
    val dialog=ZLoadingDialog( ActivityUtils.getTopActivity())
    override fun showLoading() {
        dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                .setLoadingColor(Color.WHITE)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16f) // 设置字体大小 dp
                .setHintTextColor(Color.WHITE)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#5B000000")) // 设置背景色，默认白色
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .show()
    }

    override fun hideLoading() {
            dialog.dismiss()

    }

    override fun onError(text:String) {
        toast("网络错误")
        dialog.dismiss()
    }
    open fun onKeyBackPressed(): Boolean {
        return false
    }
    @Inject
    lateinit var mpersenter:T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initActivityInjection()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityCommpoent.builder().appCommpoent((act.application as BaseApplication).appCommpoen)
                .activityModule(ActivityModule(act))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }
}