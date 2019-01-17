package com.example.home.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.home.R
import com.example.home.data.articledata
import com.example.home.persenter.HomePersenter
import com.example.home.persenter.articlepersenter
import com.example.home.persenter.view.articleView
import com.google.gson.Gson
import com.tencent.imsdk.TIMManager
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import jsc.kit.datetimepicker.widget.DateTimePicker
import kotlinx.android.synthetic.main.publicgroupfarment_3_activity.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.GifSizeFilter
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddActivity : BaseMVPActivity<articlepersenter>(), View.OnClickListener, articleView {
    override fun join(t: BaseResp<String>) {

    }

    override fun quit(t: BaseResp<String>) {
    }

    override fun article(baseResp: BaseResp<String>) {}
    override fun findarticle(r: List<articledata>) {}
    override fun addactive(t: BaseResp<String>) {
        if (t.success) {
            hideLoading()
            toast("发布成功")
            finish()
        } else {
            hideLoading()
            toast("发布失败")
        }

    }

    override fun findactive(t: List<articledata>) {
    }

    override fun uploadimg(t: BaseResp<String>) {
        if (t.success) {
            hideLoading()
            var replace = t.message.replace("[", "")
            replace = replace.replace("]", "")
            val split = replace.split(",")
            var html = textedit.html
            val indexOf = html.indexOf("src", 0)
            getimagstops(indexOf)
            if (list.isNotEmpty()) {
                for (i in 0 until list.size) {
                    val substring = html.substring(list[i] + 5, html.indexOf("\"", list[i] + 5))
                    html = html.replace(substring, split[i])
                }
            }
            val articledata = articledata(0, titles.text.toString(), timestart.text.toString(), timeend.text.toString(), address.text.toString(),
                    id, TIMManager.getInstance().loginUser, html, "", ArrayList())
            val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
            if (jwt == "") {
                return
            }
            mpersenter.addactive("Bearer " + jwt!!, this@AddActivity, articledata)

        } else {
            hideLoading()
            toast("图片上传失败！请检查网络后重试")
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ctf -> finish()
            R.id.textedit -> {

            }
            R.id.addimg -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(1)
                        .capture(false)
                        .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(study.kotin.my.baselibrary.common.GlideEngine())
                        .forResult(1)
            }
            R.id.textView24 -> {
                textedit.isVisible = false
                addimg.isVisible = false
            }
            R.id.constraintLayout6 -> {
                if (!textedit.isVisible) {
                    textedit.isVisible = true
                    addimg.isVisible = true
                    view3.isVisible = true
                } else {
                    textedit.isVisible = false
                    addimg.isVisible = false
                    view3.isVisible = false
                }

            }
            R.id.ok -> {
                if (textedit.html == null) {
                    toast("内容不能是空")
                    return
                }
                showLoading()
                //构建上传文件

                if (imgpath.isNotEmpty()) {
                    val Builder = MultipartBody.Builder()
                    Builder.setType(MultipartBody.FORM)
                    for (file in imgpath) {
                        val create = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        Builder.addFormDataPart("files", file.name, create)
                    }
                    val parts = Builder.build().parts()
                    val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                    if (jwt == "") {
                        return
                    }
                    mpersenter.uploadimg("Bearer " + jwt!!, this@AddActivity, parts)
                }else{
                    val articledata = articledata(0, titles.text.toString(), timestart.text.toString(), timeend.text.toString(), address.text.toString(),
                            id, TIMManager.getInstance().loginUser, textedit.html, "", ArrayList())
                    val jwt = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
                    if (jwt == "") {
                        return
                    }
                    mpersenter.addactive("Bearer " + jwt!!, this@AddActivity, articledata)
                }
            }
            R.id.timestart -> {
                settime(timestart)
            }
            R.id.timeend -> {
                settime(timeend)
            }
            R.id.titles -> {
                setdatadialog("设置标题", titles)
            }
            R.id.address -> {
                setdatadialog("设置地点", address)
            }
            R.id.edit -> {
                startActivity<qcodeActivity>("id" to id, "type" to "1")
            }

        }
    }

    private fun settime(time: TextView) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 1918)
        calendar.set(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        val startDate = calendar.time
        val TBuilder = DateTimePicker.Builder(this)
                .setTitle("选择年月日")
                .setCancelTextColor(Color.BLACK)
                .setOkTextColor(resources.getColor(R.color.colorPrimary))
                .setTitleTextColor(-0x666667)
                .setSelectedTextColor(resources.getColor(R.color.colorAccent))
                .setKeepLastSelected(true)
                .setShowYMDHMLabel(true)
                .setShowType(DateTimePicker.ShowType.MINUTE)
        DateTimePicker(this, object : DateTimePicker.ResultHandler {
            override fun handle(date: Date) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val format = simpleDateFormat.format(date)
                time.text = format
                Log.i("iiiiiiiii", date.toString())
            }
        }, startDate, Calendar.getInstance().time, TBuilder).show(Calendar.getInstance().time)
    }

    val id by lazy { intent.extras!!.getString("id") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroupfarment_3_activity)
        initview()
        edittext()
    }

    private fun initview() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)
        mpersenter.mView = this
        ctf.setOnClickListener(this)
        textedit.setOnClickListener(this)
        addimg.setOnClickListener(this)
        textView24.setOnClickListener(this)
        constraintLayout6.setOnClickListener(this)
        ok.setOnClickListener(this)
        timestart.setOnClickListener(this)
        timeend.setOnClickListener(this)
        titles.setOnClickListener(this)
        address.setOnClickListener(this)
        edit.setOnClickListener(this)
    }

    var imgpath = ArrayList<File>()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (data == null) {
                return
            }
            //获取图片路径
            val obtainPathResult = Matisse.obtainPathResult(data)
            val file = File(obtainPathResult[0])
            if (file.length() > 2097152) {
                toast("图片不能大于2mb")
                return
            }
            textedit.insertImage("file:///${obtainPathResult[0]}", "huangxiaoguo\" style=\"max-width:50%")
            imgpath.add(File(obtainPathResult[0]))

            Log.d("Matisse", "mSelected: $obtainPathResult")
        }

    }

    fun edittext() {
        //初始化编辑高度
        textedit.setEditorHeight(200)
        //初始化字体大小
        textedit.setEditorFontSize(22)//初始化字体颜色
        textedit.setEditorFontColor(Color.BLACK)
        textedit.setEditorBackgroundColor(Color.BLUE)
        //初始化内边距
        textedit.setPadding(10, 10, 10, 10) //设置编辑框背景，可以是网络图片 //
        //  textedit.setBackground(R.color.white) //
        textedit.setBackgroundColor(Color.WHITE)
        // mEditor.setBackgroundResource(R.drawable.bg)
        textedit.setPlaceholder("请输入内容")  //设置默认显示语句
        textedit.setInputEnabled(true);//设置编辑器是否可用
    }

    private fun setdatadialog(name: String, text: TextView) {
        alert {
            title = name
            val editText = EditText(this@AddActivity)
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

    val list = ArrayList<Int>()
    fun getimagstops(a: Int) {
        if (a != -1) {
            list.add(a)
            getimagstops(textedit.html.indexOf("src", a + 3))
        } else {
            return
        }
    }

}

