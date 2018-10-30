package study.kotin.my.baselibrary.ui.fragment

import org.jetbrains.anko.support.v4.act
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.baselibrary.injection.commponent.DaggerActivityCommpoent
import study.kotin.my.baselibrary.injection.module.ActivityModule
import study.kotin.my.baselibrary.injection.module.LifecycleProviderModule
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import javax.inject.Inject

open class BaseMVPFragmnet<T: Basepersenter<*>>:BaseFragment(),BaseView {

    lateinit var mActivityComponent: ActivityCommpoent

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun onError() {
    }
    open fun onKeyBackPressed(): Boolean {
        return false
    }
    @Inject
    lateinit var mpersenter:T

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityCommpoent.builder().appCommpoent((act.application as BaseApplication).appCommpoen)
                .activityModule(ActivityModule(act))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }
}