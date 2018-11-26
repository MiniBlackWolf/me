package com.example.home.ui.Frament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.home.HomeAdapter.PublicGroupFarment_2_adapter
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.example.home.ui.activity.PersonalhomeActivity
import com.tencent.imsdk.TIMGroupMemberInfo
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet

class PublicGroupFarment_2 : BaseMVPFragmnet<HomePersenter>() {
    val id by lazy { activity?.intent?.extras?.getString("id") }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.publicgroupitem_2, container, false)
        val AllMembers = view.find<TextView>(R.id.AllMembers)
        val Memberlist = view.find<RecyclerView>(R.id.Memberlist)
        TIMGroupManagerExt.getInstance().getGroupMembers(id!!, object : TIMValueCallBack<MutableList<TIMGroupMemberInfo>> {
            override fun onSuccess(p0: MutableList<TIMGroupMemberInfo>?) {
                if (p0 == null) return
                AllMembers.text = "共${p0.size}人"
                val publicGroupFarment_2_adapter = PublicGroupFarment_2_adapter(p0)
                publicGroupFarment_2_adapter.setOnItemClickListener { adapter, view, position ->
                    startActivity<PersonalhomeActivity>("id" to p0[position].user)
                }
                Memberlist.adapter=publicGroupFarment_2_adapter
                Memberlist.layoutManager=LinearLayoutManager(activity)
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
        return view
    }

}