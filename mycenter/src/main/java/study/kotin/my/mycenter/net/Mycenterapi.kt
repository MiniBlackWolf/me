package study.kotin.my.mycenter.net

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import study.kotin.my.baselibrary.protocol.BaseResp
import java.io.File

interface Mycenterapi {
    @POST("auth/userlogout")
    fun logout(): Observable<BaseResp<String>>

    @POST("http://madengwang.com:9200/user/changePassword")
    fun changePassword(@Header("Authorization") Authorization: String, @Query("oldepsd") oldepsd: String, @Query("newpsd") newpsd: String): Observable<BaseResp<String>>

    @POST("http://madengwang.com:9200/user/Synchronizeinfo")
    fun Synchronizeinfo(@Header("Authorization") Authorization: String): Observable<BaseResp<String>>

    @Multipart
    @POST("http://192.168.1.105:9200/upload/img")
    fun uploadimg(@Header("Authorization")   Authorization: String,@Part file: List<MultipartBody.Part>): Observable<BaseResp<String>>
}