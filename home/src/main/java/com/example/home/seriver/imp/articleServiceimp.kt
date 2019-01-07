package com.example.home.seriver.imp

import com.example.home.Repossitory.HomeRepossitory
import com.example.home.data.articledata
import com.example.home.data.listarticledata
import com.example.home.data.madengdata
import com.example.home.net.homeapi
import com.example.home.seriver.articleService
import io.reactivex.Observable
import okhttp3.MultipartBody
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
    override fun addactive(articledata: articledata): Observable<BaseResp<String>> {
        return HomeRepossitory.addactive(articledata)
    }

    override fun findactive(id: Map<String, String>):Observable<List<articledata>>{
        return HomeRepossitory.findactive(id).flatMap {
            Observable.just(it.rows)
        }
    }
    override fun uploadimg(file: List<MultipartBody.Part>): Observable<BaseResp<String>> {
        return HomeRepossitory.uploadimg(file)
    }
    override fun join(Activeid: Int): Observable<BaseResp<String>> {
        return HomeRepossitory.join(Activeid)
    }

    override fun quit(Activeid: Int): Observable<BaseResp<String>> {
        return HomeRepossitory.quit(Activeid)
    }
    override fun helper(page:Int): Observable<List<madengdata>>{
        return HomeRepossitory.helper(page).flatMap {
            Observable.just(it.rows)
        }
    }
}