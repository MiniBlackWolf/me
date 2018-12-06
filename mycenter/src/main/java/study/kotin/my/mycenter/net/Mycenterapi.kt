package study.kotin.my.mycenter.net

import io.reactivex.Observable
import retrofit2.http.POST
import study.kotin.my.baselibrary.protocol.BaseResp

interface Mycenterapi {
    @POST("auth/userlogout")
    fun logout():Observable<BaseResp<String>>

}