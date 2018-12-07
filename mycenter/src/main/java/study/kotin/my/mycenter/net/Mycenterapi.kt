package study.kotin.my.mycenter.net

import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import study.kotin.my.baselibrary.protocol.BaseResp

interface Mycenterapi {
    @POST("auth/userlogout")
    fun logout(): Observable<BaseResp<String>>

    @POST("http://madengwang.com:9200/user/changePassword")
    fun changePassword(@Header("Authorization") Authorization: String, @Query("oldepsd") oldepsd: String, @Query("newpsd") newpsd: String): Observable<BaseResp<String>>

    @POST("http://madengwang.com:9200/user/Synchronizeinfo")
    fun Synchronizeinfo(@Header("Authorization") Authorization:String): Observable<BaseResp<String>>
}