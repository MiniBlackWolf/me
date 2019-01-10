package com.example.home.ui.Frament

import android.content.Context
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
import com.example.home.data.articledata
import com.example.home.persenter.articlepersenter
import com.example.home.persenter.view.articleView
import com.tencent.open.utils.Global
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

class PublicGroupFarment_4 : BaseMVPFragmnet<articlepersenter>(), articleView {
    override fun join(t: BaseResp<String>) {
    }

    override fun quit(t: BaseResp<String>) {
    }

    override fun addactive(t: BaseResp<String>) {

    }

    override fun findactive(t: List<articledata>) {
    }

    override fun uploadimg(t: BaseResp<String>) {
    }

    override fun article(baseResp: BaseResp<String>) {
    }

    override fun findarticle(r: List<articledata>) {
        if (!r.isEmpty()) {
            //更新文章
            publicGroupFarment_4_adapter.addData(r)
        }
    }
    val jwt by lazy { activity!!.getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "") }
    lateinit var publicGroupFarment_4_adapter: PublicGroupFarment_4_adapter
    val id by lazy { activity!!.intent!!.extras!!.getString("id") }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val views = inflater.inflate(R.layout.publicgroupitem_4, container, false)
        initinject()
        val f4list = views.find<RecyclerView>(R.id.f4list)
        publicGroupFarment_4_adapter = PublicGroupFarment_4_adapter(ArrayList())
        //全文点击事件
        publicGroupFarment_4_adapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.enroll->{
                    val content = adapter.getViewByPosition(f4list, position, R.id.content) as TextView
                    val enroll = adapter.getViewByPosition(f4list, position, R.id.enroll) as TextView
                    if(content.getTag() as Boolean){
                        content.setSingleLine(true)
                        content.tag=false
                        enroll.setText("全文")
                    }else{
                        content.setSingleLine(false)
                        content.tag=true
                        enroll.setText("收回")
                    }
                }
            }
        }
        val textView = TextView(activity)
        textView.text = "没有更多文章咯..."
        textView.gravity = Gravity.CENTER
        publicGroupFarment_4_adapter.setEmptyView(textView)
        f4list.adapter = publicGroupFarment_4_adapter
        f4list.layoutManager = LinearLayoutManager(activity)
        //网络请求
        val map=HashMap<String,String>()
        map["id"] = id
        mpersenter.findarticle("Bearer " + jwt!!,map)
        return views
    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(mActivityComponent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView = this
    }

}