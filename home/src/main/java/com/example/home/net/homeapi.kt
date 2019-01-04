package com.example.home.net


import com.example.home.data.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import study.kotin.my.baselibrary.protocol.BaseResp


interface homeapi {
    @POST("http://madengwang.com:9200/user/searchUserinfo?pageNum=0&pageSize=20")
    fun search(@Header("Authorization") Authorization: String, @Query("keywords") keyword: String): Observable<searchuserdata>

    @POST("http://madengwang.com:9200/Group/search?pageNum=0&pageSize=20")
    fun GroupSearch(@Header("Authorization") Authorization: String, @Query("name") name: String): Observable<searchgroupdata>

    @POST("http://192.168.1.105:9200/communityarticle/add")
    fun addarticle(@Header("Authorization") Authorization: String, @Body articledata: articledata): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/communityarticle/findAll?page=0&size=9999")
    fun findarticle(@Body id: Map<String, String>): Observable<listarticledata<articledata>>

    @Multipart
    @POST("http://madengwang.com:9200/upload/img")
    fun uploadimg(@Part file: List<MultipartBody.Part>): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/communityactive/add")
    fun addactive(@Body articledata: articledata): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/communityactive/findAll?page=0&size=9999")
    fun findactive(@Body id: Map<String, String>): Observable<listarticledata<articledata>>

    @POST("http://192.168.1.105:9200/ActiveUser/join")
    fun join(@Query("Activeid") Activeid: Int): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/ActiveUser/quit")
    fun quit(@Query("Activeid") Activeid: Int): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/helper/findAll?page=1&size=1")
    fun helper(): Observable<listarticledata<madengdata>>

}