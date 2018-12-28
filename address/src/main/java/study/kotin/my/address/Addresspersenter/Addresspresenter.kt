package study.kotin.my.address.Addresspersenter

import android.util.Log
import io.reactivex.Observable
import okhttp3.MultipartBody
import org.jetbrains.anko.toast
import study.kotin.my.address.Addresspersenter.view.AddressView
import study.kotin.my.address.net.addressapi
import study.kotin.my.address.service.AddressService
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import javax.inject.Inject

class Addresspresenter @Inject constructor() : Basepersenter<AddressView>() {
    @Inject
    lateinit var AddressServiceimp: AddressService

    fun uploadimg(activity: BaseMVPActivity<*>, Authorization:String, file: List<MultipartBody.Part>){
        AddressServiceimp.uploadimg(Authorization,file).excute(object : BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.uploadimg(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                activity.hideLoading()
                BaseApplication.context.toast("图片上传错误，请重试")
                Log.e("eeeeeeeeeeeee","图片上传错误")
            }
        },lifecycleProvider)


    }
    fun loadProvince() {
        AddressServiceimp.loadProvince().excute(object:BaseObserver<ProvinceList>(){
            override fun onNext(t: ProvinceList) {
                mView.loadProvince(t.data)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun loadschool(url:String) {
        AddressServiceimp.loadschool(url).excute(object:BaseObserver<SchoolList>(){
            override fun onNext(t: SchoolList) {
                mView.loadschool(t.data)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }

        },lifecycleProvider)
    }
    fun ss() {
        AddressServiceimp.aa()
        mView.reslut()
    }
}