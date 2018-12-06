package study.kotin.my.mycenter.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMConversationType
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMManagerExt
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import kotlinx.android.synthetic.main.allsetting.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.setuserstting
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.GifSizeFilter
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.persenter.Mypersenter

class AllSettingActivity : BaseMVPActivity<Mypersenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.s1_1 -> {
                if (s1_1.isChecked) {
                    setuserstting("Invisible", true)
                } else {
                    setuserstting("Invisible", false)
                }

            }
            R.id.s2_1 -> {
                if (s2_1.isChecked) {
                    setuserstting("Mute", true)
                } else {
                    setuserstting("Mute", false)
                }
            }
            R.id.s3 -> {
            }
            R.id.s4 -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(1)
                        .capture(false)
                        .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(GlideEngine())
                        .forResult(1)

            }
            R.id.s5 -> {
                startActivity<changepassActivity>()
            }
            R.id.s6_1 -> {
                if (s6_1.isChecked) {
                    setuserstting("Strangernews", true)
                } else {
                    setuserstting("Strangernews", false)
                }
            }
            R.id.s7_1 -> {
                if (s7_1.isChecked) {
                    setuserstting("Friendverification", true)
                } else {
                    setuserstting("Friendverification", false)
                }
            }
            R.id.s8_1 -> {
                if (s8_1.isChecked) {
                    setuserstting("wifimsg", true)
                } else {
                    setuserstting("wifimsg", false)
                }
            }
            R.id.s9_1 -> {
                if (s9_1.isChecked) {
                    setuserstting("Strangerdynamic", true)
                } else {
                    setuserstting("Strangerdynamic", false)
                }
            }
            R.id.cleanmsg -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("是否清空所有消息记录?")
                builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val conversationList = TIMManagerExt.getInstance().conversationList
                        for (c in conversationList) {
                            TIMConversationExt(c).deleteLocalMessage(object : TIMCallBack {
                                override fun onSuccess() {
                                }

                                override fun onError(p0: Int, p1: String?) {
                                }
                            })
                        }
                        toast("清空完成!")
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allsetting)
        s1_1.setOnClickListener(this)
        s2_1.setOnClickListener(this)
        s3.setOnClickListener(this)
        s4.setOnClickListener(this)
        s5.setOnClickListener(this)
        s6_1.setOnClickListener(this)
        s7_1.setOnClickListener(this)
        s8_1.setOnClickListener(this)
        s9_1.setOnClickListener(this)
        cleanmsg.setOnClickListener(this)
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
            s4_1.setImageBitmap(photoImg)
        }
        val edit = getSharedPreferences("UserSetting", Context.MODE_PRIVATE)
        s1_1.isChecked=edit.getBoolean("Invisible", false)
        s2_1.isChecked=edit.getBoolean("Mute", false)
        s6_1.isChecked=edit.getBoolean("Strangernews", false)
        s7_1.isChecked=edit.getBoolean("Friendverification", true)
        s8_1.isChecked=edit.getBoolean("wifimsg", true)
        s9_1.isChecked=edit.getBoolean("Strangerdynamic", false)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //获取图片路径
            val mSelected = Matisse.obtainResult(data!!) //uri
            val obtainPathResult = Matisse.obtainPathResult(data) //path
            val bitmap = BitmapFactory.decodeFile(obtainPathResult.get(0)) //bitmap
            val sharedPreferencess = getSharedPreferences("boolen", Context.MODE_PRIVATE).edit()
            sharedPreferencess.putString("BGpath", obtainPathResult[0])
            sharedPreferencess.apply()
            Log.d("Matisse", "mSelected: $obtainPathResult")
        }
    }
}