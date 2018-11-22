package com.example.home.ui.activity

import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.os.Process
import android.support.v7.widget.LinearLayoutManager
import android.widget.CheckBox
import android.widget.PopupWindow
import android.widget.TextView
import com.example.home.HomeAdapter.ChatFileAdapter
import com.example.home.R
import com.example.home.data.ChatFiledata
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.chatfilelayout.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import java.io.File

class ChatFileActivity : BaseMVPActivity<HomePersenter>() {
    var AllfileList = ArrayList<File>()
    var chatFiledata = ArrayList<ChatFiledata>()
    val id by lazy { intent.extras?.getString("id") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatfilelayout)
        cf.setOnClickListener {
            finish()
        }
        val chatFileAdapter = updataview()
        Choice.setOnClickListener { it ->
            val popWindow = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.chatfileactivitydialog, null)
            popWindow.contentView = view//显示的布局，还可以通过设置一个View // .size(600,400) //设置显示的大小，不设置就默认包裹内容
            popWindow.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.touming)))
            popWindow.setFocusable(true)//是否获取焦点，默认为ture
            popWindow.setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
            popWindow.showAsDropDown(Choice, 0, 10)//显示PopupWindow
            val del = view.find<TextView>(R.id.del)
            val delall = view.find<TextView>(R.id.delall)
            del.setOnClickListener {
                var count = 0
                for (i in 0 until chatFileAdapter.itemCount) {
                    val check = chatFileAdapter.getViewByPosition(Filerc, i, R.id.check) as CheckBox
                    if (check.isChecked) {
                        AllfileList.get(i).deleteRecursively()
                        count++
                    }
                }
                if (count == 0) {
                    toast("请选择一个项目")
                }
                chatFileAdapter.data.clear()
                Filerc.removeAllViewsInLayout()
                updataview()
                toast("删除成功")
                popWindow.dismiss()
            }
            delall.setOnClickListener {
                for (s in 0 until chatFileAdapter.itemCount) {
                    AllfileList[s].deleteRecursively()
                }
                chatFileAdapter.data.clear()
                Filerc.removeAllViewsInLayout()
                updataview()
                toast("删除成功")
                popWindow.dismiss()
            }

        }
    }

    private fun updataview(): ChatFileAdapter {
        AllfileList.clear()
        val Allpath = Environment.getExternalStorageDirectory().canonicalPath
        val path = File("$Allpath/madeng/")
        val path2 = File("$Allpath/madengFolder/")
        val listFiles = path.listFiles()
        AllfileList.addAll(listFiles)
        val listFiles2 = path2.listFiles()
        AllfileList.addAll(listFiles2)
        for (list in AllfileList) {
            val filetype = list.name.substring(list.name.lastIndexOf(".") + 1, list.name.length)
            if (filetype == "jpg" || filetype == "png" || filetype == "gif" || filetype == "jpeg" || filetype == "bmp") {
                val decodeFile = BitmapFactory.decodeFile(list.canonicalPath)
                val chatdata = ChatFiledata(decodeFile, list.name, list.length(), id!!)
                chatFiledata.add(chatdata)
            } else {
                val chatdata = ChatFiledata(null, list.name, list.length(), id!!)
                chatFiledata.add(chatdata)
            }
        }
        val chatFileAdapter = ChatFileAdapter(chatFiledata)
        Filerc.adapter = chatFileAdapter
        Filerc.layoutManager = LinearLayoutManager(this)
        return chatFileAdapter
    }

}