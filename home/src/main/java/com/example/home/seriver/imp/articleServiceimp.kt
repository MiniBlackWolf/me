package com.example.home.seriver.imp

import com.example.home.Repossitory.HomeRepossitory
import com.example.home.data.articledata
import com.example.home.data.listarticledata
import com.example.home.net.homeapi
import com.example.home.seriver.articleService
import io.reactivex.Observable
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import javax.inject.Inject

class articleServiceimp @Inject constructor(): articleService {
    @Inject
    lateinit var HomeRepossitory:HomeRepossitory
    override fun addarticle(Authorization: String, articledata: articledata): Observable<BaseResp<String>> {
        return HomeRepossitory.addarticle(Authorization,articledata)
    }
    override fun findarticle(id:Map<String,String>):Observable<List<articledata>>{
        return HomeRepossitory.findarticle(id).flatMap {
            Observable.just(it.rows)
        }
    }
}