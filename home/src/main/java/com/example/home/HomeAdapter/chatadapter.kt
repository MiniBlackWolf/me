package com.example.home.HomeAdapter

import android.content.Context

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.common.Msg

class chatadapter(data: ArrayList<Msg>?, private val context: Context) : BaseMultiItemQuickAdapter<Msg, BaseViewHolder>(data) {


    init {
        addItemType(Msg.TYPE_RECEIVED, R.layout.chatitem2)
        addItemType(Msg.TYPE_SENT, R.layout.chatitem)
    }

    override fun convert(helper: BaseViewHolder, item: Msg) {
        when (helper.itemViewType) {
            Msg.TYPE_RECEIVED -> helper.setText(R.id.chatmsg2, item.content + "")
            Msg.TYPE_SENT -> {
                helper.setText(R.id.chatname, "gg")
                helper.setText(R.id.chatmsg1, item.content + "")
            }
        }

    }

}
