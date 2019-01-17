package com.example.home.HomeAdapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.articledata
import com.example.home.data.f4data


class PublicGroupFarment_4_adapter(data: List<articledata>) : BaseQuickAdapter<articledata, BaseViewHolder>
(R.layout.publicgroupfarment_4_item, data) {
    override fun convert(helper: BaseViewHolder, item: articledata) {
        helper.setText(R.id.worksg,item.title)
        helper.setText(R.id.worktime,item.createtime?.substring(0,item.createtime.indexOf("T")))
        helper.setText(R.id.content,item.content)
        helper.addOnClickListener(R.id.enroll)
        helper.setTag(R.id.content,false)
    }
}