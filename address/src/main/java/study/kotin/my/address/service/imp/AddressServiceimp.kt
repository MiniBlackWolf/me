package study.kotin.my.address.service.imp

import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.address.service.AddressService
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.Repossitory.UserRepossitory
import javax.inject.Inject

class AddressServiceimp @Inject constructor(): AddressService {

    @Inject
    lateinit var UserRepossitory: UserRepossitory

    override fun uploadimg(Authorization: String,file: List<MultipartBody.Part>): Observable<BaseResp<String>> {
        return UserRepossitory.uploadimg(Authorization,file)
    }
    override fun aa() {

    }

}