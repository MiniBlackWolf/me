package com.example.usercenter2.service.impl




import io.reactivex.Observable
import retrofit2.Response
import study.kotin.my.baselibrary.protocol.BaseResp
import com.example.usercenter2.data.Repossitory.UserRepossitory
import com.example.usercenter2.data.protocol.registerdata
import com.example.usercenter2.service.Userservice
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Userserviceimpl @Inject constructor() : Userservice {

    @Inject
    lateinit var userRepossitory: UserRepossitory
    var count=0;
    override fun sendSms(phonenumber: String): Observable<BaseResp<String>> {
        return userRepossitory.sendSms(phonenumber)
    }

    override fun Regist(registerdata: registerdata, code: String): Observable<BaseResp<String>> {
      return  userRepossitory.register(registerdata,code)
    }
    override fun resetpass(phonenumber: String, yzm: String, pass: String): Observable<BaseResp<String>> {
        return userRepossitory.resetpass(phonenumber,yzm,pass)
    }

    //登陆方法
    override fun Login(user: String, pass: String): Observable<Response<BaseResp<String>>> {
        return  userRepossitory.login(user, pass).retryWhen { it->
            it.flatMap {its->
                if(count>5){
                    Observable.error(its)
                }else{
                    Observable.just(count).delay(2,TimeUnit.SECONDS)
                }
            }

        }
    }
 }