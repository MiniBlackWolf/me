package com.example.home.ui.Frament

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.eightbitlab.rxbus.Bus
import com.example.home.HomeAdapter.ChatListAdapter
import com.example.home.HomeAdapter.HomeListAdapter
import com.example.home.R
import com.example.home.Utils.HomeRefreshView
import com.example.home.common.UpdateMessgeSizeEvent
import com.example.home.data.UserList
import com.example.home.persenter.HomePersenter
import com.example.home.ui.activity.HomeActivity
import com.example.home.ui.activity.SearchActivity
import com.example.home.ui.activity.qcodeActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.tencent.imsdk.*
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMManagerExt
import com.tencent.qcloud.presentation.presenter.ConversationPresenter
import com.tencent.qcloud.presentation.viewfeatures.ConversationView
import kotlinx.android.synthetic.main.homerefreshhead.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import kotlin.collections.LinkedHashSet


class HomeFarment : BaseMVPFragmnet<HomePersenter>(), ConversationView, View.OnClickListener {
    override fun onClick(v: View?) {
        if (TIMManager.getInstance().loginUser == "") {
            toast("请先登录")
            ARouter.getInstance().build("/usercenter/RegisterActivity").navigation(activity as Activity)
            return
        }
        when (v!!.id) {
            R.id.search -> {
                startActivity<SearchActivity>()
            }
            R.id.more -> {


            }
            R.id.sys->{
                startActivity<qcodeActivity>()
            }

        }
    }

    lateinit var lastmsg: String
    /**
     * 初始化界面或刷新界面
     */
    override fun initView(conversationList: MutableList<TIMConversation>?) {
        //   homeRefreshView = HomeRefreshView(mpersenter.context)
        hz.finishRefresh()
        if (TIMManager.getInstance().loginUser == "") {
            RecyclerViewset1(LinkedHashSet<UserList>())
            RecyclerViewset2(LinkedHashSet<UserList>())
            hz.setOnRefreshListener {
                hz.finishRefresh()
            }
        }
        Log.i("iiiiii", "初始化界面或刷新界面")
    }

    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    override fun updateMessage(message: TIMMessage?) {

        val userlist = LinkedHashSet<UserList>()
        val userlist2 = LinkedHashSet<UserList>()
        Log.i("iiiiii", "更新最新消息显示")
        val list = TIMManagerExt.getInstance().conversationList
        for (lists in list) {
            if (lists.peer == TIMManager.getInstance().loginUser) {
                TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.C2C, TIMManager.getInstance().loginUser)
            }
            if (lists.peer == "") {
                TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.C2C, "")
                //     list.remove(lists)
                continue
            }
            val s = TIMConversationExt(lists).getLastMsgs(1)
            var timestamp: String
            if (s.size == 0) {
                lastmsg = ""
                timestamp = ""
            } else {
                when (s.get(0).getElement(0).type) {
                    TIMElemType.Text, TIMElemType.Face -> {
                        lastmsg = (s.get(0).getElement(0) as TIMTextElem).text
                        if (lastmsg.length > 10) {
                            val substring = lastmsg.substring(0, 10)
                            lastmsg = ("$substring....")
                        }
                    }
                    TIMElemType.Image -> lastmsg = "图片"
                    TIMElemType.Sound -> lastmsg = "语音"
                    TIMElemType.Video -> lastmsg = "视频"
                    TIMElemType.GroupTips -> lastmsg = "群消息"
                    //  return new GroupTipMessage(message);
                    TIMElemType.File -> lastmsg = "文件"
                    TIMElemType.UGC -> {
                    }
                    else -> {
                    }
                }
                timestamp = s.get(0).timestamp().toString()

            }
            val unreadMessageNum = TIMConversationExt(lists).unreadMessageNum
            val user = UserList("", "", lists.peer, lastmsg, unreadMessageNum.toInt(), timestamp)
            if (lists.peer.substring(0, 5) == "@TGS#") {
                userlist2.add(user)
            }
            userlist.add(user)


        }

        RecyclerViewset1(userlist)
        RecyclerViewset2(userlist2)
        hz.finishRefresh()
    }

    /**
     * 更新好友关系链消息
     */
    override fun updateFriendshipMessage() {
        Log.i("iiiiii", "更新好友关系链消息")
    }

    /**
     * 删除会话
     */
    override fun removeConversation(identify: String?) {
        Log.i("iiiiii", "删除会话")
    }

    /**
     * 更新群信息
     */
    override fun updateGroupInfo(info: TIMGroupCacheInfo?) {
        Log.i("iiiiii", "更新群信息")
    }

    /**
     * 刷新
     */
    override fun refresh() {
        mpersenter.getdatas()
        Log.i("iiiiii", "刷新")
    }


    lateinit var chatlist: RecyclerView
    lateinit var chatlist2: RecyclerView
    lateinit var sys:ImageView
    lateinit var left: ImageView
    lateinit var rigth: ImageView
    lateinit var hz: SmartRefreshLayout
    val conversationPresenter = ConversationPresenter(this)
    val homeRefreshView by lazy {    HomeRefreshView(activity as Activity).initview(chatlist2, hz) { conversationPresenter.getConversation() }}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.homemain_layout, container, false)
        initinject()
        initlayout(view)
        conversationPresenter.getConversation()
        //    homeRefreshView = HomeRefreshView(activity as Activity).initview(chatlist2, hz) { conversationPresenter.getConversation() }
        val list = TIMManagerExt.getInstance().conversationList
        if (list.size == 0) {
            RecyclerViewset1(LinkedHashSet<UserList>())
            RecyclerViewset2(LinkedHashSet<UserList>())
            hz.setOnRefreshListener {
                hz.finishRefresh()
            }
        } else {
            hz.setOnRefreshListener {
                conversationPresenter.getConversation()
            }
        }
        hz.setRefreshHeader(homeRefreshView)
        hz.setHeaderMaxDragRate(4f)
        mpersenter.getdatas()
        return view
    }

    //消息列表
    fun RecyclerViewset1(userlist: LinkedHashSet<UserList>) {
        var noReadAllCount: Int = 0
        val homeListAdapter = HomeListAdapter(mpersenter.context, userlist.toList())
        val textView = TextView(mpersenter.context)
        textView.text = "没有更多消息了哦"
        textView.gravity = Gravity.CENTER
        homeListAdapter.emptyView = textView
//        homeListAdapter.onItemChildClickListener  = object : BaseQuickAdapter.OnItemChildClickListener {
//            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
//                startActivity<HomeActivity>("id" to userlist.toList().get(position).Name)
//            }
//
//        }

        homeListAdapter.setOnItemChildClickListener { adapter, view, position ->
            let {
                val id = userlist.toList().get(position).Name
                if (id.substring(0, 5) == "@TGS#") {
                    TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.Group, id)
                    ConversationPresenter(this).getConversation()
                } else {
                    TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.C2C, id)
                    ConversationPresenter(this).getConversation()
                }
            }
        }
        chatlist.adapter = homeListAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        chatlist.layoutManager = linearLayoutManager
        //未读总计数
        for (i in userlist) {
            noReadAllCount += i.noreadmsg
        }
        Bus.send(UpdateMessgeSizeEvent(noReadAllCount))
        chatlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState); //设置什么布局管理器,就获取什么的布局管理器
                val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
                val firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
//                if(firstCompletelyVisibleItemPosition==0) {
//                    easylayout.isClickable = true
//                }else{
//                    easylayout.isClickable = false
//                }
            }
        })
    }

    //消息上方列表
    fun RecyclerViewset2(userlist: LinkedHashSet<UserList>) {
        val chatListAdapter = ChatListAdapter(mpersenter.context, userlist.toList())
        val textView = TextView(mpersenter.context)
        textView.text = "没有更多社团了哦"
        textView.gravity = Gravity.TOP
        chatListAdapter.emptyView = textView
        chatListAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity<HomeActivity>("id" to userlist.toList().get(position).Name)
            }
        }
        val chatlistrc = homeRefreshView.chatlistrc
        chatlistrc.adapter = chatListAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        chatlistrc.layoutManager = linearLayoutManager
        chatlistrc.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isSlidingToLast = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                // dx>0:向右滑动,dx<0:向左滑动
                // dy>0:向下滑动,dy<0:向上滑动
                isSlidingToLast = dy > 0


            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState); //设置什么布局管理器,就获取什么的布局管理器
                val manager = recyclerView.getLayoutManager() as LinearLayoutManager// 当停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //获取最后一个完全显示的ItemPosition ,角标值
                    val lastVisibleItem = manager.findLastCompletelyVisibleItemPosition()
                    //所有条目,数量值
                    val totalItemCount = manager.getItemCount();// 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码 }
                        hz.right2.isVisible = false
                        hz.left2.isVisible = true
                    } else {
                        hz.right2.isVisible = true
                        hz.left2.isVisible = false
                    }

                }
            }
        })
    }

    private fun initlayout(view: View) {
        chatlist = view.find(R.id.chatlist)
        chatlist2 = view.find(R.id.chatlist2)
        left = view.find(R.id.left)
        rigth = view.find(R.id.right)
        hz = view.find(R.id.hz)
        //   easylayout = view.find(R.id.easylayout)
        view.find<ImageView>(R.id.search).setOnClickListener(this)
        view.find<ImageView>(R.id.more).setOnClickListener(this)
        oooo = view.find(R.id.oooo)
        sys=view.find<ImageView>(R.id.sys)
        sys .setOnClickListener(this)
    }

    lateinit var oooo: TextView
    //注入
    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(mActivityComponent).homemodule(Homemodule()).build().inject(this)

    }

    override fun onResume() {
        val conversationPresenter = ConversationPresenter(this)
        conversationPresenter.getConversation()
        super.onResume()
    }


}