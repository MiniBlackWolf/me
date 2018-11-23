package com.example.home.ui.Frament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.home.HomeAdapter.PublicGroupFarment_4_adapter
import com.example.home.R
import com.example.home.persenter.HomePersenter
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet

class PublicGroupFarment_4 : BaseMVPFragmnet<HomePersenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.publicgroupitem_4, container, false)
        val f4list=view.find<RecyclerView>(R.id.f4list)
        val publicGroupFarment_4_adapter = PublicGroupFarment_4_adapter(ArrayList())
        val textView = TextView(activity)
        textView.text="没有更多文章咯..."
        textView.gravity= Gravity.CENTER
        publicGroupFarment_4_adapter.setEmptyView(textView)
        f4list.adapter=publicGroupFarment_4_adapter
        f4list.layoutManager=LinearLayoutManager(activity)
        return view
    }

}