package com.example.home.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import androidx.core.view.isVisible
import com.example.home.HomeAdapter.chatadapter
import com.example.home.R
import com.example.home.common.ChatViewSet
import com.example.home.common.Msg
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.example.home.persenter.HomePersenter
import com.example.home.persenter.view.HomeView
import kotlinx.android.synthetic.main.chatlayout.*
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMTextElem
import study.kotin.my.baselibrary.common.BaseApplication


class HomeActivity : BaseMVPActivity<HomePersenter>(),HomeView {
    var msglist=ArrayList<Msg>()
    override fun showmsg(TIMTextElem: TIMTextElem) {
        msglist.add(Msg((TIMTextElem as TIMTextElem).text, 0))
        chatrecyclerview.adapter = chatadapter(msglist, BaseApplication.context)
        chatrecyclerview.layoutManager = LinearLayoutManager(BaseApplication.context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatlayout)
        initinject()
        mpersenter.mView=this
        chatsendview.setChatView(ChatViewSet(mpersenter.mView), R.id.btn_photo, R.id.btn_image, R.id.btn_file)
        btn_photo.setOnClickListener(chatsendview)
        btn_image.setOnClickListener(chatsendview)
        btn_file.setOnClickListener(chatsendview)
        chatsendview.btnAdd.setOnClickListener {
            morePanel.isVisible = !morePanel.isVisible
        }
        mpersenter.showtextmessge()

    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)

    }

}