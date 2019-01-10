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
    fun findarticle(Authorization: String,id:Map<String,String>):Observable<listarticledata<articledata>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).findarticle(Authorization,id)
    }
    fun addactive(Authorization: String, articledata: articledata):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).addactive(Authorization,articledata)
    }
    fun findactive(Authorization: String,id:Map<String,String>):Observable<listarticledata<articledata>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).findactive(Authorization,id)
    }
    fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).uploadimg(Authorization,file)
    }
    fun join(Authorization: String,Activeid: Int): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).join(Authorization,Activeid)
    }
    fun quit(Authorization: String,Activeid: Int): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).quit(Authorization,Activeid)
    }

    fun helper(Authorization: String,page:Int): Observable<listarticledata<madengdata>>{
        return RetrofitFactory.instance.creat(homeapi::class.java).helper(Authorization,page)
    }

}