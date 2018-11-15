package com.example.home.common

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.home.R
import com.example.home.Utils.ImgUtils
import com.example.home.data.Sounddata
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMFileElem
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.getsoundtime
import study.kotin.my.baselibrary.utils.FileUtil
import study.kotin.my.baselibrary.utils.MediaUtil
import java.io.File
import java.io.FileInputStream

class itemclickListener(val chatrecyclerview:RecyclerView,val context:Activity): BaseQuickAdapter.OnItemChildClickListener {
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

            //全部数据
            val data = adapter!!.data
            when (view!!.id) {
                //图片点击
                R.id.chatmsgs2 -> {
                    val imgview = adapter.getViewByPosition(chatrecyclerview, position, R.id.chatmsgs2) as ImageView
                    imgview.setDrawingCacheEnabled(true)
                    val bitmap = Bitmap.createBitmap(imgview.drawingCache)
                    imgview.setDrawingCacheEnabled(false)
                    val dialog = ImgUtils(bitmap, context).init()
                    dialog.show()
                }
                R.id.showimgmsgs -> {
                    val imgview = adapter.getViewByPosition(chatrecyclerview, position, R.id.showimgmsgs) as ImageView
                    imgview.setDrawingCacheEnabled(true)
                    val bitmap = Bitmap.createBitmap(imgview.drawingCache)
                    imgview.setDrawingCacheEnabled(false)
                    val dialog = ImgUtils(bitmap, context).init()
                    dialog.show()
                }
                //语音点击
                R.id.chatmsgs3 -> {
                    val soundanm = adapter.getViewByPosition(chatrecyclerview, position, R.id.soundanm) as ImageView
                    val chatmsgs3 = adapter.getViewByPosition(chatrecyclerview, position, R.id.chatmsgs3) as Button
                    val an = soundanm.background as AnimationDrawable
                    an.start()
                    try {
                        val fileInputStream = FileInputStream(((data.get(position) as Msg).content as Sounddata).path)
                        MediaUtil.getInstance().play(fileInputStream)
                        MediaUtil.getInstance().setEventListener {
                            an.stop()
                        }
                        val getsoundtime = MediaPlayer().getsoundtime(((data.get(position) as Msg).content as Sounddata).path)
                        object : CountDownTimer(getsoundtime, 1000) {
                            override fun onFinish() {
                                MediaUtil.getInstance().stop()
                                chatmsgs3.text = ((getsoundtime / 1000).toString() + "\'\'")
                                soundanm.background=context.resources.getDrawable(R.drawable.sounditem)
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                chatmsgs3.text = ((millisUntilFinished / 1000).toString() + "\'\'")
                            }


                        }.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                R.id.chatpaly -> {
                    val chatpaly2 = adapter.getViewByPosition(chatrecyclerview, position, R.id.chatpaly2)
                    val chatpaly = adapter.getViewByPosition(chatrecyclerview, position, R.id.chatpaly) as Button
                    val an = chatpaly2!!.background as AnimationDrawable
                    an.start()
                    try {
                        val fileInputStream = FileInputStream(((data.get(position) as Msg).content as Sounddata).path)
                        MediaUtil.getInstance().play(fileInputStream)
                        MediaUtil.getInstance().setEventListener {
                            an.stop()
                        }
                        val getsoundtime = MediaPlayer().getsoundtime(((data.get(position) as Msg).content as Sounddata).path)
                        object : CountDownTimer(getsoundtime, 1000) {
                            override fun onFinish() {
                                MediaUtil.getInstance().stop()
                                chatpaly.text = ((getsoundtime / 1000).toString() + "\'\'")
                                chatpaly2.background=context.resources.getDrawable(R.drawable.sounditem)
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                chatpaly.text = ((millisUntilFinished / 1000).toString() + "\'\'")
                            }


                        }.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                R.id.filepackage -> {
                    ActivityCompat.requestPermissions(context , arrayOf(android
                            .Manifest.permission.WRITE_EXTERNAL_STORAGE), 22)
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("是否下载文件?")
                    builder.setNegativeButton("取消") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.setPositiveButton("下载") { dialog, which ->
                        val TIMFileElem = (data.get(position) as Msg).content as TIMFileElem
                        val str = TIMFileElem.getFileName().split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                        val filename = str[str.size - 1]
                        if (FileUtil.isFileExist(filename, Environment.DIRECTORY_DOWNLOADS)) {
                            Toast.makeText(BaseApplication.context, BaseApplication.context.getString(R.string.save_exist), Toast.LENGTH_SHORT).show()
                            return@setPositiveButton
                        }
                        val path= Environment.getExternalStorageDirectory().canonicalPath+"/madeng"
                        val filepath= Environment.getExternalStorageDirectory().canonicalPath+"/madeng/$filename"
                        val file= File(filepath)
                        val file2= File(path)
                        if(file.exists()){
                            context.toast("文件已存在！${file.canonicalPath}")
                            dialog.dismiss()
                            return@setPositiveButton
                        }
                        if(!file2.exists()){
                            file2.mkdirs()
                        }
                        TIMFileElem.getToFile(filepath, object : TIMCallBack {
                            override fun onSuccess() {
                                context.toast("下载成功文件保存在${filepath}")
                                dialog.dismiss()
                            }

                            override fun onError(p0: Int, p1: String?) {
                                Log.e("eeeeee", p1)
                                dialog.dismiss()
                            }
                        })


                    }
                    builder.show()

                }



        }
    }
}