package study.kotin.my.usercenter.data.Repossitory


import io.reactivex.Observable
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.usercenter.data.api.UserApi
import study.kotin.my.usercenter.data.protocol.loginregisterreq
import javax.inject.Inject

class UserRepossitory @Inject constructor(){
    fun register(user:String,pass:String): Observable<BaseResp<String>> {
       return RetrofitFactory.instance.creat(UserApi::class.java).Login(loginregisterreq(user,pass))
    }

}