package study.kotin.my.find.presenter.view

import io.reactivex.Observable
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.find.data.neardata

interface Findview:BaseView {
    fun findByNear(t:List<neardata>)
}