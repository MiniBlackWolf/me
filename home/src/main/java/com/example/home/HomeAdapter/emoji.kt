package com.example.home.HomeAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class emoji(data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1, data) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(android.R.id.text1, item)
    }
}