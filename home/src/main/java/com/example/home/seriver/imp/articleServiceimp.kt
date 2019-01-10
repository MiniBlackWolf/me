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
    override fun findarticle(Authorization: String,id:Map<String,String>):Observable<List<articledata>>{
        return HomeRepossitory.findarticle(Authorization,id).flatMap {
            Observable.just(it.rows)
        }
    }
    override fun addactive(Authorization: String,articledata: articledata): Observable<BaseResp<String>> {
        return HomeRepossitory.addactive(Authorization,articledata)
    }

    override fun findactive(Authorization: String,id: Map<String, String>):Observable<List<articledata>>{
        return HomeRepossitory.findactive(Authorization,id).flatMap {
            Observable.just(it.rows)
        }
    }
    override fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>> {
        return HomeRepossitory.uploadimg(Authorization,file)
    }
    override fun join(Authorization: String,Activeid: Int): Observable<BaseResp<String>> {
        return HomeRepossitory.join(Authorization,Activeid)
    }

    override fun quit(Authorization: String,Activeid: Int): Observable<BaseResp<String>> {
        return HomeRepossitory.quit(Authorization,Activeid)
    }
    override fun helper(Authorization: String,page:Int): Observable<List<madengdata>>{
        return HomeRepossitory.helper(Authorization,page).flatMap {
            Observable.just(it.rows)
        }
    }
}