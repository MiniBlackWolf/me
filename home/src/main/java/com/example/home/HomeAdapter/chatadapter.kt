package com.example.home.HomeAdapter

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.common.Msg
import kotlinx.android.synthetic.main.chatitem2.*
import org.jetbrains.anko.find

class chatadapter(data: ArrayList<Msg>?, private val context: Context) : BaseMultiItemQuickAdapter<Msg, BaseViewHolder>(data) {
    lateinit var showimgmsgs: ImageView
    lateinit var chatmsg2: TextView
    lateinit var palyer: Button
    lateinit var chatmsgs1: TextView
    lateinit var chatmsgs2: ImageView
    lateinit var chatmsgs3: Button

    init {
        addItemType(Msg.TYPE_RECEIVED, R.layout.chatitem2)
        addItemType(Msg.TYPE_SENT, R.layout.chatitem)
    }

    override fun convert(helper: BaseViewHolder, item: Msg) {
        when (helper.itemViewType) {
            Msg.TYPE_RECEIVED -> {
                when (item.datatype) {
                    1 -> {
                        initview(helper)
                        chatmsg2.isVisible = true
                        helper.setText(R.id.chatmsg2, item.content as String)
                    }
                    2 -> {
                        helper.setImageBitmap(R.id.showimgmsgs, item.content as Bitmap)
                    }
                    3 -> {
                    }

                }


            }
            Msg.TYPE_SENT -> {
                when (item.datatype) {
                    1 -> {
                        initview2(helper)
                        chatmsgs1.isVisible = true
                        helper.setText(R.id.chatname, "gg")
                        helper.setText(R.id.chatmsgs1, item.content as String)

                    }
                    2 -> {
                        initview2(helper)
                        chatmsgs2.isVisible = true
                        helper.setImageBitmap(R.id.chatmsgs2, item.content as Bitmap)
                    }
                    3 -> {
                    }

                }

            }
        }

    }

    fun initview(helper: BaseViewHolder) {
        showimgmsgs = helper.getView(R.id.showimgmsgs)
        showimgmsgs.isVisible = false
        chatmsg2 = helper.getView(R.id.chatmsg2)
        chatmsg2.isVisible = false
        palyer = helper.getView(R.id.palyer)
        palyer.isVisible = false

    }
    fun initview2(helper: BaseViewHolder){
        chatmsgs1 = helper.getView(R.id.chatmsgs1)
        chatmsgs1.isVisible = false
        chatmsgs2 = helper.getView(R.id.chatmsgs2)
        chatmsgs2.isVisible = false
        chatmsgs3 = helper.getView(R.id.chatmsgs3)
        chatmsgs3.isVisible = false

    }
}
