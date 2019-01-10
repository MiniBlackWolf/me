package com.example.home.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMManagerExt
import com.tencent.imsdk.ext.sns.TIMDelFriendType
import com.tencent.imsdk.ext.sns.TIMFriendResult
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import kotlinx.android.synthetic.main.personalchatsettingslayout.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.getuserstting
import study.kotin.my.baselibrary.ext.setuserstting
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.*

class PersonalChatSettingActivity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    var id: String? = ""
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.Messagenotification -> {
                val view = layoutInflater.inflate(R.layout.messagenotificationitem, null)
                val popWindow = PopupWindow(this)
                popWindow.contentView = view//显示的布局，还可以通过设置一个View // .size(600,400) //设置显示的大小，不设置就默认包裹内容
                popWindow.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)))
                popWindow.setFocusable(true)//是否获取焦点，默认为ture
                popWindow.setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                popWindow.showAsDropDown(notmsg, 0, 10)//显示PopupWindow
                view.find<RadioGroup>(R.id.gp).setOnCheckedChangeListener { group, checkedId ->
                    notmsg.text = view.find<RadioButton>(checkedId).text.toString()
                    val sharedPreferences = getSharedPreferences("boolen", Context.MODE_PRIVATE).edit()
                    if (view.find<RadioButton>(checkedId).text.toString() == "静音") {
                        sharedPreferences.putBoolean("RadioGroup", true)
                        sharedPreferences.apply()
                    } else {
                        sharedPreferences.putBoolean("RadioGroup", false)
                        sharedPreferences.apply()
                    }

                }

            }
            R.id.publicgroupcat -> {
                startActivity<ChatRecordActivity>("id" to id)
            }
            R.id.s2 -> {
                startActivity<ChatFileActivity>("id" to id)

            }
            R.id.chatbg -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(1)
                        .capture(false)
                        .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(study.kotin.my.baselibrary.common.GlideEngine())
                        .forResult(1)

            }
            R.id.Remarks -> {
                val builder = AlertDialog.Builder(this)
                val edittext = EditText(this)
                builder.setView(edittext)
                builder.setTitle("修改备注名")
                builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        if (edittext.text.toString().isEmpty() || edittext.text.toString().length > 15) {
                            toast("备注名不能为空或大于15个字符")
                            return
                        }
                        val param = TIMFriendshipManagerExt.ModifySnsProfileParam(id!!)
                        param.setRemark(edittext.text.toString())
                        TIMFriendshipManagerExt.getInstance().modifySnsProfile(param, object : TIMCallBack {
                            override fun onSuccess() {
                                val fdname = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                                fdname.putString("${id}fdname",edittext.text.toString())
                                fdname.apply()
                                Remarksshow.text = edittext.text.toString()
                                toast("修改成功")
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
            R.id.delfd -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("是否删除好友?")
                builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val param = TIMFriendshipManagerExt.DeleteFriendParam()
                        param.setType(TIMDelFriendType.TIM_FRIEND_DEL_BOTH)
                                .setUsers(arrayListOf(id))
                        TIMFriendshipManagerExt.getInstance().delFriend(param, object : TIMValueCallBack<MutableList<TIMFriendResult>> {
                            override fun onSuccess(p0: MutableList<TIMFriendResult>?) {
                                if(p0==null){
                                    ARouter.getInstance().build("/App/Homepage").navigation()
                                    finish()
                                    return
                                }
                                TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.C2C,p0[0].identifer)
                                toast("删除成功")
                                ARouter.getInstance().build("/App/Homepage").navigation()
                            }

                            override fun onError(p0: Int, p1: String?) {
                                Log.e("eeeeeeeeee", p1)
                                toast("删除失败$p1")
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
            R.id.chf -> {
                finish()
            }
            R.id.cleanmsg -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("是否清空消息记录?")
                builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val con = TIMManager.getInstance().getConversation(TIMConversationType.C2C, id)
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
            R.id.a1->{
                if(a1.isChecked){
                    setuserstting("disturb",true)
                }else{

                    setuserstting("disturb",false)
                }

            }
            R.id.a2->{
                if(a2.isChecked){
                    setuserstting("Shield",true)
                }else{

                    setuserstting("Shield",false)
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalchatsettingslayout)
        //初始化视图
        initview()

        //通知选项
        val RadioGroup = getSharedPreferences("boolen", Context.MODE_PRIVATE).getBoolean("RadioGroup", true)
        if (RadioGroup) {
            notmsg.text = "静音"
        } else {
            notmsg.text = "音乐"
        }


        //背景略缩图
        val Bgpath = getSharedPreferences("boolen", Context.MODE_PRIVATE).getString("BGpath", "")
        if (Bgpath != "") {
            //压缩bitmap
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(Bgpath, options)
            val ratio = Math.max(options.outWidth * 1.0 / 1024f, options.outHeight * 1.0 / 1024f)
            options.inSampleSize = Math.ceil(ratio).toInt()
            options.inJustDecodeBounds = false
            val photoImg = BitmapFactory.decodeFile(Bgpath, options)
            Bgshow.setImageBitmap(photoImg)
        }


        //备注名
        val fdname = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("${id}fdname", "")
        Remarksshow.text = fdname

        a1.isChecked=getuserstting("disturb")
        a2.isChecked=getuserstting("Shield")

    }

    private fun initview() {
        id = intent.extras?.getString("id")
        Messagenotification.setOnClickListener(this)
        publicgroupcat.setOnClickListener(this)
        s2.setOnClickListener(this)
        chatbg.setOnClickListener(this)
        Remarks.setOnClickListener(this)
        delfd.setOnClickListener(this)
        chf.setOnClickListener(this)
        cleanmsg.setOnClickListener(this)
        a1.setOnClickListener(this)
        a2.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if(data==null){
                return
            }
            //获取图片路径
            val mSelected = Matisse.obtainResult(data) //uri
            val obtainPathResult = Matisse.obtainPathResult(data) //path
            val bitmap = BitmapFactory.decodeFile(obtainPathResult.get(0)) //bitmap
            val sharedPreferencess = getSharedPreferences("boolen", Context.MODE_PRIVATE).edit()
            sharedPreferencess.putString("BGpath", obtainPathResult[0])
            sharedPreferencess.apply()
            Log.d("Matisse", "mSelected: $obtainPathResult")
        }
    }
}