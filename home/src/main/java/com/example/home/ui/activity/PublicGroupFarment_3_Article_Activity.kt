package com.example.home.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
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
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule

class PublicGroupFarment_3_Article_Activity : BaseMVPActivity<articlepersenter>(), View.OnClickListener,articleView {
    override fun join(t: BaseResp<String>) {
    }

    override fun quit(t: BaseResp<String>) {
    }
    override fun addactive(t: BaseResp<String>) {}
    override fun findactive(t: List<articledata>) {}
    override fun uploadimg(t: BaseResp<String>) {}
    override fun findarticle(r: List<articledata>) {}
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
                val articledata = articledata(0,Title.text.toString(), "", "", "", id, TIMManager.getInstance().loginUser, body.text.toString(),"",ArrayList())
                mpersenter.articledata("Bearer " + jwt!!,articledata)
            }
            R.id.Title->{
                setdatadialog("设置标题",Title)
            }
            R.id.edit->{
                startActivity<qcodeActivity>("id" to id)
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
        Title.setOnClickListener(this)
        edit.setOnClickListener(this)
    }
    private fun setdatadialog(name: String, text: TextView) {
        alert {
            title = name
            val editText = EditText(this@PublicGroupFarment_3_Article_Activity)
            customView = editText
            positiveButton("确定") {
                if (editText.text.toString() == "") {
                    toast("不能为空")
                    return@positiveButton
                }
                text.text = editText.text.toString()
                it.dismiss()
            }
            negativeButton("取消") {
                it.dismiss()
            }
            show()
        }
    }
}