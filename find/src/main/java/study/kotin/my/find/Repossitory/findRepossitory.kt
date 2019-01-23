package study.kotin.my.find.Repossitory

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.find.data.*
import study.kotin.my.find.net.FindApi
import javax.inject.Inject

/**
 * Creat by blackwolf
 * 2019/1/18
 * system username : Administrator
 */
class findRepossitory @Inject constructor() {
    fun findByNear(Authorization: String): Observable<rowsdata<neardata>>{
        return RetrofitFactory.instance.creat(FindApi::class.java).findByNear(Authorization)
    }
    fun friendcicle(Authorization: String,friendcicledata: friendcicledata): Observable<BaseResp<String>>
    {
        return RetrofitFactory.instance.creat(FindApi::class.java).friendcicle(Authorization,friendcicledata)
    }
    fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(FindApi::class.java).uploadimg(Authorization,file)
    }
    fun addConment(Authorization: String,ConmentData: ConmentData): Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(FindApi::class.java).addConment(Authorization,ConmentData)
    }
    fun getfriendcicle(Authorization: String,page: Int,size: Int):Observable<rowsdata<Getfriendcicledata>>{
        return RetrofitFactory.instance.creat(FindApi::class.java).getfriendcicle(Authorization,page,size)
    }
    fun addlike(Authorization: String,id:Int ):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.creat(FindApi::class.java).addlike(Authorization,id)
    }

}