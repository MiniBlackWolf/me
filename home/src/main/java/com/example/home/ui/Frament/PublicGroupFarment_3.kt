package com.example.home.ui.Frament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.home.HomeAdapter.PublicGroupFarment_3_adapter
import com.example.home.R
import com.example.home.persenter.HomePersenter
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet

class PublicGroupFarment_3 : BaseMVPFragmnet<HomePersenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.publicgroupitem_3, container, false)
        val f3list = view.find<RecyclerView>(R.id.f3list)
        val publicGroupFarment_3_adapter = PublicGroupFarment_3_adapter(ArrayList())
        val textView = TextView(activity)
        textView.text="没有更多活动咯..."
        textView.gravity=Gravity.CENTER
        publicGroupFarment_3_adapter.setEmptyView(textView)
        f3list.adapter=publicGroupFarment_3_adapter
        f3list.layoutManager=LinearLayoutManager(activity)
        return view
    }

}