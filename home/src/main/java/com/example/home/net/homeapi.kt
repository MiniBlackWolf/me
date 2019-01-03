package com.example.home.net


import com.example.home.data.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import study.kotin.my.baselibrary.protocol.BaseResp


interface homeapi {
    @POST("http://madengwang.com:9200/user/searchUserinfo?pageNum=0&pageSize=20")
    fun search(@Header("Authorization") Authorization: String, @Query("keywords") keyword: String): Observable<searchuserdata>

    @POST("http://madengwang.com:9200/Group/search?pageNum=0&pageSize=20")
    fun GroupSearch(@Header("Authorization") Authorization: String, @Query("name") name: String): Observable<searchgroupdata>

    @POST("http://192.168.1.105:9200/communityarticle/add")
    fun addarticle(@Header("Authorization") Authorization: String, @Body articledata: articledata): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/communityarticle/findAll?page=0&size=9999")
    fun findarticle(@Body id:Map<String,String>):Observable<listarticledata<articledata>>
}