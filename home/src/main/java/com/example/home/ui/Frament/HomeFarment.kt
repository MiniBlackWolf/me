package com.example.home.ui.Frament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.tencent.imsdk.*
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo
import com.tencent.qcloud.presentation.presenter.ConversationPresenter
import com.tencent.qcloud.presentation.viewfeatures.ConversationView
import org.jetbrains.anko.support.v4.startActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import java.util.*


class HomeFarment : BaseMVPFragmnet<HomePersenter>(), ConversationView {
    lateinit var lastmsg: String

    /**
     * 初始化界面或刷新界面
     */
    override fun initView(conversationList: MutableList<TIMConversation>?) {

    }

    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    override fun updateMessage(message: TIMMessage?) {
        when (message!!.getElement(0).type) {
            TIMElemType.Text, TIMElemType.Face -> lastmsg = (message.getElement(0) as TIMTextElem).text
            TIMElemType.Image -> lastmsg = "图片"
            TIMElemType.Sound -> lastmsg = "语音"
            TIMElemType.Video -> lastmsg = "视频"
            TIMElemType.GroupTips -> return
            //  return new GroupTipMessage(message);
            TIMElemType.File -> lastmsg = "文件"
            TIMElemType.UGC -> return
            else -> return
        }
        val user = UserList("", "", message.conversation.peer, lastmsg, message.msg.seq().toInt(),message.timestamp().toString())
        userlist.add(user)
        RecyclerViewset1()
        RecyclerViewset2()
    }

    /**
     * 更新好友关系链消息
     */
    override fun updateFriendshipMessage() {
    }

    /**
     * 删除会话
     */
    override fun removeConversation(identify: String?) {
    }

    /**
     * 更新群信息
     */
    override fun updateGroupInfo(info: TIMGroupCacheInfo?) {
    }

    /**
     * 刷新
     */
    override fun refresh() {
    }

    val userlist = HashSet<UserList>()
    lateinit var chatlist: RecyclerView
    lateinit var chatlist2: RecyclerView
    lateinit var left: ImageView
    lateinit var rigth: ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.homemain_layout, container, false)
        initinject()
        initlayout(view)

        val conversationPresenter = ConversationPresenter(this)
        conversationPresenter.getConversation()
        view.find<ImageView>(R.id.search).let { it ->
            it.setOnClickListener {
                Bus.send(UpdateMessgeSizeEvent(5))
            }

        }

        return view
    }

    //消息列表
    fun RecyclerViewset1() {
        val homeListAdapter = HomeListAdapter(userlist.toList())
        homeListAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val viewByPosition:TextView = adapter!!.getViewByPosition(chatlist, position, R.id.peername) as TextView
                startActivity<HomeActivity>("id" to viewByPosition.text)

            }
        }
        chatlist.adapter = homeListAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        chatlist.layoutManager = linearLayoutManager

    }

    //消息上方列表
    fun RecyclerViewset2() {
        val chatListAdapter = ChatListAdapter(userlist.toList())
        chatListAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
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

    //注入
    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(mActivityComponent).homemodule(Homemodule()).build().inject(this)

    }
}