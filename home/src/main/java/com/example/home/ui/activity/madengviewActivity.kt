package com.example.home.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.home.HomeAdapter.madengviewAdapter
import com.example.home.R
import com.example.home.data.madengdata
import com.example.home.persenter.madenghelperpersenter
import com.example.home.persenter.view.madenghelperView
import com.scwang.smartrefresh.header.DeliveryHeader
import com.scwang.smartrefresh.header.FlyRefreshHeader
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.header.WaveSwipeHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.madengview.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import java.util.*

class madengviewActivity:BaseMVPActivity<madenghelperpersenter>(),madenghelperView {
    override fun helpererror(e: Throwable) {
        hz.finishRefresh(1000,false)
    }

    override fun helper(t: List<madengdata>) {
        madengviewAdapter.addData(0,t)
        hz.finishRefresh(1000,true)
        madenglist.scrollToPosition(0)
    }
    lateinit var madengviewAdapter:madengviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.madengview)
        initinject()
        //初始适配器
        madengviewAdapter = madengviewAdapter(ArrayList())
        madenglist.adapter=madengviewAdapter
        madenglist.layoutManager=LinearLayoutManager(this)
        //初始数据
        mpersenter.madenghelper(1)
        //刷新
        hz.setOnRefreshListener {
            val random = Random()
            mpersenter.madenghelper(random.nextInt(10))
        }
        hz.setRefreshHeader(MaterialHeader(this))
        hz.setEnableOverScrollDrag(true)
        //返回
        chatfh.setOnClickListener {
            finish()
        }
    }
    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView = this
    }

}