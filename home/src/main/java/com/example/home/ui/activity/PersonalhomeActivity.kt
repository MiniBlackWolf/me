package com.example.home.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import kotlinx.android.synthetic.main.personalhomepage.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalhomepage)
        chatfh.setOnClickListener(this)

        TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf(id), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
            override fun onSuccess(p0: MutableList<TIMUserProfile>) {
                tname.text = p0[0].nickName
                when {
                    p0[0].gender == TIMFriendGenderType.Male -> tsex.setImageResource(R.drawable.men)
                    p0[0].gender == TIMFriendGenderType.Female -> tsex.setImageResource(R.drawable.women)
                    else -> tsex.setImageResource(R.drawable.unkownsex)
                }
                tsite.text = p0[0].location
                tmadengid.text = p0[0].identifier
                tage.text = p0[0].birthday.toString()
                tsg.text = p0[0].selfSignature
                if (p0[0].identifier == TIMManager.getInstance().loginUser) {
                    send.isVisible = false
                }
                TIMFriendshipManagerExt.getInstance().getFriendList(object : TIMValueCallBack<MutableList<TIMUserProfile>> {
                    override fun onSuccess(p1: MutableList<TIMUserProfile>?) {
                        var count = 0
                        for (p in p1!!) {
                            count++
                            if (p0[0].identifier == p.identifier) {
                                count++
                                send.isVisible = true
                                send.text = "发送消息"
                                send.setOnClickListener(this@PersonalhomeActivity)
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