package com.example.home.ui.activity

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
import com.example.home.persenter.HomePersenter
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import kotlinx.android.synthetic.main.searchlayout.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

@Route(path = "/home/searchactivity")
class SearchActivity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.startsearch -> {
                textlist.isVisible = true
                val input = contentview.text.toString()
                if (input.length > 5) {
                    if (input.substring(0, 5) == "@TGS#") {
                        TIMGroupManagerExt.getInstance().getGroupPublicInfo(arrayListOf(input), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                            override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                                updataview(searchdata(p0!![0],"G"))
                            }

                            override fun onError(p0: Int, p1: String?) {
                                updataview(null)
                                Log.e("eeeeeeeee", p1)
                                toast("$p1")
                            }
                        })

                    } else {
                        TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf("86-$input"), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
                            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                                updataview(searchdata(p0!![0],"U"))
                            }

                            override fun onError(p0: Int, p1: String?) {
                                updataview(null)
                                Log.e("eeeeeeeee", p1)
                                toast("$p1")
                            }
                        })

                    }
                }
            }

        }
    }

    private fun updataview(p0: searchdata?) {
        val searchAdapter = SearchAdapter(arrayListOf(p0))
        searchAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

            }
        }
        searchlstview.adapter = searchAdapter
        searchlstview.layoutManager = LinearLayoutManager(this@SearchActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initinject()
        setContentView(R.layout.searchlayout)
        startsearch.setOnClickListener(this)

    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)
    }

}