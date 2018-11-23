package com.example.home.HomeAdapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.f3data


class PublicGroupFarment_3_adapter(data: List<f3data>) : BaseQuickAdapter<f3data, BaseViewHolder>
(R.layout.publicgroupfarment_3_item, data) {
    override fun convert(helper: BaseViewHolder, item: f3data) {
        helper.setText(R.id.worksg, item.workname)
        helper.setText(R.id.worktime, item.worktime)
        helper.addOnClickListener(R.id.enroll)
    }
}