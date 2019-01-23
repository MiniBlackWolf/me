package study.kotin.my.find.presenter

import android.util.Log
import okhttp3.MultipartBody
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.baselibrary.ui.activity.BaseActivity
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.data.friendcicledata
import study.kotin.my.find.data.neardata
import study.kotin.my.find.presenter.view.Findview
import study.kotin.my.find.service.findService
import javax.inject.Inject

class Findpresenter @Inject constructor() : Basepersenter<Findview>() {
    @Inject
    lateinit var findService: findService

    fun findByNear(Authorization: String) {
        findService.findByNear(Authorization).excute(object :BaseObserver<List<neardata>>(){
            override fun onNext(t: List<neardata>) {
                mView.findByNear(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun friendcicle(Authorization: String, friendcicledata: friendcicledata){
        findService.friendcicle(Authorization,friendcicledata).excute(object :BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.friendcicle(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun uploadimg(Authorization: String,activity: BaseMVPActivity<Findpresenter>, file: List<MultipartBody.Part>) {
        findService.uploadimg(Authorization,file).excute(object : BaseObserver<BaseResp<String>>(){
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
}