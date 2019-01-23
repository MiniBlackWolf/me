package com.example.home.HomeAdapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.madengdata
import kotlinx.android.synthetic.main.publicgroupfarment_3_activity.*

class madengviewAdapter(val context: Context, data: ArrayList<madengdata>) : BaseQuickAdapter<madengdata, BaseViewHolder>(R.layout.madengviewitem, data) {
    var content=""
    override fun convert(helper: BaseViewHolder?, item: madengdata?) {
        if (helper == null || item == null) return
        helper.setText(R.id.time, item.createtime.substring(0, item.createtime.indexOf("T")))
        helper.setText(R.id.title, item.title)
        val bg = helper.getView<ImageView>(R.id.bg)
        val options = RequestOptions()
                .error(R.drawable.mdhelperbg)
        Glide.with(context).load(item.imgurl).apply(options).into(bg)
        //  bg.setImageResource(R.drawable.a1)
        helper.addOnClickListener(R.id.cardView)
        content=item.content
        val indexOf = content.indexOf("<img", 0)
        getimagstops(indexOf)
         content = content.replace("<br>", "")
        helper.setText(R.id.content, content)
    }
    var trun=true
    fun getimagstops(a: Int) {
        if (a != -1) {
            if(trun){
                val indexOf = content.indexOf("/>", a + 2)
                if(indexOf==-1)return
                val substring = content.substring(a, indexOf+2)
                content=content.replace(substring,"(图片)")
                trun=false
                getimagstops(indexOf)
            }else{
                val indexOf = content.indexOf("<img", a + 4)
                if(indexOf==-1)return
                trun=true
                getimagstops(indexOf)
            }
        } else {
            return
        }
    }
}