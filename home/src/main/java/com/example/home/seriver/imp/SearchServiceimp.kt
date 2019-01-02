package com.example.home.seriver.imp

import com.example.home.Repossitory.HomeRepossitory
import com.example.home.data.articledata
import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import com.example.home.net.homeapi
import com.example.home.seriver.SearchSeriver
import io.reactivex.Observable
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import javax.inject.Inject

class SearchServiceimp @Inject constructor():SearchSeriver {

    @Inject
    lateinit var HomeRepossitory: HomeRepossitory
    override fun addarticle(Authorization: String, articledata: articledata): Observable<BaseResp<String>> {
        return HomeRepossitory.addarticle(Authorization,articledata)
    }
   override fun search(Authorization: String, keywords:String ): Observable<searchuserdata> {
        return HomeRepossitory.search(Authorization,keywords).flatMap {
            it.itemType = 1
            Observable.just(it)
        }
    }
    override fun GroupSearch(Authorization: String, name:String): Observable<searchgroupdata> {
        return HomeRepossitory.GroupSearch(Authorization,name).flatMap {
            it.itemType = 2
            Observable.just(it)
        }
    }

}