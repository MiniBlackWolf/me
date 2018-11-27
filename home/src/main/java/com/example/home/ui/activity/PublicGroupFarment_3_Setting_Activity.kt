package com.example.home.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.home.R
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.publicgroupfarment_3_setting.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMConversationType
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.group.TIMGroupSelfInfo
import com.tencent.imsdk.ext.message.TIMConversationExt
import org.jetbrains.anko.startActivity
import com.tencent.imsdk.TIMGroupReceiveMessageOpt
import study.kotin.my.baselibrary.ext.getuserstting
import study.kotin.my.baselibrary.ext.setuserstting


class PublicGroupFarment_3_Setting_Activity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    val id by lazy { intent.extras?.getString("id") }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.publicgroupcat -> {
                alert {
                    title = "修改我的群名片"
                    val editText = EditText(this@PublicGroupFarment_3_Setting_Activity)
                    customView = editText
                    positiveButton("确定") {
                        if (editText.text.toString() == "") {
                            toast("不能为空")
                            return@positiveButton
                        }
                        if (editText.text.toString().length > 15) {
                            toast("不能大于15个字符")
                            return@positiveButton
                        }
                        val param = TIMGroupManagerExt.ModifyMemberInfoParam(id!!, TIMManager.getInstance().loginUser)
                        param.nameCard = editText.text.toString()
                        TIMGroupManagerExt.getInstance().modifyMemberInfo(param, object : TIMCallBack {
                            override fun onError(code: Int, desc: String) {
                                Log.e("eeeeeeeeeeee", "modifyMemberInfo failed, code:$code|msg: $desc")
                                toast("修改失败$desc")
                            }

                            override fun onSuccess() {
                                toast("修改成功")
                                publicgroupcattext.text = editText.text.toString()
                            }
                        })
                        it.dismiss()
                    }
                    negativeButton("取消") {
                        it.dismiss()
                    }
                    show()
                }

            }
            R.id.s2 -> {
                startActivity<ChatRecordActivity>("id" to id)
            }
            R.id.cleanmsg -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("是否清空消息记录?")
                builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val con = TIMManager.getInstance().getConversation(TIMConversationType.Group, id)
                        TIMConversationExt(con).deleteLocalMessage(object : TIMCallBack {
                            override fun onSuccess() {
                                toast("清空消息成功!")
                            }

                            override fun onError(p0: Int, p1: String?) {
                            }
                        })
                        dialog!!.dismiss()
                    }
                })
                builder.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                    }
                })
                builder.show()
            }
            R.id.stopck -> {
                val param = TIMGroupManagerExt.ModifyMemberInfoParam(id!!, TIMManager.getInstance().loginUser)
                if (stopck.isChecked) {
                    param.setReceiveMessageOpt(TIMGroupReceiveMessageOpt.NotReceive)
                } else {
                    param.setReceiveMessageOpt(TIMGroupReceiveMessageOpt.ReceiveAndNotify)
                }
                TIMGroupManagerExt.getInstance().modifyMemberInfo(param, object : TIMCallBack {
                    override fun onError(code: Int, desc: String) {
                        Log.e("eeeeeeeee", "modifyMemberInfo failed, code:$code|msg: $desc")
                    }

                    override fun onSuccess() {

                    }
                })

            }
            R.id.a1 -> {
                if(a1.isChecked){
                    setuserstting("disturb",true)
                }else{
                    setuserstting("disturb",false)
                }

            }
            R.id.a2 -> {
                if(a1.isChecked){
                    setuserstting("stick",true)
                }else{
                    setuserstting("stick",false)
                }
            }
            R.id.a3 -> {
                if(a3.isChecked){
                    setuserstting("showcard",true)
                }else{
                    setuserstting("showcard",false)
                }
            }
            R.id.ctf -> {
                finish()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroupfarment_3_setting)
        publicgroupcat.setOnClickListener(this)
        ctf.setOnClickListener(this)
        s2.setOnClickListener(this)
        cleanmsg.setOnClickListener(this)
        stopck.setOnClickListener(this)
        a1.setOnClickListener(this)
        a2.setOnClickListener(this)
        a3.setOnClickListener(this)
        a1.isChecked = getuserstting("disturb")
        a2.isChecked = getuserstting("stick")
        a3.isChecked = getuserstting("showcard")
        TIMGroupManagerExt.getInstance().getSelfInfo(id!!, object : TIMValueCallBack<TIMGroupSelfInfo> {
            override fun onSuccess(p0: TIMGroupSelfInfo?) {
                val s = p0!!.recvOpt
                stopck.isChecked = s == TIMGroupReceiveMessageOpt.NotReceive
                publicgroupcattext.text = p0.nameCard
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
    }
}