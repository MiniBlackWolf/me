package study.kotin.my.kotinstudy.Repossitory


import io.reactivex.Observable
import retrofit2.Response
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.usercenter.data.api.UserApi
import study.kotin.my.usercenter.data.protocol.loginregisterreq
import javax.inject.Inject

class AppRepossitory @Inject constructor(){
    fun login(user:String,pass:String): Observable<Response<BaseResp<String>>> {
       return RetrofitFactory.instance.creat(UserApi::class.java).Login(loginregisterreq(user,pass))
    }

}