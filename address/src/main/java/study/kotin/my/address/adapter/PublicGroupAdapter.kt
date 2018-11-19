package study.kotin.my.address.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tencent.imsdk.TIMGroupMemberInfo
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import study.kotin.my.address.R

class PublicGroupAdapter(data:MutableList<TIMGroupBaseInfo>):BaseQuickAdapter<TIMGroupBaseInfo,BaseViewHolder>(R.layout.publicgroupitem,data) {
    override fun convert(helper: BaseViewHolder?, item: TIMGroupBaseInfo?) {
        helper!!.setText(R.id.publicgroupname,item!!.groupName)
        TIMGroupManagerExt.getInstance().getGroupMembers(item.groupId,object : TIMValueCallBack<MutableList<TIMGroupMemberInfo>> {
            override fun onSuccess(p0: MutableList<TIMGroupMemberInfo>?) {
                if (p0?.size==null)return
                helper.setText(R.id.publicgroupmb,"成员人数(${p0.size}/100)")
            }
            override fun onError(p0: Int, p1: String?) {
            }
        })
    }
}