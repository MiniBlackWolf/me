package study.kotin.my.find.presenter

import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.find.data.neardata
import study.kotin.my.find.presenter.view.Findview
import study.kotin.my.find.service.findService
import javax.inject.Inject

class Findpresenter @Inject constructor() : Basepersenter<Findview>() {
    @Inject
    lateinit var findService: findService

    fun findByNear(Authorization: String) {
        findService.findByNear(Authorization).excute(object :BaseObserver<List<neardata>>(){
            override fun onNext(t: List<neardata>) {
                mView.findByNear(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
}