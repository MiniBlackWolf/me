package com.example.home.ui.Frament

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.eightbitlab.rxbus.Bus
import com.example.home.HomeAdapter.ChatListAdapter
import com.example.home.HomeAdapter.HomeListAdapter
import com.example.home.R
import com.example.home.common.UpdateMessgeSizeEvent
import com.example.home.data.UserList
import com.example.home.persenter.HomePersenter
import com.example.home.ui.activity.HomeActivity
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import com.tencent.imsdk.TIMManager
import org.jetbrains.anko.support.v4.startActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule


class HomeFarment : BaseMVPFragmnet<HomePersenter>() {
    val userlist: ArrayList<UserList> = ArrayList()
    lateinit var chatlist: RecyclerView
    lateinit var chatlist2: RecyclerView
    lateinit var left: ImageView
    lateinit var rigth: ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.homemain_layout, container, false)
        initinject()
        initlayout(view)
        for (i in 1..10) {
            val user = UserList("", "", "皮皮虾", "撒大了可打算离开", 11)
            userlist.add(user)
        }
        TIMManager.getInstance().loginUser
        RecyclerViewset1()
        RecyclerViewset2()
        //收到新消息
        //消息的内容解析请参考消息收发文档中的消息解析说明
        //返回true将终止回调链，不再调用下一个新消息监听器
        TIMManager.getInstance().addMessageListener { it ->
            return@addMessageListener false
        }

        view.find<ImageView>(R.id.search).let { it ->
            it.setOnClickListener {
                Bus.send(UpdateMessgeSizeEvent(5))
            }

        }

        return view
    }

    fun RecyclerViewset1() {
        val homeListAdapter = HomeListAdapter(userlist)
        homeListAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity<HomeActivity>()
            }
        }
        chatlist.adapter = homeListAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        chatlist.layoutManager = linearLayoutManager

    }

    fun RecyclerViewset2() {
        val chatListAdapter = ChatListAdapter(userlist)
        chatListAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity<HomeActivity>()
            }
        }
        chatlist2.adapter = chatListAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        chatlist2.layoutManager = linearLayoutManager
        chatlist2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.i("iii", dx.toString())
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val down = chatlist2.canScrollHorizontally(-1)
                val up = chatlist2.canScrollHorizontally(1)
                if (!down) {
                    left.isVisible = false
                    rigth.isVisible = true
                }
                if (!up) {
                    left.isVisible = true
                    rigth.isVisible = false
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    private fun initlayout(view: View) {
        chatlist = view.find(R.id.chatlist)
        chatlist2 = view.find(R.id.chatlist2)
        left = view.find(R.id.left)
        rigth = view.find(R.id.right)
    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(mActivityComponent).homemodule(Homemodule()).build().inject(this)

    }
}