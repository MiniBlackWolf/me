package study.kotin.my.mycenter.persenter

import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.mycenter.persenter.view.MyView
import study.kotin.my.mycenter.servier.MyService
import javax.inject.Inject

class Mypersenter @Inject constructor() : Basepersenter<MyView>() {
    @Inject
    lateinit var myServiceimp: MyService

    fun s() {
        myServiceimp.aa()
        mView.reslut()
    }
}