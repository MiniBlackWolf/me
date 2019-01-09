package com.example.home.HomeAdapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.madengdata

class madengviewAdapter(val context:Context,data: ArrayList<madengdata>) : BaseQuickAdapter<madengdata, BaseViewHolder>(R.layout.madengviewitem, data) {
    override fun convert(helper: BaseViewHolder?, item: madengdata?) {
        if(helper==null||item==null)return
        helper.setText(R.id.time, item.createtime.substring(0,item.createtime.indexOf("T")))
        helper.setText(R.id.title,item.title)
        helper.setText(R.id.content,item.content)
        val bg = helper.getView<ImageView>(R.id.bg)
        val options = RequestOptions()
                .error(R.drawable.mdhelperbg)
        Glide.with(context).load(item.imgurl).apply(options).into(bg)
      //  bg.setImageResource(R.drawable.a1)

    }
}