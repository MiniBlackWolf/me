package com.example.home.HomeAdapter

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.UserList
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt


class ChatListAdapter(val context: Context, data: List<UserList>) : BaseQuickAdapter<UserList, BaseViewHolder>(R.layout.chatitem3, data) {
    override fun convert(helper: BaseViewHolder, item: UserList) {
        TIMGroupManagerExt.getInstance().getGroupPublicInfo(arrayListOf(item.Name), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                    if (p0 == null) return
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