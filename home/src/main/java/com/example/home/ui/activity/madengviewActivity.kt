package com.example.home.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.home.HomeAdapter.madengviewAdapter
import com.example.home.R
import com.example.home.data.madengdata
import com.example.home.persenter.madenghelperpersenter
import com.example.home.persenter.view.madenghelperView
import kotlinx.android.synthetic.main.madengview.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

class madengviewActivity:BaseMVPActivity<madenghelperpersenter>(),madenghelperView {
    override fun helper(t: List<madengdata>) {
        madengviewAdapter.addData(t)
    }
    lateinit var madengviewAdapter:madengviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.madengview)
        initinject()
        madengviewAdapter = madengviewAdapter(ArrayList())
        madenglist.adapter=madengviewAdapter
        madenglist.layoutManager=LinearLayoutManager(this)
        mpersenter.madenghelper()
        madenglist.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState); //设置什么布局管理器,就获取什么的布局管理器
                val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
                val firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                if(firstCompletelyVisibleItemPosition==0) {
                    mpersenter.madenghelper()
                }
            }
        })
    }
    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView = this
    }

}