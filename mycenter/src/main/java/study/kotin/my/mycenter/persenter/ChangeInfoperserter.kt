package study.kotin.my.mycenter.persenter

import android.util.Log
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.mycenter.persenter.view.ChangeInfoview
import study.kotin.my.mycenter.servier.MyService
import javax.inject.Inject

class ChangeInfoperserter @Inject constructor() : Basepersenter<ChangeInfoview>() {
    @Inject
    lateinit var myServiceimp: MyService

    fun Synchronizeinfo(Authorization:String) {
        myServiceimp.Synchronizeinfo(Authorization).excute(object : BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.Synchronizeinfo(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.e("eeeeeeeeeeeee","同步错误")
            }
        },lifecycleProvider)

    }
}