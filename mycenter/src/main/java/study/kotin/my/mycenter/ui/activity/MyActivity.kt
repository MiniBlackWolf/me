package study.kotin.my.mycenter.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.SimpleAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.my_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.injection.commponent.DaggerMyCommponent
import study.kotin.my.mycenter.injection.module.Mymodule
import study.kotin.my.mycenter.persenter.Mypersenter
import study.kotin.my.mycenter.persenter.view.MyView
import javax.inject.Inject
import jsc.kit.datetimepicker.widget.DateTimePicker
import java.text.SimpleDateFormat
import java.util.*


class MyActivity : BaseMVPActivity<Mypersenter>(), MyView, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.z1 -> {
                setdatadialog("修改个性签名", z1_1)
            }
            R.id.z2 -> {
                setdatadialog("修改昵称", z2_1)
            }
            R.id.z3 -> {
                setdatadialog("修改性别", z3_1)
            }
            R.id.z4 -> {
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
                        .setShowType(DateTimePicker.ShowType.DAY)
                DateTimePicker(this, object : DateTimePicker.ResultHandler {
                    override fun handle(date: Date) {
                        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
                        val format = simpleDateFormat.format(date)
                        z4_1.text = format
                        Log.i("iiiiiiiii", date.toString())
                    }
                }, startDate, Calendar.getInstance().time, TBuilder).show(Calendar.getInstance().time)

            }
            R.id.z5 -> {
                setdatadialog("修改职业", z5_1)
            }
            R.id.z6 -> {
                setdatadialog("修改学校", z6_1)
            }
            R.id.z7 -> {
                setdatadialog("修改邮箱", z7_1)
            }
            R.id.z8 -> {
                setdatadialog("修改个人标签", z8_1)
            }

        }

    }

    private fun setdatadialog(name: String, text: TextView) {
        alert {
            title = name
            val editText = EditText(this@MyActivity)
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

    override fun reslut() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_main)
        initinject()
        mpersenter.mView = this
        mpersenter.s()
        z1.setOnClickListener(this)
        z2.setOnClickListener(this)
        z3.setOnClickListener(this)
        z4.setOnClickListener(this)
        z5.setOnClickListener(this)
        z6.setOnClickListener(this)
        z7.setOnClickListener(this)
        z8.setOnClickListener(this)

    }

    fun initinject() {
        DaggerMyCommponent.builder().activityCommpoent(activityCommpoent).mymodule(Mymodule()).build().inject(this)

    }

}