package study.kotin.my.usercenter.persenter.view

import retrofit2.Response
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface registerView:BaseView {
    fun LoginResult(result: Response<BaseResp<String>>)
    fun RegistResult(result: BaseResp<String>)
    fun sendSms(result: BaseResp<String>)
}