package com.example.home.net


import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface homeapi {
    @POST("http://madengwang.com:9200/user/searchUserinfo?pageNum=0&pageSize=20")
    fun search(@Header("Authorization") Authorization: String,@Query("keywords") keyword:String ): Observable<searchuserdata>

    @POST("http://madengwang.com:9200/Group/search?pageNum=0&pageSize=20")
    fun GroupSearch(@Header("Authorization") Authorization: String,@Query("name") name:String ):Observable<searchgroupdata>
}