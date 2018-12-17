package study.kotin.my.usercenter.data.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.usercenter.data.protocol.loginregisterreq
import study.kotin.my.usercenter.data.protocol.registerdata


interface UserApi {
    //登陆
    @POST("auth/userlogin")
    fun Login(@Body req: loginregisterreq): Observable<Response<BaseResp<String>>>

    @POST("auth/register")
    fun register(@Body registerdata: registerdata, @Query("code") code: String): Observable<BaseResp<String>>

    @GET("auth/sendSms")
    fun sendSms(@Query("phonenumber") phonenumber: String): Observable<BaseResp<String>>

    @POST("")
    fun resetpass(@Query("") phonenumber: String,@Query("")yzm:String,@Query("") pass:String): Observable<BaseResp<String>>
}