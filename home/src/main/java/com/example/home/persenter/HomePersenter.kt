package com.example.home.persenter

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import com.example.home.Messges.*
import com.example.home.common.ChatViewSet
import com.example.home.data.articledata
import study.kotin.my.baselibrary.presenter.Basepersenter
import com.example.home.persenter.view.HomeView
import com.example.home.seriver.HomeSeriver
import com.example.home.seriver.SearchSeriver
import com.example.home.seriver.imp.SearchServiceimp
import com.tencent.imsdk.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import study.kotin.my.baselibrary.ext.excute
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.rx.BaseObserver
import javax.inject.Inject

class HomePersenter @Inject constructor() : Basepersenter<HomeView>() {
    @Inject
    lateinit var HomeSeriverImp: HomeSeriver
    @Inject
    lateinit var SearchServiceimp:SearchSeriver

    fun showmessge(id:String) {
        //消息监听
        TIMManager.getInstance().loginUser
        TIMManager.getInstance().addMessageListener {
           for(i in it){
               if(i.conversation.peer!=id){
                   return@addMessageListener false
               }

           }
            if (it.size == 1) {
                ChatViewSet(mView).showMessage(it.get(0))
            } else {
                ChatViewSet(mView).showMessage(it)
            }

            return@addMessageListener false
        }

    }

    fun sendmessge(id: String, data: TIMElem) {
        when (data.type) {
            TIMElemType.Text, TIMElemType.Face -> {
                val TIMTextMessage = TIMMessage()
                TIMTextMessage.addElement(data as TIMTextElem)
                SendTextMsg.sendtextmsg(id, TIMTextMessage)
            }
            TIMElemType.Image -> {
                val msg = TIMMessage()
                //将 elem 添加到消息
                if (msg.addElement(data as TIMImageElem) != 0) {
                    Log.d("iiiiiii", "addElement failed")
                    return
                }
                SendImgMsg.sendimgmsg(id, msg)
            }
            TIMElemType.Sound -> {
                val TIMsoundMessage = TIMMessage()
                if (TIMsoundMessage.addElement(data as TIMSoundElem) != 0) {
                    Log.d("iiiiiii", "addElement failed")
                    return
                }
                SendSoundMsg.sendsoundmsg(id, TIMsoundMessage)
            }
            TIMElemType.Video -> {
            }
            TIMElemType.GroupTips -> return
            //  return new GroupTipMessage(message);
            TIMElemType.File -> {
                val TIMFileMessage = TIMMessage()
                TIMFileMessage.addElement(data)
                SendFileMessge.SendFileMessge(id,TIMFileMessage)
            }
            TIMElemType.UGC -> return
            else -> return

        }

    }
@SuppressLint("CheckResult")
fun getdatas(){
//    Observable.create(object :ObservableOnSubscribe<Int> {
//        override fun subscribe(emitter: ObservableEmitter<Int>) {
//            Log.e("iiiiii", "Observable thread is : " + Thread.currentThread().getName())
//            emitter.onNext(1)
//            emitter.onComplete()
//        }
//
//    }).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object :Consumer<Int>{
//                override fun accept(t: Int?) {
//                    Log.e("iiiiiiiii",Thread.currentThread().getName())
//                }
//            })
    HomeSeriverImp.getdata().excute(object :  BaseObserver<Boolean>() {
        override fun onNext(t: Boolean) {
            Log.e("iiiiiiiii",Thread.currentThread().getName())
            super.onNext(t)
        }
    },lifecycleProvider)

}

}