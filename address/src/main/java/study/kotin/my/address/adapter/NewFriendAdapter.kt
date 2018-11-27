package study.kotin.my.address.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tencent.imsdk.ext.sns.TIMFriendFutureItem
import study.kotin.my.address.R

class NewFriendAdapter(data:List<TIMFriendFutureItem>):BaseQuickAdapter<TIMFriendFutureItem,BaseViewHolder>(R.layout.newfrienditem,data) {
    override fun convert(helper: BaseViewHolder?, item: TIMFriendFutureItem?) {

    }
}