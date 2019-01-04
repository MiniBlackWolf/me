package com.example.home.Repossitory



import com.example.home.data.*
import com.example.home.net.homeapi
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import javax.inject.Inject

class HomeRepossitory @Inject constructor(){
    fun search(Authorization: String, keywords:String ): Observable<searchuserdata> {
        return RetrofitFactory.instance.creat(homeapi::class.java).search(Authorization,keywords)
    }
    fun GroupSearch(Authorization: String, name:String): Observable<searchgroupdata> {
        return RetrofitFactory.instance.creat(homeapi::class.java).GroupSearch(Authorization,name)
    }
    fun addarticle(Authorization: String, articledata: articledata):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).addarticle(Authorization,articledata)
    }
    fun findarticle(id:Map<String,String>):Observable<listarticledata<articledata>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).findarticle(id)
    }
    fun addactive( articledata: articledata):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).addactive(articledata)
    }
    fun findactive(id:Map<String,String>):Observable<listarticledata<articledata>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).findactive(id)
    }
    fun uploadimg(file: List<MultipartBody.Part>): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).uploadimg(file)
    }
    fun join(Activeid: Int): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).join(Activeid)
    }
    fun quit(Activeid: Int): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).quit(Activeid)
    }

    fun helper(): Observable<listarticledata<madengdata>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).helper()
    }

}