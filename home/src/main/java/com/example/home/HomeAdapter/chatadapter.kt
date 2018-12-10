package com.example.home.HomeAdapter

import android.app.Activity
import android.app.Dialog
import android.graphics.Bitmap
import android.support.constraint.ConstraintLayout
import android.widget.*
import androidx.core.view.isVisible

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.home.R
import com.example.home.common.Msg
import com.example.home.data.Sounddata
import com.tencent.imsdk.*
import study.kotin.my.baselibrary.common.CircleImageView
import java.math.BigDecimal

class chatadapter(data: ArrayList<Msg>?, private val context: Activity) : BaseMultiItemQuickAdapter<Msg, BaseViewHolder>(data) {
    lateinit var showimgmsgs: ImageView
    lateinit var chatmsg2: TextView
    lateinit var chatpaly: Button
    lateinit var chatmsgs1: TextView
    lateinit var chatmsgs2: ImageView
    lateinit var chatmsgs3: Button
    lateinit var chatbg: LinearLayout
    lateinit var dialog: Dialog
    lateinit var soundanm: ImageView
    lateinit var chatpaly2: ImageView
    lateinit var filepackage: ConstraintLayout
    lateinit var filaename: TextView
    lateinit var filesize: TextView
    lateinit var ches: CircleImageView
    lateinit var grouptip: TextView
    lateinit var dd:ImageView

    init {
        addItemType(Msg.TYPE_RECEIVED, R.layout.chatitem2)
        addItemType(Msg.TYPE_SENT, R.layout.chatitem)
    }

    override fun convert(helper: BaseViewHolder, item: Msg) {
        ches = helper.getView(R.id.ches)
        helper.addOnClickListener(R.id.ches)
        when (helper.itemViewType) {
            Msg.TYPE_RECEIVED -> {
                chatpaly = helper.getView(R.id.chatpaly)
                chatpaly2 = helper.getView(R.id.chatpaly2)
                chatmsg2 = helper.getView(R.id.chatmsg2)
                showimgmsgs = helper.getView(R.id.showimgmsgs)
                filepackage = helper.getView(R.id.filepackage)
                filaename = helper.getView(R.id.filaename)
                filesize = helper.getView(R.id.filesize)
                grouptip = helper.getView(R.id.grouptip)
                dd = helper.getView(R.id.dd)
                TIMFriendshipManager.getInstance().getUsersProfile(arrayListOf(item.userInfoData.id),object: TIMValueCallBack<MutableList<TIMUserProfile>> {
                    override fun onError(p0: Int, p1: String?) {

                    }

                    override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                        if(p0==null)return
                        if(p0[0].remark==""){
                            helper.setText(R.id.myname,p0[0].nickName)
                        }else{
                            helper.setText(R.id.myname,p0[0].remark)
                        }

                    }
                })
                when (item.datatype) {
                    1 -> {
                        initview(helper)
                        chatmsg2.isVisible = true
                        dd.isVisible=true
                        ches.isVisible = true
                        helper.setText(R.id.chatmsg2, item.content as String)
                    }
                    2 -> {
                        initview(helper)
                        showimgmsgs.isVisible = true
                        ches.isVisible = true
                        helper.setImageBitmap(R.id.showimgmsgs, item.content as Bitmap)
                        helper.addOnClickListener(R.id.showimgmsgs)

                    }
                    3 -> {
                        initview(helper)
                        chatpaly.isVisible = true
                        chatpaly2.isVisible = true
                        ches.isVisible = true
                        chatpaly.text = (((item.content as Sounddata).time / 1000).toString() + "\'\'")
                        helper.addOnClickListener(R.id.chatpaly)
                    }
                    4 -> {
                        initview(helper)
                        filepackage.isVisible = true
                        ches.isVisible = true
                        filaename.text = ((item.content as TIMFileElem).fileName)
                        filesize.text = (((item.content as TIMFileElem).fileSize.toDouble().toBigDecimal().divide(1000000.toBigDecimal(), 2, BigDecimal.ROUND_HALF_UP)).toString() + "Mb")
                        helper.addOnClickListener(R.id.filepackage)
                    }
                    5 -> {
                        initview(helper)
                        ches.isVisible = false
                        grouptip.isVisible = true
                        val TIMGroupTipsElem = (item.content as TIMGroupTipsElem)
                        var Modify = ""
                        when (TIMGroupTipsElem.tipsType) {
                            TIMGroupTipsType.Join -> {
                                Modify = "用户${TIMGroupTipsElem.opUser}加入了群"
                            }
                            TIMGroupTipsType.Kick -> {
                                Modify = "用户${TIMGroupTipsElem.opUser}被踢出了群"
                            }
                            TIMGroupTipsType.Quit -> {
                                Modify = "用户${TIMGroupTipsElem.opUser}退出了群"
                            }
                            TIMGroupTipsType.SetAdmin -> {
                                Modify = "用户${TIMGroupTipsElem.opUser}成为了管理"
                            }
                            TIMGroupTipsType.CancelAdmin -> {
                                Modify = "用户${TIMGroupTipsElem.opUser}被取消了管理"
                            }
                            TIMGroupTipsType.ModifyGroupInfo -> {
                                if (TIMGroupTipsElem.groupInfoList.size != 0) {
                                    when (TIMGroupTipsElem.groupInfoList[0].type) {
                                        TIMGroupTipsGroupInfoType.ModifyFaceUrl -> {
                                            Modify = "用户${TIMGroupTipsElem.opUser}变更了群头像"
                                        }
                                        TIMGroupTipsGroupInfoType.ModifyName -> {
                                            Modify = "用户${TIMGroupTipsElem.opUser}变更了群名字"
                                        }
                                        TIMGroupTipsGroupInfoType.ModifyIntroduction -> {
                                            Modify = "用户${TIMGroupTipsElem.opUser}变更了群简介"
                                        }
                                        TIMGroupTipsGroupInfoType.ModifyNotification -> {
                                            Modify = "用户${TIMGroupTipsElem.opUser}变更了群介绍"
                                        }
                                        TIMGroupTipsGroupInfoType.ModifyOwner -> {
                                            Modify = "用户${TIMGroupTipsElem.opUser}变更了群主"
                                        }
                                        else -> {
                                        }
                                    }
                                }
                            }
                            else -> {
                            }
                        }

                        helper.setText(R.id.grouptip, Modify)
                    }
                }


            }
            Msg.TYPE_SENT -> {
                dd = helper.getView(R.id.dd)
                chatbg = helper.getView(R.id.chatbg)
                chatmsgs3 = helper.getView(R.id.chatmsgs3)
                chatmsgs2 = helper.getView(R.id.chatmsgs2)
                chatmsgs1 = helper.getView(R.id.chatmsgs1)
                soundanm = helper.getView(R.id.soundanm)
                filepackage = helper.getView(R.id.filepackage)
                filaename = helper.getView(R.id.filaename)
                filesize = helper.getView(R.id.filesize)
                helper.setText(R.id.chatnamebbb, item.userInfoData.names)
                when (item.datatype) {
                    1 -> {
                        initview2(helper)
                        chatmsgs1.isVisible = true
                        dd.isVisible=true
                        helper.setText(R.id.chatmsgs1, item.content as String)

                    }
                    2 -> {
                        initview2(helper)
                        chatmsgs2.isVisible = true
                        helper.setImageBitmap(R.id.chatmsgs2, item.content as Bitmap)
                        helper.addOnClickListener(R.id.chatmsgs2)
                    }
                    3 -> {
                        initview2(helper)
                        chatmsgs3.isVisible = true
                        soundanm.isVisible = true
                        chatmsgs3.text = (((item.content as Sounddata).time / 1000).toString() + "\'\'")
                        helper.addOnClickListener(R.id.chatmsgs3)

                    }
                    4 -> {
                        initview2(helper)
                        filepackage.isVisible = true
                        filaename.text = ((item.content as TIMFileElem).fileName)
                        filesize.text = (((item.content as TIMFileElem).fileSize.toDouble().toBigDecimal().divide(1000000.toBigDecimal(), 2, BigDecimal.ROUND_HALF_UP)).toString() + "Mb")
                        helper.addOnClickListener(R.id.filepackage)
                    }

                }

            }
        }

    }

    fun initview(helper: BaseViewHolder) {
        grouptip.isVisible = false
        filepackage.isVisible = false
        showimgmsgs.isVisible = false
        chatmsg2.isVisible = false
        chatpaly.isVisible = false
        chatpaly2.isVisible = false
        dd.isVisible = false
    }

    fun initview2(helper: BaseViewHolder) {
        filepackage.isVisible = false
        soundanm.isVisible = false
        chatmsgs1.isVisible = false
        chatmsgs2.isVisible = false
        chatmsgs3.isVisible = false
        dd.isVisible = false
//        chatbg.isVisible = false
    }


}
