package study.kotin.my.usercenter.data.Repossitory


import io.reactivex.Observable
import retrofit2.Response
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.usercenter.data.api.UserApi
import study.kotin.my.usercenter.data.protocol.loginregisterreq
import study.kotin.my.usercenter.data.protocol.registerdata
import javax.inject.Inject

class UserRepossitory @Inject constructor(){
    fun login(user:String,pass:String): Observable<Response<BaseResp<String>>> {
       return RetrofitFactory.instance.creat(UserApi::class.java).Login(loginregisterreq(user,pass))
    }
    fun register(registerdata: registerdata,code:String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.creat(UserApi::class.java).register(registerdata,code)
    }
    fun sendSms(phonenumber:String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.creat(UserApi::class.java).sendSms(phonenumber)
    }
}