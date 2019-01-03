package com.example.home.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.home.R
import com.example.home.data.articledata
import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.persenter.HomePersenter
import com.example.home.persenter.HomeSeachPersenter
import com.example.home.persenter.articlepersenter
import com.example.home.persenter.view.HomeSeachView
import com.example.home.persenter.view.HomeView
import com.example.home.persenter.view.articleView
import com.tencent.imsdk.TIMManager
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.publicgroupfarment_3_article.*
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

class PublicGroupFarment_3_Article_Activity : BaseMVPActivity<articlepersenter>(), View.OnClickListener,articleView {
    override fun findarticle(r: List<articledata>) {

    }


    override fun article(baseResp: BaseResp<String>) {
        if(baseResp.success){
            finish()
        }else{
            toast("发布失败")
        }
    }

    lateinit var mEditor: RichEditor
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ctf -> {
                finish()
            }
            R.id.ok->{
                val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                if (jwt == "") {
                    return
                }
                val articledata = articledata(Title.text.toString(), "", "", "", id, TIMManager.getInstance().loginUser, body.text.toString(),"")
                mpersenter.articledata("Bearer " + jwt!!,articledata)
            }

        }
    }
    val id:String by lazy { intent!!.extras!!.getString("id") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroupfarment_3_article)
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView=this
        ctf.setOnClickListener(this)
        ok.setOnClickListener(this)

    }
}