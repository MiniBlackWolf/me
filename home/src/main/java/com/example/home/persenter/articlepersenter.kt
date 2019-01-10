package com.example.home.persenter

import android.app.Activity
import android.util.Log
import com.example.home.data.articledata
import com.example.home.persenter.view.articleView
import com.example.home.seriver.articleService
import com.example.home.seriver.imp.articleServiceimp
import com.example.home.ui.Frament.PublicGroupFarment_3
import com.example.home.ui.activity.AddActivity
import io.reactivex.Observable
import okhttp3.MultipartBody
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.baselibrary.ui.activity.BaseActivity
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import javax.inject.Inject

class articlepersenter @Inject constructor():Basepersenter<articleView>() {
    @Inject
    lateinit var articleServiceimp: articleService
    fun articledata(Authorization: String, articledata: articledata){
        articleServiceimp.addarticle(Authorization,articledata).excute(object : BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.article(t)
                super.onNext(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun findarticle(Authorization: String,id:Map<String,String>){
        articleServiceimp.findarticle(Authorization,id).excute(object : BaseObserver<List<articledata>>(){
            override fun onNext(t: List<articledata>) {
                mView.findarticle(t)
                super.onNext(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
     fun addactive(Authorization: String,activity: AddActivity,articledata: articledata) {
         articleServiceimp.addactive(Authorization,articledata).excute(object : BaseObserver<BaseResp<String>>(){
             override fun onNext(t: BaseResp<String>) {
                 mView.addactive(t)
                 super.onNext(t)
             }

             override fun onError(e: Throwable) {
                 super.onError(e)
                 activity.hideLoading()
             }
         },lifecycleProvider)

    }

     fun findactive(Authorization: String,id: Map<String, String>) {
         articleServiceimp.findactive(Authorization,id).excute(object : BaseObserver<List<articledata>>(){
             override fun onNext(t: List<articledata>) {
                 mView.findactive(t)
                 super.onNext(t)
             }

             override fun onError(e: Throwable) {
                 super.onError(e)
             }
         },lifecycleProvider)
    }

     fun uploadimg(Authorization: String,activity: AddActivity, file: List<MultipartBody.Part>) {
         articleServiceimp.uploadimg(Authorization,file).excute(object : BaseObserver<BaseResp<String>>(){
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

     fun join(Authorization: String,activity: PublicGroupFarment_3, Activeid: Int){
         articleServiceimp.join(Authorization,Activeid).excute(object : BaseObserver<BaseResp<String>>(){
             override fun onNext(t: BaseResp<String>) {
                 mView.join(t)
             }

             override fun onError(e: Throwable) {
                 super.onError(e)
                 activity.hideLoading()
                 BaseApplication.context.toast("加入失败")

             }
         },lifecycleProvider)
    }

     fun quit(Authorization: String,activity: PublicGroupFarment_3,Activeid: Int){
         articleServiceimp.quit(Authorization,Activeid).excute(object : BaseObserver<BaseResp<String>>(){
             override fun onNext(t: BaseResp<String>) {
                 mView.quit(t)
             }

             override fun onError(e: Throwable) {
                 super.onError(e)
                 activity.hideLoading()
                 BaseApplication.context.toast("退出失败")
             }
         },lifecycleProvider)
    }
}