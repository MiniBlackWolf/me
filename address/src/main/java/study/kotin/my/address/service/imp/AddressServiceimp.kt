package study.kotin.my.address.service.imp

import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.address.service.AddressService
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.address.Repossitory.UserRepossitory
import study.kotin.my.address.net.addressapi
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import javax.inject.Inject

class AddressServiceimp @Inject constructor(): AddressService {

    @Inject
    lateinit var UserRepossitory: UserRepossitory

    override fun loadProvince(): Observable<ProvinceList> {
        return UserRepossitory.loadProvince()
    }
    override fun loadschool(url:String): Observable<SchoolList> {
        return UserRepossitory.loadschool(url)
    }
    override fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>> {
        return UserRepossitory.uploadimg(Authorization,file)
    }
    override fun aa() {

    }

}