package com.example.home.HomeAdapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.UserList
import com.example.home.ui.activity.HomeActivity
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*


class HomeListAdapter(val context: Context, userList: List<UserList>) : BaseQuickAdapter<UserList, BaseViewHolder>(R.layout.homechat, userList) {
    override fun convert(helper: BaseViewHolder?, item: UserList?) {
      //  helper!!.setText(R.id.peername, item!!.Name)
        helper!!.setText(R.id.lastmsg, item!!.msg)
        val format:String
        format = if(item.lastmsgtime!=""){
            val simpleDateFormat = SimpleDateFormat("MM月dd日 HH:mm")
            simpleDateFormat.format(Date(item.lastmsgtime.toLong()*1000))
        }else{
            ""
        }

        helper.setText(R.id.lastmsgtime, format)
        val noreadmsg = helper.getView<TextView>(R.id.noreadmsg)
        if (item.noreadmsg != 0) {
            noreadmsg.isVisible = true
            helper.setText(R.id.noreadmsg, item.noreadmsg.toString())

        } else {
            noreadmsg.isVisible = false
        }
        helper.addOnClickListener(R.id.btnDelete)
        helper.getView<ConstraintLayout>(R.id.rt).setOnClickListener {
            context.startActivity<HomeActivity>("id" to item.Name)

        }
        val name:String?
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        if (item.Name.substring(0, 5) == "@TGS#") {
            name=sharedPreferences.getString("${item.Name}Gname","")
        } else {
            name=sharedPreferences.getString("${item.Name}fdname","")
        }
        helper.setText(R.id.peername,name)
    }

}