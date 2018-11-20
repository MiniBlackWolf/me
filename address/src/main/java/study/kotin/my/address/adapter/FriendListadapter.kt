package study.kotin.my.address.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import study.kotin.my.address.R
import study.kotin.my.address.data.UserInfoData

class FriendListadapter(data: List<UserInfoData>) : BaseQuickAdapter<UserInfoData, BaseViewHolder>(R.layout.friendlistitem, data) {
    override fun convert(helper: BaseViewHolder?, item: UserInfoData?) {
        helper!!.setText(R.id.fdnames, item!!.names)
    }
}