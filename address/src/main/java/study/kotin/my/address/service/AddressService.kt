package study.kotin.my.address.service

import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.address.net.addressapi
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp

interface AddressService {
    fun uploadimg(Authorization:String,file:List<MultipartBody.Part>): Observable<BaseResp<String>>
    fun aa()
    fun loadProvince(): Observable<ProvinceList>
    fun loadschool(url:String): Observable<SchoolList>
}