package com.example.home.HomeAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.madengdata

class madengviewAdapter(data:ArrayList<madengdata>):BaseQuickAdapter<madengdata,BaseViewHolder>(R.layout.madengviewitem,data) {
    override fun convert(helper: BaseViewHolder?, item: madengdata?) {

    }
}