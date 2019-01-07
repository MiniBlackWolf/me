package com.example.home.HomeAdapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.articledata
import com.example.home.data.f3data
import com.tencent.imsdk.TIMManager


class PublicGroupFarment_3_adapter(data: List<articledata>) : BaseQuickAdapter<articledata, BaseViewHolder>
(R.layout.publicgroupfarment_3_item, data) {
    override fun convert(helper: BaseViewHolder, item: articledata) {
        helper.setText(R.id.worksg, item.title)
        helper.setText(R.id.worktime, item.createtime.substring(0,item.createtime.indexOf("T")))
        helper.addOnClickListener(R.id.enroll)
        helper.addOnClickListener(R.id.tc)
        for(i in item.num){
            if(i.toAccount==TIMManager.getInstance().loginUser){
                helper.setText(R.id.enroll,"取消参加")
            }
        }

    }
}