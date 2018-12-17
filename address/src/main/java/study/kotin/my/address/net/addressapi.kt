package study.kotin.my.address.net

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import study.kotin.my.baselibrary.protocol.BaseResp

interface addressapi {
    @Multipart
    @POST("http://madengwang.com:9200/upload/img")
    fun uploadimg(@Header("Authorization")   Authorization: String, @Part file: List<MultipartBody.Part>): Observable<BaseResp<String>>
}