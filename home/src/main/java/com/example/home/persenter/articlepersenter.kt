package com.example.home.persenter

import com.example.home.data.articledata
import com.example.home.persenter.view.articleView
import com.example.home.seriver.articleService
import com.example.home.seriver.imp.articleServiceimp
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import javax.inject.Inject

class articlepersenter @Inject constructor():Basepersenter<articleView>() {
    @Inject
    lateinit var articleServiceimp: articleService
    fun articledata(Authorization: String, articledata: articledata){
        articleServiceimp.addarticle(Authorization,articledata).excute(object : BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.article(t)
                super.onNext(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun findarticle(id:Map<String,String>){
        articleServiceimp.findarticle(id).excute(object : BaseObserver<List<articledata>>(){
            override fun onNext(t: List<articledata>) {
                mView.findarticle(t)
                super.onNext(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
}