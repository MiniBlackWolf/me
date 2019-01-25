package com.example.home.ui.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.Button
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import kotlinx.android.synthetic.main.personalhomepage.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import java.text.SimpleDateFormat
import java.util.*

@Route(path = "/home/PersonalhomeActivity")
class PersonalhomeActivity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    val id by lazy { intent.extras?.getString("id") }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.chatfh -> {
                finish()
            }
            R.id.send -> {
                startActivity<HomeActivity>("id" to id)
                finish()
            }
            R.id.dt->{
                ARouter.getInstance().build("/find/FriendDtActivity").withString("id",id).navigation()
            }
        }
    }

//    var y1: Float = 0F
//    var y2: Float = 0F
//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            y1 = event.getY()
//        }
//        if (event.action == MotionEvent.ACTION_MOVE) {
//            y2 = event.y
//            var x2 = event.x
//            val y = sc.y
//            //   Log.i("iiiiiiiii", "$y2,$y")
////                if (y1 - y2 > 10) {//向上滑动
////                    val ofFloat = ObjectAnimator.ofFloat(v, "translationY", y, (y2 / 2))
////                    ofFloat.start()
////                }
//            if (y2 - y1 > 10) {//向下滑动
//                val ys = y2 / 3
////                x2=x2/3
//                //       Log.i("iiiiiiiii", "$ys,$y")
////                val translateAnimation = TranslateAnimation(0f, sc.y, 0f, y)
////                val animationSet = AnimationSet(true)
////                animationSet.addAnimation(translateAnimation)
////                sc.startAnimation(animationSet)0
//                val ofFloats = ObjectAnimator.ofFloat(sc, "translationY", ys, 0f)
//                ofFloats.start()
//            }
//        }
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            val ofFloat = ObjectAnimator.ofFloat(sc, "translationY", 0f)
//            ofFloat.start()
//        }
//        return super.dispatchTouchEvent(event)
//
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalhomepage)
        chatfh.setOnClickListener(this)
        dt.setOnClickListener(this)
        hz.setEnableOverScrollDrag(true)
        hz.setEnableOverScrollBounce(true)
        hz.setEnableRefresh(false)
//        sc.setOnTouchListener { v, event ->
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                //当手指按下的时候
//                y1 = event.getY()
//            }
//            if (event.action == MotionEvent.ACTION_MOVE) {
//                y2 = event.y
//                val y = v.y
//                Log.i("iiiiiiiii", "$y2,$y")
////                if (y1 - y2 > 10) {//向上滑动
////                    val ofFloat = ObjectAnimator.ofFloat(v, "translationY", y, (y2 / 2))
////                    ofFloat.start()
////                }
//                    if (y2 - y1 > 10) {//向下滑动
//                    val ofFloat = ObjectAnimator.ofFloat(v, "translationY", y, y2 / 4)
//                        ofFloat.setDuration(100)
//                    ofFloat.start()
//                }
//            }
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                //当手指离开的时候
//                val ofFloat = ObjectAnimator.ofFloat(v, "translationY", 0f)
//                ofFloat.start()
//            }
//            true
//        }
        TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf(id), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
            override fun onSuccess(p0: MutableList<TIMUserProfile>) {
                //名字
                tname.text = p0[0].nickName
                //头像
                val options = RequestOptions()
                        .error(R.drawable.a4_2)
                Glide.with(this@PersonalhomeActivity).load(p0[0].faceUrl).apply(options).into(thead)
                when {
                    //性别
                    p0[0].gender == TIMFriendGenderType.Male -> tsex.setImageResource(R.drawable.men)
                    p0[0].gender == TIMFriendGenderType.Female -> tsex.setImageResource(R.drawable.women)
                    else -> tsex.setImageResource(R.drawable.unkownsex)
                }
                tsite.text = p0[0].location
                //马镫号
                tmadengid.text = p0[0].identifier
                //生日
                val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
                val birthday = simpleDateFormat.format(p0[0].birthday)
                if (birthday != "0") {
                    val get = Calendar.getInstance().get(Calendar.YEAR)
                    val substring = birthday.substring(0, 4)
                    val age = get - substring.toInt()
                    tage.text = age.toString()
                }
                //个性签名
                tsg.text = p0[0].selfSignature
                //职业
                if (p0[0].customInfo["Tag_Profile_Custom_work"] == null) {
                    twork.text = "不明"
                } else {
                    twork.text = String(p0[0].customInfo["Tag_Profile_Custom_work"]!!)
                }
                //是否是自己
                if (p0[0].identifier == TIMManager.getInstance().loginUser) {
                    send.isVisible = false
                }

                //是否是好友
                TIMFriendshipManagerExt.getInstance().getFriendList(object : TIMValueCallBack<MutableList<TIMUserProfile>> {
                    override fun onSuccess(p1: MutableList<TIMUserProfile>?) {


                        //好友判断
                        var count = 0
                        for (p in p1!!) {
                            count++
                            if (p0[0].identifier == p.identifier) {
                                count++
                                send.isVisible = true
                                send.text = "发送消息"
                                send.setOnClickListener(this@PersonalhomeActivity)
                            }
                            //名字
                            if(p.identifier==id){
                                if(p.remark==""){
                                    tname.text = p.nickName
                                }else{
                                    tname.text =p.remark
                                    tsite.text="(${p.nickName  })"
                                }
                            }

                        }
                        if (count == p1.size && send.isVisible) {
                            send.isVisible = true
                            send.text = "添加好友"
                            send.setOnClickListener {
                                startActivity<addfriendActivity>("id" to p0[0].identifier)
                            }
                        }

                    }

                    override fun onError(p0: Int, p1: String?) {
                    }

                })
            }

            override fun onError(p0: Int, p1: String?) {

            }
        })

    }
}