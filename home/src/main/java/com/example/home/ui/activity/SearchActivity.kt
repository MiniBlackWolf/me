package com.example.home.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.home.HomeAdapter.SearchAdapter
import com.example.home.R
import com.example.home.data.seachAlldata
import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import com.example.home.persenter.HomeSeachPersenter
import com.example.home.persenter.view.HomeSeachView
import kotlinx.android.synthetic.main.searchlayout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import java.util.regex.Pattern

@Route(path = "/home/searchactivity")
class SearchActivity : BaseMVPActivity<HomeSeachPersenter>(), View.OnClickListener, HomeSeachView {

    override fun search(searchuserdata: searchuserdata) {
        val list=ArrayList<seachAlldata>()
        for(s in searchuserdata.rows){
            list.add(seachAlldata(searchuserdata.itemType,s.toAccount,s.tagProfileImNick,s.tagProfileImImage,
                    s.tagProfileImGender,s.tagProfileImSelfsignature,"") )
        }
        searchAdapter.addData(list)
        hideLoading()
    }

    override fun GroupSearch(searchgroupdata: searchgroupdata) {
        val list=ArrayList<seachAlldata>()
        for(s in searchgroupdata.rows){
            list.add(seachAlldata(searchgroupdata.itemType,s.groupid,s.name,s.faceurl,"",
                    if(s.introduction==null) "" else s.introduction ,
                    s.authentication
            ) )
        }
        searchAdapter.addData(list)
        hideLoading()
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fh -> {
                finish()
            }
            R.id.startsearch -> {
                 val patternnmber = """^[0-9]*${'$'}"""
                val matcher = Pattern.compile(patternnmber).matcher(contentview.text.toString().trim())
                if(matcher.find()&&contentview.text.toString().length<6){
                    toast("id不能小于6位")
                    return
                }
                showLoading()
                searchAdapter.data.clear()
                searchlstview.removeAllViewsInLayout()
                val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                if (jwt != "") {
                    mpersenter.searchuser("Bearer $jwt", contentview.text.toString(),this)
                    mpersenter.searchgroup("Bearer $jwt", contentview.text.toString(),this)
                } else {
                    toast("请重试")
                }
                val textView = TextView(this)
                textView.setText("没有找到搜索结果!")
                textView.gravity = Gravity.CENTER
                searchAdapter.emptyView = textView
            }
        }
    }

    lateinit var searchAdapter: SearchAdapter
    private fun initdataview() {
        searchAdapter = SearchAdapter(ArrayList())
        searchAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val data = adapter!!.data as List<seachAlldata>
                if(data[position].type==2){
                    startActivity<PublicGroupmsgActivity>("id" to data[position].id)
                }else{
                    startActivity<PersonalhomeActivity>("id" to data[position].id)
                }
            }
        }
        searchlstview.adapter = searchAdapter
        searchlstview.layoutManager = LinearLayoutManager(this@SearchActivity)
        fh.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initinject()
        setContentView(R.layout.searchlayout)
        startsearch.setOnClickListener(this)
        initdataview()
    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView = this
    }

}