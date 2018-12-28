package study.kotin.my.address.adapter

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tencent.imsdk.TIMGroupMemberInfo
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import study.kotin.my.address.R

class PublicGroupAdapter(val context:Context,data:MutableList<TIMGroupBaseInfo>):BaseQuickAdapter<TIMGroupBaseInfo,BaseViewHolder>(R.layout.publicgroupitem,data) {
    override fun convert(helper: BaseViewHolder?, item: TIMGroupBaseInfo?) {
        helper!!.setText(R.id.publicgroupname,item!!.groupName)
        val publicgrouprz = helper.getView<Button>(R.id.publicgrouprz)
        val publicgrouphead=helper.getView<ImageView>(R.id.publicgrouphead)
        Glide.with(context).load(item.faceUrl).apply(RequestOptions().error(R.drawable.qchat)).into(publicgrouphead)
        TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(item.groupId),object: TIMValueCallBack<MutableList<TIMGroupDetailInfo>>{
            override fun onError(p0: Int, p1: String?) {

            }

            override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                if(p0==null)return
                helper.setText(R.id.publicgroupmb,"成员人数(${p0[0].memberNum}/${p0[0].maxMemberNum})")
                val custom = p0[0].custom
                if(custom["Authentication"]!=null){
                    if(String(custom["Authentication"]!!)=="true"){
                        publicgrouprz.isVisible=true
                        publicgrouprz.setBackgroundResource(R.drawable.buttonssss)
                        publicgrouprz.text = "认证"
                    }else{
                        publicgrouprz.isVisible=true
                        publicgrouprz.setBackgroundResource(R.drawable.nobuttonssss)
                        publicgrouprz.text = "未认证"
                    }
                }else{
                    publicgrouprz.isVisible=false
                }
            }
        })
    }
}