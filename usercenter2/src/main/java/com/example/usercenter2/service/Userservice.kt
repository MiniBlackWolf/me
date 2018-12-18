package com.example.usercenter2.service

import io.reactivex.Observable
import retrofit2.Response
import study.kotin.my.baselibrary.protocol.BaseResp
import com.example.usercenter2.data.protocol.registerdata


interface Userservice {
    fun Login(user:String,pass:String): Observable<Response<BaseResp<String>>>
    fun Regist(registerdata: registerdata, code:String): Observable<BaseResp<String>>
    fun sendSms(phonenumber:String): Observable<BaseResp<String>>
    fun resetpass(phonenumber: String,yzm:String,pass:String):Observable<BaseResp<String>>
}