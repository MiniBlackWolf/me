package com.example.home.net

import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface homeapi {
    @POST("http://192.168.1.105:9200/search/searchuser")
    fun search(@Header("Authorization")   Authorization: String,@Body sendsearchuserdata: sendsearchuserdata): Observable<searchuserdata>
}