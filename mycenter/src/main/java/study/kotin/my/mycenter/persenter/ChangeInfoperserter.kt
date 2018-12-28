package study.kotin.my.mycenter.persenter

import android.app.Activity
import android.util.Log
import okhttp3.MultipartBody
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.persenter.view.ChangeInfoview
import study.kotin.my.mycenter.servier.MyService
import javax.inject.Inject

class ChangeInfoperserter @Inject constructor() : Basepersenter<ChangeInfoview>() {
    @Inject
    lateinit var myServiceimp: MyService

    fun Synchronizeinfo(Authorization:String) {
        myServiceimp.Synchronizeinfo(Authorization).excute(object : BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.Synchronizeinfo(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                BaseApplication.context.toast("修改失败，请重试")
                Log.e("eeeeeeeeeeeee","同步错误")
            }
        },lifecycleProvider)

    }
    fun uploadimg(activity:BaseMVPActivity<*>,Authorization:String,file: List<MultipartBody.Part>){
        myServiceimp.uploadimg(Authorization,file).excute(object : BaseObserver<BaseResp<String>>(){
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
        myServiceimp.loadProvince().excute(object:BaseObserver<ProvinceList>(){
            override fun onNext(t: ProvinceList) {
                mView.loadProvince(t.data)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun loadschool(url:String) {
        myServiceimp.loadschool(url).excute(object:BaseObserver<SchoolList>(){
            override fun onNext(t: SchoolList) {
                mView.loadschool(t.data)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }

        },lifecycleProvider)
    }

}