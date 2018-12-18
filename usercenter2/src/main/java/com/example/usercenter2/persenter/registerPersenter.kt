package com.example.usercenter2.persenter


import org.jetbrains.anko.toast
import retrofit2.Response
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import com.example.usercenter2.data.protocol.registerdata
import com.example.usercenter2.persenter.view.registerView
import com.example.usercenter2.service.Userservice
import javax.inject.Inject


class registerPersenter @Inject constructor() : Basepersenter<registerView>() {
    @Inject
    lateinit var userserviceimpl: Userservice


    fun resetpass(phonenumber: String, yzm: String, pass: String){
        userserviceimpl.resetpass(phonenumber,yzm,pass).excute(object : BaseObserver<BaseResp<String>>() {
            override fun onNext(t: BaseResp<String>) {
                mView.resetpassResult(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                BaseApplication.context.toast("重置失败，请重试")
            }
        }, lifecycleProvider)

    }
    fun Login(user: String, pass: String) {
        userserviceimpl.Login(user, pass)
                .excute(object : BaseObserver<Response<BaseResp<String>>>() {
                    override fun onNext(t: Response<BaseResp<String>>) {
                        mView.LoginResult(t)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        BaseApplication.context.toast("登录失败")
                    }
                }, lifecycleProvider)
    }

    fun Regist(registerdata: registerdata, code: String) {
        userserviceimpl.Regist(registerdata, code)
                .excute(object : BaseObserver<BaseResp<String>>() {
                    override fun onNext(t: BaseResp<String>) {
                        mView.RegistResult(t)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        BaseApplication.context.toast("注册失败")
                    }
                }, lifecycleProvider)
    }

    fun sendsms(phonenumber: String) {
        userserviceimpl.sendSms(phonenumber).excute(
                object : BaseObserver<BaseResp<String>>() {
                    override fun onNext(t: BaseResp<String>) {
                        mView.sendSms(t)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        BaseApplication.context.toast("发送短信失败")
                    }
                }, lifecycleProvider)
    }
}