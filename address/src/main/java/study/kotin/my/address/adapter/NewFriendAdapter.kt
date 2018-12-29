package study.kotin.my.address.adapter

import android.widget.ImageView
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tencent.imsdk.TIMFriendGenderType
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupAssistant
import com.tencent.imsdk.ext.sns.TIMFriendFutureItem
import study.kotin.my.address.R
import study.kotin.my.address.data.addnewFDdata
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.common.CircleImageView

class NewFriendAdapter(data: List<addnewFDdata>) : BaseQuickAdapter<addnewFDdata, BaseViewHolder>(R.layout.newfrienditem, data) {
    override fun convert(helper: BaseViewHolder?, item: addnewFDdata?) {
        if (item == null || helper == null) return
        helper.addOnClickListener(R.id.acc)
        helper.addOnClickListener(R.id.refuse)
        helper.addOnClickListener(R.id.headurl)
        helper.addOnClickListener(R.id.msg)
        helper.setText(R.id.or,"请求加为好友")
        helper.setTag(R.id.msg,false)
        TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf(item.id),object: TIMValueCallBack<MutableList<TIMUserProfile>>{
            override fun onSuccess(p0: MutableList<TIMUserProfile>) {
                helper.setText(R.id.name, p0[0].nickName)
                when (p0[0].gender) {
                    TIMFriendGenderType.Female -> {
                        helper.setImageResource(R.id.sex,R.drawable.women)
                    }
                    TIMFriendGenderType.Male -> {
                        helper.setImageResource(R.id.sex,R.drawable.men)
                    }
                    else -> {
                        helper.setImageResource(R.id.sex,R.drawable.unkownsex)
                    }
                }
                val head = helper.getView<CircleImageView>(R.id.headurl)
                val options = RequestOptions()
                        .error(R.drawable.a4_2)
                Glide.with(BaseApplication.context)
                        .load(p0[0].faceUrl)
                        .apply(options)
                        .into(head)
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })

        when (item.type) {
            0 -> {
                helper.setText(R.id.name, item.name)
                helper.setText(R.id.msg, item.introduce)
                when (item.sex) {
                    TIMFriendGenderType.Female -> {
                        helper.setImageResource(R.id.sex,R.drawable.women)
                    }
                    TIMFriendGenderType.Male -> {
                        helper.setImageResource(R.id.sex,R.drawable.men)
                    }
                    else -> {
                        helper.setImageResource(R.id.sex,R.drawable.unkownsex)
                    }
                }
                val millis2String = TimeUtils.millis2String(item.addtime)
                helper.setText(R.id.time,millis2String)
            }
            1 -> {
                helper.setText(R.id.msg, item.introduce)
                val groups = TIMGroupAssistant.getInstance().getGroups(arrayListOf(item.groupid))
                if(groups!=null){
                    val groupName = groups[0].groupInfo.groupName
                    helper.setText(R.id.or,"请求加入社团$groupName")
                }else{
                    helper.setText(R.id.or,"请求加入社团${item.groupid}")
                }
                val millis2String = TimeUtils.millis2String(item.addtime*1000)
                helper.setText(R.id.time,millis2String)
            }
        }
    }
}