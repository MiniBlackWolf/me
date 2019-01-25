package study.kotin.my.find.presenter

import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import io.reactivex.Observable
import org.jetbrains.anko.toast
import retrofit2.http.Query
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import study.kotin.my.find.data.ConmentData
import study.kotin.my.find.data.Getfriendcicledata
import study.kotin.my.find.presenter.view.friendsCircleView
import study.kotin.my.find.service.findService
import javax.inject.Inject

/**
 * Creat by blackwolf
 * 2019/1/23
 * system username : Administrator
 */
class friendsCirclePresenter @Inject constructor() : Basepersenter<friendsCircleView>() {
    @Inject
    lateinit var findService: findService
    fun addConment(Authorization: String, LinearLayout: LinearLayout, ConmentData: ConmentData){
        findService.addConment(Authorization,ConmentData).excute(object:BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.addConment(t,LinearLayout,ConmentData,ConmentData.conment_id)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun getfriendcicle(Authorization: String, page: Int, size: Int){
        findService.getfriendcicle(Authorization,page,size).excute(object:BaseObserver<List<Getfriendcicledata>>(){
            override fun onNext(t:List<Getfriendcicledata>) {
                mView.getfriendcicle(t)
            }

            override fun onError(e: Throwable) {
                BaseApplication.context.toast("加载错误")
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun addlike(Authorization: String, id: Int,goodcount: TextView){
        findService.addlike(Authorization,id).excute(object:BaseObserver<BaseResp<String>>(){
            override fun onNext(t: BaseResp<String>) {
                mView.addlike(t,goodcount)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        },lifecycleProvider)
    }
    fun getone(Authorization: String, userid: String, page: Int, size: Int){
        findService.getone(Authorization,userid,page,size).excute(object:BaseObserver<List<Getfriendcicledata>>(){
            override fun onNext(t: List<Getfriendcicledata>) {
               mView.getone(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }

        },lifecycleProvider)
    }
}