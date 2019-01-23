package study.kotin.my.find.service

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Query
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.find.data.*

interface findService {
    fun findByNear(Authorization: String): Observable<List<neardata>>
    fun friendcicle(Authorization: String,friendcicledata: friendcicledata): Observable<BaseResp<String>>
    fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>>
    fun addConment(Authorization: String,ConmentData: ConmentData): Observable<BaseResp<String>>
    fun getfriendcicle(Authorization: String,page: Int,size: Int):Observable<List<Getfriendcicledata>>
    fun addlike(Authorization: String,id:Int ):Observable<BaseResp<String>>
}