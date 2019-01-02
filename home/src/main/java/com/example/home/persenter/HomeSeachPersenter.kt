package com.example.home.persenter

import android.util.Log
import com.example.home.data.articledata
import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import com.example.home.persenter.view.HomeSeachView
import com.example.home.seriver.SearchSeriver
import com.example.home.ui.activity.SearchActivity
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import javax.inject.Inject

class HomeSeachPersenter @Inject constructor():Basepersenter<HomeSeachView>() {
    @Inject
    lateinit var SearchServiceimp: SearchSeriver
    fun articledata(Authorization: String, articledata: articledata){
        SearchServiceimp.addarticle(Authorization,articledata).excute(object :BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.article(t)
                super.onNext(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun searchuser(Authorization: String,keywords: String,SearchActivity: SearchActivity){
        SearchServiceimp.search(Authorization,keywords).excute(object: BaseObserver<searchuserdata>(){
            override fun onNext(t: searchuserdata) {
                mView.search(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                SearchActivity.hideLoading()
                Log.e("eeeeeeee","搜索错误")
            }
        },lifecycleProvider)
    }
    fun searchgroup(Authorization: String,name: String,SearchActivity: SearchActivity){
        SearchServiceimp.GroupSearch(Authorization,name).excute(object: BaseObserver<searchgroupdata>(){
            override fun onNext(t: searchgroupdata) {
                mView.GroupSearch(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                SearchActivity.hideLoading()
                Log.e("eeeeeeee","搜索错误")
            }
        },lifecycleProvider)
    }
}