package com.example.home.persenter

import android.util.Log
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import com.example.home.persenter.view.HomeSeachView
import com.example.home.seriver.SearchSeriver
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.rx.BaseObserver
import javax.inject.Inject

class HomeSeachPersenter @Inject constructor():Basepersenter<HomeSeachView>() {
    @Inject
    lateinit var SearchServiceimp: SearchSeriver
    fun searchuser(Authorization: String,sendsearchuserdata: sendsearchuserdata){
        SearchServiceimp.search(Authorization,sendsearchuserdata).excute(object: BaseObserver<searchuserdata>(){
            override fun onNext(t: searchuserdata) {
                mView.searchuser(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.e("eeeeeeee","搜索错误")
            }
        },lifecycleProvider)
    }
}