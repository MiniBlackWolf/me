package study.kotin.my.kotinstudy.ui.activity

import io.reactivex.Observable
import retrofit2.Response
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface MainView:BaseView {
    fun LoginResult(t:Response<BaseResp<String>>)
}