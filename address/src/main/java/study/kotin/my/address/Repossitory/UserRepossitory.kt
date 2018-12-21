package study.kotin.my.address.Repossitory


import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.address.net.addressapi
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import javax.inject.Inject

class UserRepossitory @Inject constructor(){
    fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.creat(addressapi::class.java).uploadimg(Authorization,file)
    }
}