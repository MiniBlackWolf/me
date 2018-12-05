package com.example.home.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import kotlinx.android.synthetic.main.addfriendlayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.tencent.imsdk.ext.sns.TIMAddFriendRequest
import com.tencent.imsdk.ext.sns.TIMFriendResult
import org.jetbrains.anko.toast


class addfriendActivity : BaseMVPActivity<HomePersenter>() {
    val id by lazy { intent.extras?.getString("id") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addfriendlayout)
        sendfdmsg.setOnClickListener {
            val users = ArrayList<TIMAddFriendRequest>()
            val timAddFriendRequest = TIMAddFriendRequest(id!!)
            timAddFriendRequest.setRemark(Remark.text.toString())
            timAddFriendRequest.setAddWording(Wording.text.toString())
            users.add(timAddFriendRequest)
            TIMFriendshipManagerExt.getInstance().addFriend(users, object : TIMValueCallBack<MutableList<TIMFriendResult>> {
                override fun onSuccess(p0: MutableList<TIMFriendResult>?) {
                    toast("添加成功")
                    ARouter.getInstance().build("/App/Homepage").navigation()
                    finish()
                }

                override fun onError(p0: Int, p1: String?) {
                    toast("添加失败")
                }
            })
        }
    }
}