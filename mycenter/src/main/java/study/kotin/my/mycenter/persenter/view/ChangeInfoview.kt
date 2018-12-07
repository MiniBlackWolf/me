package study.kotin.my.mycenter.persenter.view

import io.reactivex.Observable
import retrofit2.http.Header
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface ChangeInfoview:BaseView {
    fun Synchronizeinfo(result:BaseResp<String>)

}