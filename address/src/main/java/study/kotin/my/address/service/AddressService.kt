package study.kotin.my.address.service

import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.baselibrary.protocol.BaseResp

interface AddressService {
    fun uploadimg(Authorization:String,file:List<MultipartBody.Part>): Observable<BaseResp<String>>
    fun aa()
}