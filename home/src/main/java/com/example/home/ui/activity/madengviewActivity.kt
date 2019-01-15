package com.example.home.ui.activity

import android.content.Context
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
import org.jetbrains.anko.startActivity
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import java.util.*

@Suppress("UNCHECKED_CAST")
class madengviewActivity : BaseMVPActivity<madenghelperpersenter>(), madenghelperView {
    val jwt by lazy { getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "") }
   // var data: List<madengdata>? = null
    override fun helpererror(e: Throwable) {
        hz.finishRefresh(1000, false)
    }

    override fun helper(t: List<madengdata>) {
     //   data = t
        madengviewAdapter.addData(0, t)
        hz.finishRefresh(1000, true)
        madenglist.scrollToPosition(0)
    }

    lateinit var madengviewAdapter: madengviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.madengview)
        initinject()
        //初始适配器
        madengviewAdapter = madengviewAdapter(this, ArrayList())
        madengviewAdapter.setOnItemChildClickListener { adapter, view, position ->
            val data=adapter.data as List<madengdata>
            when(view.id){
                R.id.cardView->{
                    startActivity<madenghelprinfoactivity>("id" to data[position].id.toString(),"type" to data[position].type.toString())
                }
            }

        }

        madenglist.adapter = madengviewAdapter
        madenglist.layoutManager = LinearLayoutManager(this)
        //初始数据
        mpersenter.madenghelper("Bearer " + jwt!!, 1)
        //刷新
        hz.setOnRefreshListener {
            val random = Random()
            mpersenter.madenghelper("Bearer " + jwt!!, random.nextInt(10))
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