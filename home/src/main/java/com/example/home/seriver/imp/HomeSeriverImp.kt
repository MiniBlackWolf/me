package com.example.home.seriver.imp

import android.content.Context
import android.util.Log
import com.example.home.Repossitory.HomeRepossitory
import com.example.home.data.articledata
import com.example.home.seriver.HomeSeriver
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMManagerExt
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import io.reactivex.Observable
import kotlinx.android.synthetic.main.chatlayout.*
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.utils.FileUtil
import java.io.IOException
import javax.inject.Inject

class HomeSeriverImp @Inject constructor() : HomeSeriver {
    @Inject
    lateinit var HomeRepossitory: HomeRepossitory



    override fun getdata(): Observable<Boolean> {
        return Observable.create { emitter ->
            Log.e("iiiiiiiiis", Thread.currentThread().getName())
            val list = TIMManagerExt.getInstance().conversationList
            for (userconversation in list) {
                TIMConversationExt(userconversation).getMessage(10, null, object :
                        TIMValueCallBack<MutableList<TIMMessage>> {
                    override fun onSuccess(p0: MutableList<TIMMessage>?) {
                        p0!!.reverse()
                        loop@ for (i in 0 until p0.size) {
                            val element = p0.get(i).getElement(0)
                            when (element.type) {
                                TIMElemType.Text, TIMElemType.Face -> {
                                }
                                TIMElemType.Image -> {
                                    for (image in (element as TIMImageElem).imageList) {
                                        val uuid = image.uuid
                                        image.getImage(FileUtil.getCacheFilePath(uuid), object : TIMCallBack {
                                            override fun onError(code: Int, desc: String) {//获取图片失败
                                                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                                                //错误码 code 含义请参见错误码表
                                                Log.e("imgs--d", "getImage failed. code: $code errmsg: $desc")
                                            }

                                            override fun onSuccess() {//成功，参数为图片数据
                                                //doSomething
                                                Log.d("imgs--d", "getImage success.")
                                                Log.i("iiiiiiiii", Thread.currentThread().getName())
                                            }
                                        })
                                    }
                                }
                                TIMElemType.Sound -> {
                                    val string = BaseApplication.context.getSharedPreferences("LongTimeData", Context.MODE_PRIVATE).getString((element as TIMSoundElem).uuid, "")
                                    if (string != "") {
                                        continue@loop
                                    }
                                    val tempAudio = FileUtil.getTempFile(FileUtil.FileType.AUDIO)
                                    (element as TIMSoundElem).getSoundToFile(tempAudio.absolutePath, object : TIMCallBack {
                                        override fun onSuccess() {
                                            val edit = BaseApplication.context.getSharedPreferences("LongTimeData", Context.MODE_PRIVATE).edit()
                                            edit.putString(element.uuid, tempAudio.absolutePath)
                                            edit.apply()
                                        }

                                        override fun onError(p0: Int, p1: String?) {

                                        }
                                    })

                                }
                                TIMElemType.Video -> {
                                }
                                TIMElemType.GroupTips -> {
                                }
                                TIMElemType.File -> {

                                }
                                TIMElemType.UGC -> {
                                }
                                else -> return
                            }

                        }

                    }

                    override fun onError(p0: Int, p1: String?) {
                        Log.i("iiiiiiiiiiiiii", p1)
                    }
                })
            }
            val conversationList = TIMManagerExt.getInstance().conversationList
            for(l in conversationList){
                TIMFriendshipManagerExt.getInstance().getFriendsProfile(arrayListOf(l.peer), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
                    override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                        if(p0?.size==null)return
                        try {
                            val edit = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                            if(p0.get(0).remark==null){
                                edit.putString(l.peer+"fdname", p0.get(0).nickName)
                            }else{
                                edit.putString(l.peer+"fdname", p0.get(0).remark)
                            }
                            edit.putString(l.peer+"fdheadurl", p0.get(0).faceUrl)
                            edit.putString(l.peer+"fdid", p0.get(0).identifier)
                            edit.apply()
                        }catch (e:IOException){
                            e.printStackTrace()
                        }

                    }

                    override fun onError(p0: Int, p1: String?) {
                    }

                })
                TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(l.peer), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                    override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                        if(p0?.size==null)return
                        val edit = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                        edit.putString(l.peer+"Gname", p0.get(0).groupName)
                        edit.putString(l.peer+"Gheadurl", p0.get(0).faceUrl)
                        edit.putString(l.peer+"Gtype",p0[0].groupType)
                        edit.apply()
                    }

                    override fun onError(p0: Int, p1: String?) {
                    }

                })
            }
            TIMFriendshipManager.getInstance().getSelfProfile(object : TIMValueCallBack<TIMUserProfile> {
                override fun onSuccess(p0: TIMUserProfile?) {
                    try {
                        val edit = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                        edit.putString("myname", p0!!.nickName)
                        edit.putString("myheadurl", p0.faceUrl)
                        edit.apply()
                    }catch (e:IOException){
                        e.printStackTrace()
                    }

                }

                override fun onError(p0: Int, p1: String?) {
                }

            })



            emitter.onNext(true)
            emitter.onComplete()
        }


    }
}