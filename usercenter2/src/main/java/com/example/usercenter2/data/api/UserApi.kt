package com.example.usercenter2.data.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*
import study.kotin.my.baselibrary.protocol.BaseResp
import com.example.usercenter2.data.protocol.loginregisterreq
import com.example.usercenter2.data.protocol.registerdata


interface UserApi {
    //登陆
    @POST("auth/userlogin")
    fun Login(@Body req: loginregisterreq): Observable<Response<BaseResp<String>>>

    @POST("auth/register")
    fun register(@Body registerdata: registerdata, @Query("code") code: String): Observable<BaseResp<String>>

    @GET("auth/sendSms")
    fun sendSms(@Query("phonenumber") phonenumber: String): Observable<BaseResp<String>>

    @POST("auth/forget")
    fun resetpass(@Query("phone") phonenumber: String,@Query("code")yzm:String,@Query("password") pass:String): Observable<BaseResp<String>>
}