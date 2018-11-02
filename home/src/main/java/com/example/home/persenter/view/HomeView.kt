package com.example.home.persenter.view

import com.tencent.imsdk.TIMTextElem
import study.kotin.my.baselibrary.presenter.view.BaseView

interface HomeView:BaseView {
    fun showmsg(TIMTextElem: TIMTextElem)
}