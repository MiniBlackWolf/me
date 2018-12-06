package study.kotin.my.mycenter.Repossitory


import io.reactivex.Observable
import retrofit2.Response
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.net.Mycenterapi
import javax.inject.Inject

class UserRepossitory @Inject constructor(){
    fun logout(): Observable<BaseResp<String>> {
       return RetrofitFactory.instance.creat(Mycenterapi::class.java).logout()
    }
}