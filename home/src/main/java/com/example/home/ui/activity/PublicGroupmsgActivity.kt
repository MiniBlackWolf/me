package com.example.home.ui.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.view.isVisible
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.example.home.ui.Frament.PublicGroupFarment_1
import com.example.home.ui.Frament.PublicGroupFarment_2
import com.example.home.ui.Frament.PublicGroupFarment_3
import com.example.home.ui.Frament.PublicGroupFarment_4
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.group.TIMGroupSelfInfo
import com.tencent.imsdk.ext.message.TIMManagerExt
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import kotlinx.android.synthetic.main.chatlayout.*
import kotlinx.android.synthetic.main.publicgrouplayout.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class PublicGroupmsgActivity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    lateinit var m1: TextView
    lateinit var m2: TextView
    lateinit var m3: TextView
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.l1 -> {
                guset.isVisible = false
                initview()
                l1.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l1.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_1)
                beginTransaction.commit()
            }
            R.id.l2 -> {
                guset.isVisible = false
                initview()
                l2.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l2.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_2)
                beginTransaction.commit()
            }
            R.id.l3 -> {
                initview()
                l3.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l3.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_3)
                beginTransaction.commit()
            }
            R.id.l4 -> {
                initview()
                l4.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l4.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_4)
                beginTransaction.commit()
            }
            R.id.more -> {
                val popWindow = PopupWindow(this)
                val view = layoutInflater.inflate(R.layout.moreitem, null)
                m1 = view.find<TextView>(R.id.m1)
                m2 = view.find<TextView>(R.id.m2)
                m3 = view.find<TextView>(R.id.m3)
                when (membertype) {
                    TIMGroupMemberRoleType.Admin, TIMGroupMemberRoleType.Owner -> {
                        m1.setOnClickListener {
                            startActivity<AddActivity>("id" to id)
                        }
                        m2.setOnClickListener {
                            startActivity<PublicGroupFarment_3_Article_Activity>("id" to id)
                        }
                    }
                    else -> {
                        m1.setOnClickListener {
                            toast("管理员和社长才可以发布")
                        }
                        m2.setOnClickListener {
                            toast("管理员和社长才可以发布")
                        }
                    }

                }
                m3.setOnClickListener {
                    startActivity<PublicGroupFarment_3_Setting_Activity>("id" to id)
                }
                popWindow.contentView = view//显示的布局，还可以通过设置一个View // .size(600,400) //设置显示的大小，不设置就默认包裹内容
                popWindow.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.touming)))
                popWindow.setFocusable(true)//是否获取焦点，默认为ture
                popWindow.setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                popWindow.showAsDropDown(more, 0, 5)//显示PopupWindow
            }
            R.id.addjoin -> {
                when {
                    addjoin.text.toString() == "申请加入" -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("申请理由")
                        val editText = EditText(this)
                        builder.setView(editText)
                        builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                if (editText.text.toString() == "") {
                                    toast("申请理由不能是空！")
                                    return
                                } else {
                                    TIMGroupManager.getInstance().applyJoinGroup(id!!, editText.text.toString(), object : TIMCallBack {
                                        override fun onSuccess() {
                                            toast("申请成功！")
                                        }

                                        override fun onError(p0: Int, p1: String?) {
                                            Log.e("eeeeeeee", p1)
                                            toast("申请失败！$p1")
                                        }
                                    })

                                }
                                dialog.dismiss()
                            }
                        })
                        builder.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                dialog.dismiss()
                            }
                        })
                        builder.show()
                    }
                    addjoin.text.toString() == "解散该群" -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("真的要解散吗？")
                        builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                TIMGroupManager.getInstance().deleteGroup(id!!, object : TIMCallBack {
                                    override fun onSuccess() {
                                        toast("解散成功！")
                                        addjoin.text = "申请加入"
                                        ARouter.getInstance().build("/App/Homepage").navigation()
                                        TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.Group, id)
                                        ActivityUtils.finishActivity(HomeActivity::class.java)
                                        finish()

                                    }

                                    override fun onError(p0: Int, p1: String?) {
                                        toast("解散失败！错误$p1")
                                    }
                                })
                                dialog.dismiss()
                            }
                        })
                        builder.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                dialog.dismiss()
                            }
                        })
                        builder.show()


                    }
                    else -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("真的要退出吗？")
                        builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                TIMGroupManager.getInstance().quitGroup(id!!, object : TIMCallBack {
                                    override fun onSuccess() {
                                        toast("退出成功！")
                                        addjoin.text = "申请加入"
                                        TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.Group, id)
                                        ActivityUtils.finishActivity(HomeActivity::class.java)
                                        finish()
                                    }

                                    override fun onError(p0: Int, p1: String?) {
                                        toast("退出失败！错误$p1")
                                    }
                                })
                                dialog.dismiss()
                            }
                        })
                        builder.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                dialog.dismiss()
                            }
                        })
                        builder.show()


                    }
                }

            }
            R.id.chf -> {
                finish()
            }

        }

    }

    val publicGroupFarment_1 by lazy { PublicGroupFarment_1() }
    val publicGroupFarment_2 by lazy { PublicGroupFarment_2() }
    val publicGroupFarment_3 by lazy { PublicGroupFarment_3() }
    val publicGroupFarment_4 by lazy { PublicGroupFarment_4() }
    val id by lazy { intent.extras?.getString("id") }
    var membertype = TIMGroupMemberRoleType.NotMember
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgrouplayout)
        initFraments()
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.show(publicGroupFarment_1)
        beginTransaction.commit()
        l1.setOnClickListener(this)
        l2.setOnClickListener(this)
        l3.setOnClickListener(this)
        l4.setOnClickListener(this)
        addjoin.setOnClickListener(this)
        chf.setOnClickListener(this)
        more.setOnClickListener(this)
        publicgroupid.text = "社团号 :$id"
        TIMGroupManagerExt.getInstance().getGroupPublicInfo(arrayListOf(id), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
            override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                if (p0 == null) return
                publicname.text = p0[0].groupName
                if (p0[0].groupIntroduction == "") {
                    ins.text = "这个社团还没有简介哦！"
                } else {
                    ins.text = p0[0].groupIntroduction
                }
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
        TIMGroupManagerExt.getInstance().getSelfInfo(id!!, object : TIMValueCallBack<TIMGroupSelfInfo> {
            override fun onSuccess(p0: TIMGroupSelfInfo?) {
                if (p0 == null) return
                if (p0.role == TIMGroupMemberRoleType.Normal) {
                    addjoin.text = "退出该群"
                    more.isVisible = true
                    membertype = TIMGroupMemberRoleType.Normal
                    return
                }
                if (p0.role == TIMGroupMemberRoleType.Admin) {
                    addjoin.text = "退出该群"
                    more.isVisible = true
                    membertype = TIMGroupMemberRoleType.Admin
                    return
                }
                if (p0.role == TIMGroupMemberRoleType.Owner) {
                    addjoin.text = "解散该群"
                    more.isVisible = true
                    membertype = TIMGroupMemberRoleType.Admin
                    return
                }
                if (p0.role == TIMGroupMemberRoleType.NotMember) {
                    more.isVisible = false
                    addjoin.text = "申请加入"
                    membertype = TIMGroupMemberRoleType.NotMember
                    l3.setOnClickListener {
                        guset.isVisible = true
                    }
                    l4.setOnClickListener {

                        guset.isVisible = true
                    }
                    return
                }

            }

            override fun onError(p0: Int, p1: String?) {
            }
        })

    }

    private fun initFraments() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frament, publicGroupFarment_1)
        beginTransaction.add(R.id.frament, publicGroupFarment_2)
        beginTransaction.add(R.id.frament, publicGroupFarment_3)
        beginTransaction.add(R.id.frament, publicGroupFarment_4)
        hideAll(beginTransaction)
        beginTransaction.commit()
    }

    private fun hideAll(beginTransaction: FragmentTransaction) {
        beginTransaction.hide(publicGroupFarment_1)
        beginTransaction.hide(publicGroupFarment_2)
        beginTransaction.hide(publicGroupFarment_3)
        beginTransaction.hide(publicGroupFarment_4)
    }

    private fun initview() {
        l1.setTextColor(ContextCompat.getColor(this, R.color.black))
        l1.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
        l2.setTextColor(ContextCompat.getColor(this, R.color.black))
        l2.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
        l3.setTextColor(ContextCompat.getColor(this, R.color.black))
        l3.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
        l4.setTextColor(ContextCompat.getColor(this, R.color.black))
        l4.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
    }

}