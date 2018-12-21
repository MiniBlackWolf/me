package com.example.home.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.home.HomeAdapter.SearchAdapter
import com.example.home.R
import com.example.home.data.searchdata
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import com.example.home.persenter.HomePersenter
import com.example.home.persenter.HomeSeachPersenter
import com.example.home.persenter.view.HomeSeachView
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import kotlinx.android.synthetic.main.searchlayout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

@Route(path = "/home/searchactivity")
class SearchActivity : BaseMVPActivity<HomeSeachPersenter>(), View.OnClickListener, HomeSeachView {
    override fun searchuser(searchuserdata: searchuserdata) {
        updataview(searchuserdata)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fh -> {
                finish()
            }
            R.id.startsearch -> {
                val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
          if(jwt!=""){
              mpersenter.searchuser("Bearer $jwt", sendsearchuserdata(contentview.text.toString(),0,20,"","",""))
          }else{
              toast("请重试")
          }
            }
        }
    }

    private fun updataview(p0: searchuserdata?) {
        val searchAdapter = SearchAdapter(p0?.results)
        searchAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
//                val data = adapter!!.data as List<searchuserdata.ResultsBean>
//                if(data[position].type=="G"){
//                  val  ids=data[position].data as TIMGroupDetailInfo
//               //     startActivity<PersonalhomeActivity>("id" to ids.identifier)
//                }else{
//                    val  ids=data[position].data as TIMUserProfile
//                    startActivity<PersonalhomeActivity>("id" to ids.identifier)
//                }
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

    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView =this
    }

}