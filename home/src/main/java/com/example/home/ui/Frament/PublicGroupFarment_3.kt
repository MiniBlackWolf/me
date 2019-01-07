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
import com.example.home.data.articledata
import com.example.home.persenter.HomePersenter
import com.example.home.persenter.articlepersenter
import com.example.home.persenter.view.articleView
import com.example.home.ui.activity.ActivityInfoActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

class PublicGroupFarment_3 : BaseMVPFragmnet<articlepersenter>(), articleView {
    override fun article(baseResp: BaseResp<String>) {}
    override fun findarticle(r: List<articledata>) {}
    override fun addactive(t: BaseResp<String>) {}
    override fun uploadimg(t: BaseResp<String>) {}
    override fun join(t: BaseResp<String>) {
        if(t.success){
            hideLoading()
            toast("参加成功")
        }else{
            hideLoading()
            toast("参加失败，请检查网络")
        }
    }

    override fun quit(t: BaseResp<String>) {
        if(t.success){
            hideLoading()
            toast("退出成功")
        }else{
            hideLoading()
            toast("退出失败，请检查网络")
        }
    }

    override fun findactive(t: List<articledata>) {
        publicGroupFarment_3_adapter.addData(t)
    }

    val id by lazy { activity!!.intent.extras!!.getString("id") }
    lateinit var publicGroupFarment_3_adapter: PublicGroupFarment_3_adapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.publicgroupitem_3, container, false)
        initinject()
        //初始化适配器
        val f3list = view.find<RecyclerView>(R.id.f3list)
        publicGroupFarment_3_adapter = PublicGroupFarment_3_adapter(ArrayList())
        publicGroupFarment_3_adapter.setOnItemChildClickListener { adapter, views, position ->
            val data = adapter.data as List<articledata>
            when (views.id) {
                R.id.enroll -> {
                    showLoading()
                    val enroll = adapter.getViewByPosition(f3list, position, R.id.enroll) as TextView
                    if (enroll.text.toString() == "报名参加") {
                        mpersenter.join(this,data[position].id)
                        enroll.text="取消参加"
                    } else {
                        mpersenter.quit(this,data[position].id)
                        enroll.text="报名参加"
                    }

                }
                R.id.tc ->{
                    startActivity<ActivityInfoActivity>("articledata" to data[position])
                }
            }
        }
        //添加空视图
        val textView = TextView(activity)
        textView.text = "没有更多活动咯..."
        textView.gravity = Gravity.CENTER
        publicGroupFarment_3_adapter.setEmptyView(textView)
        f3list.adapter = publicGroupFarment_3_adapter
        f3list.layoutManager = LinearLayoutManager(activity)
        //请求活动列表
        val map = HashMap<String, String>()
        map.put("communityid", id)
        mpersenter.findactive(map)
        return view
    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(mActivityComponent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView = this
    }


}