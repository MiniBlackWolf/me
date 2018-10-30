package study.kotin.my.usercenter.persenter


import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.usercenter.persenter.view.registerView
import study.kotin.my.usercenter.service.Userservice
import study.kotin.my.usercenter.service.impl.Userserviceimpl
import javax.inject.Inject


class registerPersenter @Inject constructor() : Basepersenter<registerView>() {
    @Inject
    lateinit var userserviceimpl: Userservice

    fun Login(user: String, pass: String) {
        userserviceimpl.Login(user, pass)
                .excute(object : BaseObserver<Boolean>() {
                    override fun onNext(t: Boolean) {
                        mView.LoginResult(t)
                    }
                },lifecycleProvider)


    }
}