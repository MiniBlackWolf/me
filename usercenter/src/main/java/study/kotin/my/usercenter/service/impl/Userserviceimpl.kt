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
    override fun sendSms(phonenumber: String): Observable<BaseResp<String>> {
        return userRepossitory.sendSms(phonenumber)
    }

    override fun Regist(registerdata: registerdata, code: String): Observable<BaseResp<String>> {
      return  userRepossitory.register(registerdata,code)
    }

    @Inject
    lateinit var userRepossitory:UserRepossitory
    //登陆方法
    override fun Login(user: String, pass: String): Observable<Response<BaseResp<String>>> {
        var count=0;
        return  userRepossitory.login(user, pass).retryWhen { it->
            it.flatMap {
                if(count>5){
                    Observable.error(it)
                }else{
                    Observable.just(count).delay(2,TimeUnit.SECONDS)
                }
            }

        }
    }
 }