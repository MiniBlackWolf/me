package study.kotin.my.mycenter.persenter.view

import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface MyView:BaseView {
    fun Logoutreslut(t:BaseResp<String>)
}