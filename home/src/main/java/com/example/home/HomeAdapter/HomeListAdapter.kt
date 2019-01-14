package com.example.home.HomeAdapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.example.home.R
import com.example.home.common.UpdateMessgeSizeEvent
import com.example.home.data.UserList
import com.example.home.ui.Frament.HomeFarment
import com.example.home.ui.activity.HomeActivity
import com.example.home.ui.activity.madengviewActivity
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupAssistant
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.sns.TIMFriendshipProxy
import kotlinx.android.synthetic.main.personalchatsettingslayout.*
import org.jetbrains.anko.startActivity
import study.kotin.my.baselibrary.common.BaseApplication
import java.text.SimpleDateFormat
import java.util.*


class HomeListAdapter(val context: Context, userList: MutableList<UserList>) : BaseQuickAdapter<UserList, BaseViewHolder>(R.layout.homechat, userList) {
    override fun convert(helper: BaseViewHolder?, item: UserList?) {
//        helper!!.setText(R.id.peername, item!!.Name)
        if (item!!.Name == "马镫助手") {
            helper!!.setText(R.id.peername, item.Name)
            helper.setImageResource(R.id.head, R.drawable.helper)
            val noreadmsg = helper.getView<TextView>(R.id.noreadmsg)
            noreadmsg.isVisible = item.noreadmsg != 0
            noreadmsg.text = "" + item.noreadmsg
            helper.setText(R.id.lastmsg, item.msg)
            helper.setText(R.id.lastmsgtime, item.lastmsgtime.substring(0, item.lastmsgtime.indexOf("T")))
            helper.getView<ConstraintLayout>(R.id.rt).setOnClickListener {
                HomeFarment.count = 0
                context.startActivity<madengviewActivity>()
            }
            return
        }
        helper!!.setText(R.id.lastmsg, item.msg)
        val format: String
        format = if (item.lastmsgtime != "") {
            val simpleDateFormat = SimpleDateFormat("MM月dd日 HH:mm")
            simpleDateFormat.format(Date(item.lastmsgtime.toLong() * 1000))
        } else {
            ""
        }

        helper.setText(R.id.lastmsgtime, format)
        val noreadmsg = helper.getView<TextView>(R.id.noreadmsg)
        if (item.noreadmsg != 0) {
            noreadmsg.isVisible = true
            helper.setText(R.id.noreadmsg, item.noreadmsg.toString())

        } else {
            noreadmsg.isVisible = false
        }
        helper.addOnClickListener(R.id.btnDelete)
        helper.getView<ConstraintLayout>(R.id.rt).setOnClickListener {
            context.startActivity<HomeActivity>("id" to item.Name)

        }
        val friendsById = TIMFriendshipProxy.getInstance().getFriendsById(arrayListOf(item.Name))
        val groups = TIMGroupAssistant.getInstance().getGroups(arrayListOf(item.Name))
        if (friendsById != null) {
            if (friendsById[0].remark != "") {
                helper.setText(R.id.peername, friendsById[0].remark )
            } else {
                helper.setText(R.id.peername, friendsById[0].nickName )
            }
            val head = helper.getView<ImageView>(R.id.head)
            val options = RequestOptions()
                    .placeholder(R.drawable.a4_2)
                    .error(R.drawable.a4_2)
            Glide.with(context)
                    .load(friendsById[0].faceUrl)
                    .apply(options)
                    .into(head)
        } else if(groups!=null&&!groups.isEmpty()){
            val head = helper.getView<ImageView>(R.id.head)
            val options = RequestOptions()
                    .placeholder(R.drawable.qface)
                    .error(R.drawable.qface)
            Glide.with(context)
                    .load(groups[0].groupInfo.faceUrl)
                    .apply(options)
                    .into(head)
            helper.setText(R.id.peername, groups[0].groupInfo.groupName)
        }else{
            downloaddata(item, helper)
        }

    }

    private fun downloaddata(item: UserList, helper: BaseViewHolder) {
        TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf(item.Name), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
            override fun onError(p0: Int, p1: String?) {

            }

            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                if (p0 == null) return
                val edit = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
              //  if (type == 1) {
                    if (p0[0].remark == "") {
                        helper.setText(R.id.peername, p0[0].nickName)
                        edit.putString("${item.Name}name", p0[0].nickName)
                    } else {
                        helper.setText(R.id.peername, p0[0].remark)
                        edit.putString("${item.Name}name", p0[0].remark)
                    }
              //  } else {
                    edit.putString("${item.Name}face", p0[0].faceUrl)

                    val head = helper.getView<ImageView>(R.id.head)
                    val options = RequestOptions()
                            .placeholder(R.drawable.a4_2)
                            .error(R.drawable.a4_2)
                    Glide.with(context)
                            .load(p0[0].faceUrl)
                            .apply(options)
                            .into(head)
                edit.apply()

              //  }
            }
        })
        TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(item.Name), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
            override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                if (p0 == null) return
                val head = helper.getView<ImageView>(R.id.head)

                val options = RequestOptions()
                        .placeholder(R.drawable.qface)
                        .error(R.drawable.qface)
                Glide.with(context)
                        .load(p0[0].faceUrl)
                        .apply(options)
                        .into(head)
                helper.setText(R.id.peername, p0[0].groupName)

            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
    }

}