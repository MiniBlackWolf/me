package com.example.home.net


import com.example.home.data.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import study.kotin.my.baselibrary.protocol.BaseResp


interface homeapi {
    @POST("/user/searchUserinfo?pageNum=0&pageSize=20")
    fun search(@Header("Authorization") Authorization: String, @Query("keywords") keyword: String): Observable<searchuserdata>

    @POST("/Group/search?pageNum=0&pageSize=20")
    fun GroupSearch(@Header("Authorization") Authorization: String, @Query("name") name: String): Observable<searchgroupdata>

    @POST("/communityarticle/add")
    fun addarticle(@Header("Authorization") Authorization: String, @Body articledata: articledata): Observable<BaseResp<String>>

    @POST("/communityarticle/findAll?page=0&size=9999")
    fun findarticle(@Header("Authorization") Authorization: String,@Body id: Map<String, String>): Observable<listarticledata<articledata>>

    @Multipart
    @POST("/upload/img")
    fun uploadimg(@Header("Authorization") Authorization: String,@Part file: List<MultipartBody.Part>): Observable<BaseResp<String>>

    @POST("/communityactive/add")
    fun addactive(@Header("Authorization") Authorization: String,@Body articledata: articledata): Observable<BaseResp<String>>

    @POST("/communityactive/findAll?page=0&size=9999")
    fun findactive(@Header("Authorization") Authorization: String,@Body id: Map<String, String>): Observable<listarticledata<articledata>>

    @POST("/ActiveUser/join")
    fun join(@Header("Authorization") Authorization: String,@Query("Activeid") Activeid: Int): Observable<BaseResp<String>>

    @POST("/ActiveUser/quit")
    fun quit(@Header("Authorization") Authorization: String,@Query("Activeid") Activeid: Int): Observable<BaseResp<String>>

    @POST("/helper/findAll?size=1")
    fun helper(@Header("Authorization") Authorization: String,@Query("page") page:Int): Observable<listarticledata<madengdata>>

}