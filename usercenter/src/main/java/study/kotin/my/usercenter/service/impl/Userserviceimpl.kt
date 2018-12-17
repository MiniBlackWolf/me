package study.kotin.my.usercenter.service.impl




import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.Response
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseThrowable
import study.kotin.my.usercenter.data.Repossitory.UserRepossitory
import study.kotin.my.usercenter.data.protocol.registerdata
import study.kotin.my.usercenter.service.Userservice
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Userserviceimpl @Inject constructor() : Userservice {

    @Inject
    lateinit var userRepossitory:UserRepossitory
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