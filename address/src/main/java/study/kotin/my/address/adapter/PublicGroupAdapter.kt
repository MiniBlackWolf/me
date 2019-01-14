package study.kotin.my.address.adapter

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
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
        val UserCache = context.getSharedPreferences("UserCache", Context.MODE_PRIVATE).getString("${item.groupId}GroupInfo","")
        if(UserCache!="") {
            val fromJson = Gson().fromJson(UserCache, Map::class.java)
            helper.setText(R.id.publicgroupmb, "成员人数(${fromJson["memberNum"]}/${fromJson["maxMemberNum"]})")
            when (fromJson["Authentication"]) {
                "1" -> {
                    publicgrouprz.isVisible = true
                    publicgrouprz.setBackgroundResource(R.drawable.buttonssss)
                    publicgrouprz.text = "认证"
                }
                "2" -> {
                    publicgrouprz.isVisible = true
                    publicgrouprz.setBackgroundResource(R.drawable.nobuttonssss)
                    publicgrouprz.text = "未认证"
                }
                "3" -> {
                    publicgrouprz.isVisible = false
                }
            }
        }
        else{
            downlaodcach(item, helper, publicgrouprz)
        }
    }

    private fun downlaodcach(item: TIMGroupBaseInfo, helper: BaseViewHolder, publicgrouprz: Button) {
        TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(item.groupId), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
            override fun onError(p0: Int, p1: String?) {

            }

            override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                if (p0 == null) return
                val hashMap = HashMap<String, String>()
                val UserCache = context.getSharedPreferences("UserCache", Context.MODE_PRIVATE).edit()
                helper.setText(R.id.publicgroupmb, "成员人数(${p0[0].memberNum}/${p0[0].maxMemberNum})")
                val custom = p0[0].custom
                if (custom["Authentication"] != null) {
                    if (String(custom["Authentication"]!!) == "true") {
                        publicgrouprz.isVisible = true
                        publicgrouprz.setBackgroundResource(R.drawable.buttonssss)
                        publicgrouprz.text = "认证"
                        hashMap.put("Authentication", "1")
                    } else {
                        publicgrouprz.isVisible = true
                        publicgrouprz.setBackgroundResource(R.drawable.nobuttonssss)
                        publicgrouprz.text = "未认证"
                        hashMap.put("Authentication", "2")
                    }
                } else {
                    hashMap.put("Authentication", "3")
                    publicgrouprz.isVisible = false
                }
                hashMap.put("memberNum", p0[0].memberNum.toString())
                hashMap.put("maxMemberNum", p0[0].maxMemberNum.toString())
                hashMap.put("name", item.groupName)
                hashMap.put("faceimg", item.faceUrl)
                UserCache.putString("${item.groupId}GroupInfo", Gson().toJson(hashMap))
                UserCache.apply()
            }
        })
    }
}