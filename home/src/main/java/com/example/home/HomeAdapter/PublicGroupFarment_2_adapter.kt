package com.example.home.HomeAdapter

import android.widget.TextView
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.tencent.imsdk.*


class PublicGroupFarment_2_adapter(data: MutableList<TIMGroupMemberInfo>) : BaseQuickAdapter<TIMGroupMemberInfo, BaseViewHolder>
(R.layout.publicgroupfarment_2_item, data) {
    override fun convert(helper: BaseViewHolder?, item: TIMGroupMemberInfo?) {
        TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf(item!!.user), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                if (p0 == null) return
                helper!!.setText(R.id.Gname, p0[0].nickName)
                if (p0[0].selfSignature == "") {
                    helper.setText(R.id.gintroduce,"这个人很懒没写简介...")

                } else {
                    helper.setText(R.id.gintroduce, p0[0].selfSignature)
                }
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
        val role = helper!!.getView<TextView>(R.id.role)
        when (item.role) {
            TIMGroupMemberRoleType.Owner -> {
                role.isVisible = true
                role.text = "社长"
            }
            TIMGroupMemberRoleType.Admin -> {
                role.isVisible = true
                role.text = "管理"
            }
            else -> {
                role.isVisible = false
            }
        }
    }
}