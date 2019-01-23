package com.example.home.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.home.R
import com.example.home.data.articledata
import com.example.home.persenter.articlepersenter
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
                if(body.html==null){
                    toast("内容不能为空")
                    return
                }
                val articledata = articledata(0,Title.text.toString(), "", "", "", id, TIMManager.getInstance().loginUser, body.html,"",ArrayList())
                mpersenter.articledata("Bearer " + jwt!!,articledata)
            }
            R.id.Title->{
                setdatadialog("设置标题",Title)
            }
            R.id.edit->{
                startActivity<qcodeActivity>("id" to id,"type" to "2")
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
        edittext()
    }
    fun edittext() {
        //初始化编辑高度
        body.setEditorHeight(200)
        //初始化字体大小
        body.setEditorFontSize(14)//初始化字体颜色
        body.setEditorFontColor(Color.BLACK)
        body.setEditorBackgroundColor(Color.BLUE)
        //初始化内边距
        body.setPadding(10, 10, 10, 10) //设置编辑框背景，可以是网络图片 //
        //  textedit.setBackground(R.color.white) //
        body.setBackgroundColor(Color.WHITE)
        // mEditor.setBackgroundResource(R.drawable.bg)
        body.setPlaceholder("请输入内容")  //设置默认显示语句
        body.setInputEnabled(true);//设置编辑器是否可用
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