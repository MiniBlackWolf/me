package com.example.home.HomeAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.UserList


class HomeListAdapter(userList: ArrayList<UserList>) : BaseQuickAdapter<UserList, BaseViewHolder>(R.layout.homechat, userList) {
    override fun convert(helper: BaseViewHolder?, item: UserList?) {

    }

}