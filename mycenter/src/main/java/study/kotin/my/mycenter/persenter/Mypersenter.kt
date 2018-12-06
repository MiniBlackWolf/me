package study.kotin.my.mycenter.persenter

import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.mycenter.persenter.view.MyView
import study.kotin.my.mycenter.servier.MyService
import javax.inject.Inject

class Mypersenter @Inject constructor() : Basepersenter<MyView>() {
    @Inject
    lateinit var myServiceimp: MyService

    fun Logout() {
        myServiceimp.Logout().excute(object :BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.Logoutreslut(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                BaseApplication.context.toast("退出失败")
            }
        },lifecycleProvider)
    }
}