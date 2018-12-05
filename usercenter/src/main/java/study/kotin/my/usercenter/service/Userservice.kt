package study.kotin.my.usercenter.service

import io.reactivex.Observable
import retrofit2.Response
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.usercenter.data.protocol.registerdata


interface Userservice {
    fun Login(user:String,pass:String): Observable<Response<BaseResp<String>>>
    fun Regist(registerdata: registerdata, code:String): Observable<BaseResp<String>>
    fun sendSms(phonenumber:String): Observable<BaseResp<String>>
}