package study.kotin.my.find.net

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.find.data.*

/**
 * Creat by blackwolf
 * 2019/1/18
 * system username : Administrator
 */
interface FindApi {
    @POST("/user/findByNear?page=1&size=10")
    fun findByNear(@Header("Authorization") Authorization: String): Observable<rowsdata<neardata>>

    @POST("http://192.168.1.105:9200/friendcicle/add")
    fun friendcicle(@Header("Authorization") Authorization: String, @Body friendcicledata: friendcicledata): Observable<BaseResp<String>>

    @Multipart
    @POST("/upload/imgs")
    fun uploadimg(@Header("Authorization") Authorization: String, @Part file: List<MultipartBody.Part>): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/friendcicle/getfriendcicle")
    fun getfriendcicle(@Header("Authorization") Authorization: String, @Query("page") page: Int, @Query("size") size: Int): Observable<rowsdata<Getfriendcicledata>>

    @POST("http://192.168.1.105:9200/friendcicle/addConment")
    fun addConment(@Header("Authorization") Authorization: String, @Body ConmentData: ConmentData): Observable<BaseResp<String>>

    @POST("http://192.168.1.105:9200/friendcicle/addlike")
    fun addlike(@Header("Authorization") Authorization: String,@Query("id") id:Int ):Observable<BaseResp<String>>
}