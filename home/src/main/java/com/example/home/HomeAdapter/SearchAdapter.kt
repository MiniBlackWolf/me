package com.example.home.HomeAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.searchdata
import com.example.home.data.searchuserdata
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo

class SearchAdapter(data: List<searchuserdata.ResultsBean>?) : BaseQuickAdapter<searchuserdata.ResultsBean, BaseViewHolder>(R.layout.searchlayoutitem, data) {
    override fun convert(helper: BaseViewHolder?, item: searchuserdata.ResultsBean?) {

    }


//    override fun convert(helper: BaseViewHolder?, item: searchuserdata?) {
//        when (item!!.type) {
//            "U" -> {
//                helper!!.setText(R.id.upname, (item.data as TIMUserProfile).nickName)
//                var selfSignature = item.data.selfSignature
//                if (selfSignature == "") {
//                    selfSignature = "这个人很懒没有自我介绍哦"
//                }
//                helper.setText(R.id.downname, selfSignature)
//                if (item.data.gender.value == 1L) {
//                    helper.setImageResource(R.id.sex, R.drawable.men)
//
//                } else if (item.data.gender.value == 2L) {
//                    helper.setImageResource(R.id.sex, R.drawable.women)
//                } else {
//                    helper.setImageResource(R.id.sex, R.drawable.unkownsex)
//                }
//            }
//            "G" -> {
//                helper!!.setText(R.id.upname, (item.data as TIMGroupDetailInfo).groupName)
//                var groupNotification = item.data.groupNotification
//                if (groupNotification == "") {
//                    groupNotification = "这个社团还没有简介哦"
//                }
//                helper.setText(R.id.downname, item.data.groupNotification)
//                helper.setImageResource(R.id.sex, R.drawable.group)
//            }
//        }
//
//    }
}