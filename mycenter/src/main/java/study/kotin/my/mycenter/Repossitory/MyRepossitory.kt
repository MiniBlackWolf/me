package study.kotin.my.mycenter.Repossitory


import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.net.Mycenterapi
import javax.inject.Inject

class MyRepossitory @Inject constructor(){
    fun logout(): Observable<BaseResp<String>> {
       return RetrofitFactory.instance.creat(Mycenterapi::class.java).logout()
    }
    fun changePassword(Authorization:String,oldepsd:String,newpsd:String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.creat(Mycenterapi::class.java).changePassword(Authorization,oldepsd,newpsd)
    }
    fun Synchronizeinfo(Authorization:String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.creat(Mycenterapi::class.java).Synchronizeinfo(Authorization)
    }
    fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.creat(Mycenterapi::class.java).uploadimg(Authorization,file)
    }
    fun loadProvince(): Observable<ProvinceList> {
        return RetrofitFactory.instance.creat(Mycenterapi::class.java).loadProvince()
    }
    fun loadschool(url:String): Observable<SchoolList> {
        return RetrofitFactory.instance.creat(Mycenterapi::class.java).loadschool(url)
    }
}