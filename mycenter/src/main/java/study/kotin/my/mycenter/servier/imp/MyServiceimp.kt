package study.kotin.my.mycenter.servier.imp

import io.reactivex.Observable
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.Repossitory.UserRepossitory
import study.kotin.my.mycenter.net.Mycenterapi
import study.kotin.my.mycenter.servier.MyService
import javax.inject.Inject

class MyServiceimp @Inject constructor() : MyService {


    @Inject
    lateinit var UserRepossitory: UserRepossitory

    override fun Logout(): Observable<BaseResp<String>> {
        return UserRepossitory.logout()
    }

    override fun changePassword(Authorization: String, oldepsd: String, newpsd: String): Observable<BaseResp<String>> {
        return UserRepossitory.changePassword(Authorization, oldepsd, newpsd)
    }

    override fun Synchronizeinfo(Authorization: String): Observable<BaseResp<String>> {
        return UserRepossitory.Synchronizeinfo(Authorization)
    }
}