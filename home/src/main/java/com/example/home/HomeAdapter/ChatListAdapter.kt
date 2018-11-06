package com.example.home.HomeAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.UserList

class ChatListAdapter (data:List<UserList>):BaseQuickAdapter<UserList,BaseViewHolder>(R.layout.chatitem3,data){
    override fun convert(helper: BaseViewHolder?, item: UserList?) {

    }

}