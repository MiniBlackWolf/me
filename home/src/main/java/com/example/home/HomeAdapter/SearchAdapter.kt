package com.example.home.HomeAdapter

import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.data.seachAlldata
import com.example.home.data.searchuserdata
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.common.CircleImageView

class SearchAdapter(data: ArrayList<seachAlldata>?) : BaseQuickAdapter<seachAlldata, BaseViewHolder>(R.layout.searchlayoutitem, data) {
    override fun convert(helper: BaseViewHolder, item: seachAlldata) {
        when (item.type) {
            1 -> {
                helper.setText(R.id.upname,item.name)
                var selfSignature = item.Selfsignature
                if (selfSignature == "") {
                    selfSignature = "这个人很懒没有自我介绍哦"
                }
                helper.setText(R.id.downname,selfSignature)
                helper.setVisible(R.id.rz,false)
                helper.setVisible(R.id.sex,true)
                if (item.sex == "Gender_Type_Male") {
                    helper.setBackgroundRes(R.id.sex, R.drawable.men)

                } else if (item.sex== "Gender_Type_Female") {
                    helper.setBackgroundRes(R.id.sex, R.drawable.women)
                } else {
                    helper.setBackgroundRes(R.id.sex, R.drawable.unkownsex)
                }
                val head = helper.getView<CircleImageView>(R.id.head)
                val options = RequestOptions()
                        .error(R.drawable.a4_2)
                Glide.with(BaseApplication.context)
                        .load(item.faceurl)
                        .apply(options)
                        .into(head)
            }
            2 -> {
                helper.setVisible(R.id.rz,true)
                helper.setVisible(R.id.sex,false)
                if(item.authentication=="true"){
                    helper.setBackgroundRes(R.id.rz,R.drawable.buttonssss)
                    helper.setText(R.id.rz,"认证")
                }else{
                    helper.setBackgroundRes(R.id.rz,R.drawable.nobuttonssss)
                    helper.setText(R.id.rz,"未认证")
                }
                helper.setText(R.id.upname,item.name)
                val head = helper.getView<CircleImageView>(R.id.head)
                val options = RequestOptions()
                        .error(R.drawable.qchat)
                Glide.with(BaseApplication.context)
                        .load(item.faceurl)
                        .apply(options)
                        .into(head)
                var selfsignature = item.Selfsignature
                if(selfsignature==""){
                    selfsignature="这个社团还没有简介哦"
                }
                helper.setText(R.id.downname,selfsignature)
            }
        }
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