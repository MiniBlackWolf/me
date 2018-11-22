package com.example.home.HomeAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.ChatFiledata
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import java.math.BigDecimal

class ChatFileAdapter(data:List<ChatFiledata>):BaseQuickAdapter<ChatFiledata,BaseViewHolder>(R.layout.chatfileitem,data) {
    override fun convert(helper: BaseViewHolder?, item: ChatFiledata?) {
        if(item?.fileimg!=null){
            helper!!.setImageBitmap(R.id.fileimg,item.fileimg)
        }
        helper!!.setText(R.id.filename,item!!.filename)
        helper.setText(R.id.filesize,(item.filesize.toBigDecimal().divide(1024000.toBigDecimal())).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"mb")
        TIMFriendshipManagerExt.getInstance().getFriendsProfile(arrayListOf(item.filesource),object : TIMValueCallBack<MutableList<TIMUserProfile>>{
            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                helper.setText(R.id.filesource,"来自于用户：${p0!![0].nickName}")
            }

            override fun onError(p0: Int, p1: String?) {

            }
        })

    }
}