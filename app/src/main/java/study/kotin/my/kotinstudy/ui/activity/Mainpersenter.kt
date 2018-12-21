package study.kotin.my.kotinstudy.ui.activity

import android.app.Activity
import android.util.Log
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.toast
import retrofit2.Response
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
class Mainpersenter:Basepersenter<MainView>() {
    val AppRepossitory by lazy { study.kotin.my.kotinstudy.Repossitory.AppRepossitory() }
    fun Login(context: MainActivity, user: String, pass: String) {
        AppRepossitory.login(user,pass).excute(object : BaseObserver<Response<BaseResp<String>>>() {
            override fun onNext(t: Response<BaseResp<String>>) {
                mView.LoginResult(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.e("eeeeeeee","自动登录失败")
                context.hideLoading()
               // BaseApplication.context.toast("登录失败")
            }
        },context)
    }
}