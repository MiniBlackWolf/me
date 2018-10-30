package study.kotin.my.usercenter.data.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.usercenter.data.protocol.loginregisterreq


interface UserApi {
    //登陆
    @POST("userCenter/register")
    fun Login(@Body req: loginregisterreq): Observable<BaseResp<String>>
}