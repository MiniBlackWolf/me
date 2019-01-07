package com.example.home.persenter

import android.annotation.SuppressLint
import android.util.Log
import com.example.home.data.UserList
import com.example.home.data.articledata
import com.example.home.data.madengdata
import com.example.home.persenter.view.madenghelperView
import com.example.home.seriver.HomeSeriver
import com.example.home.seriver.SearchSeriver
import com.example.home.seriver.articleService
import io.reactivex.Observable
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.rx.BaseObserver
import javax.inject.Inject

class madenghelperpersenter @Inject constructor():Basepersenter<madenghelperView>() {
    @Inject
    lateinit var articleServiceimp: articleService
    @Inject
    lateinit var HomeSeriverImp: HomeSeriver
    fun madenghelper(page:Int){
        articleServiceimp.helper(page).excute(object : BaseObserver<List<madengdata>>(){
            override fun onNext(t: List<madengdata>) {
                mView.helper(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.helpererror(e)
            }
        },lifecycleProvider)
    }
    @SuppressLint("CheckResult")
    fun getdatas(){
//    Observable.create(object :ObservableOnSubscribe<Int> {
//        override fun subscribe(emitter: ObservableEmitter<Int>) {
//            Log.e("iiiiii", "Observable thread is : " + Thread.currentThread().getName())
//            emitter.onNext(1)
//            emitter.onComplete()
//        }
//
//    }).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object :Consumer<Int>{
//                override fun accept(t: Int?) {
//                    Log.e("iiiiiiiii",Thread.currentThread().getName())
//                }
//            })
        HomeSeriverImp.getdata().excute(object :  BaseObserver<Boolean>() {
            override fun onNext(t: Boolean) {
                Log.e("iiiiiiiii",Thread.currentThread().getName())
                super.onNext(t)
            }
        },lifecycleProvider)

    }
}