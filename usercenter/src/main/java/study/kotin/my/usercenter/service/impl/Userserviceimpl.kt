package study.kotin.my.usercenter.service.impl




import io.reactivex.Observable
import io.reactivex.functions.Function
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseThrowable
import study.kotin.my.usercenter.data.Repossitory.UserRepossitory
import study.kotin.my.usercenter.service.Userservice
import javax.inject.Inject

class Userserviceimpl @Inject constructor() : Userservice {
    @Inject
    lateinit var userRepossitory:UserRepossitory
    //注册方法
    override fun Login(user: String, pass: String): Observable<Boolean> {
        return   userRepossitory.register(user, pass).flatMap(object : Function<BaseResp<String>, Observable<Boolean>> {
            override fun apply(t: BaseResp<String>): Observable<Boolean> {
                if (t.status == 0) {
                    return Observable.just(true)
                } else {
                    return Observable.just(true)
                   // return Observable.error(BaseThrowable(t.status, t.message))
                }

            }

        })

    }
 }