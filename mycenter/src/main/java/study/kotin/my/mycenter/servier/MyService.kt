package study.kotin.my.mycenter.servier

import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.net.Mycenterapi

interface MyService {
     fun Logout(): Observable<BaseResp<String>>
     fun changePassword(Authorization:String,oldepsd:String,newpsd:String): Observable<BaseResp<String>>
     fun Synchronizeinfo(Authorization:String): Observable<BaseResp<String>>
     fun uploadimg(Authorization:String,file:List<MultipartBody.Part>): Observable<BaseResp<String>>
     fun loadProvince(): Observable<ProvinceList>
     fun loadschool(url:String): Observable<SchoolList>
}