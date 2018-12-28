package study.kotin.my.address.net

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.protocol.BaseResp

interface addressapi {
    @Multipart
    @POST("http://madengwang.com:9200/upload/img")
    fun uploadimg(@Header("Authorization") Authorization: String, @Part file: List<MultipartBody.Part>): Observable<BaseResp<String>>

    @POST("http://www.hisihi.com/app.php?s=/school/province")
    fun loadProvince(): Observable<ProvinceList>

    @POST
    fun loadschool(@Url url: String): Observable<SchoolList>

}