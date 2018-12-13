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
import com.example.home.R
import com.example.home.data.UserList
import com.example.home.ui.activity.HomeActivity
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import org.jetbrains.anko.startActivity
import study.kotin.my.baselibrary.common.BaseApplication
import java.text.SimpleDateFormat
import java.util.*


class HomeListAdapter(val context: Context, userList: List<UserList>) : BaseQuickAdapter<UserList, BaseViewHolder>(R.layout.homechat, userList) {
    override fun convert(helper: BaseViewHolder?, item: UserList?) {
//        helper!!.setText(R.id.peername, item!!.Name)
        helper!!.setText(R.id.lastmsg, item!!.msg)
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
        val name = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("${item.Name}name", "")
        if (name != "") {
            helper.setText(R.id.peername, name)
        } else {
            TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf(item.Name), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
                override fun onError(p0: Int, p1: String?) {

                }

                override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                    val edit = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                    if (p0 == null) return
                    if (p0[0].remark == "") {
                        helper.setText(R.id.peername, p0[0].nickName)
                        edit.putString("${item.Name}name", p0[0].nickName)
                    } else {
                        helper.setText(R.id.peername, p0[0].remark)
                        edit.putString("${item.Name}name", p0[0].remark)
                    }
                    edit.apply()
                    val head = helper.getView<ImageView>(R.id.head)
                    val options =  RequestOptions()
                            .error(R.drawable.a4_2)
                    Glide.with(context)
                            .load(p0[0].faceUrl)
                            .apply(options)
                            .into(head)

                }
            })
            TIMGroupManagerExt.getInstance().getGroupPublicInfo(arrayListOf(item.Name), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                    if (p0 == null) return
                    val edit = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                    helper.setText(R.id.peername, p0[0].groupName)
                    edit.putString("${item.Name}name", p0[0].groupName)
                    edit.apply()
                    val head = helper.getView<ImageView>(R.id.head)
                    val options =  RequestOptions()
                            .error(R.drawable.a4_2)
                    Glide.with(context)
                            .load(p0[0].faceUrl)
                            .apply(options)
                            .into(head)

                }

                override fun onError(p0: Int, p1: String?) {
                }
            })
        }
    }

}