package com.example.home.Utils

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.support.v7.app.AlertDialog
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.graphics.drawable.toDrawable
import com.example.home.R
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.drawable.BitmapDrawable
import com.github.chrisbanes.photoview.PhotoView
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.utils.FileUtil


class ImgUtils(val bmp: Bitmap, val context: Activity) {


    fun init(): Dialog {
        //大图所依附的dialog
        val dialog = Dialog(context, R.style.AlertDialog_AppCompat_Light)
        val mImageView = getImageView(bmp)
        dialog.setContentView(mImageView)
        //大图的点击事件（点击让他消失）
        mImageView.setOnClickListener {
            dialog.dismiss()
        }


        //大图的长按监听
        mImageView.setOnLongClickListener {
            //弹出的“保存图片”的Dialog
            val builder = AlertDialog.Builder(context)
            builder.setTitle("是否要保存图片？")

            builder.setPositiveButton("保存图片", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    saveCroppedImage(bmp)
                    dialog!!.dismiss()
                }
            })
            builder.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
            builder.show()
            true
        }
        return dialog

    }

    //保存图片
    private fun saveCroppedImage(bmp: Bitmap) {
        var file = File(FileUtil.getSDPath()+"/madengFolder")
        if (!file.exists()) file.mkdir()

        file = File("/sdcard/temp.jpg".trim())
        val fileName = file.getName()
        val mName = fileName.substring(0, fileName.lastIndexOf("."))
        val sName = fileName.substring(fileName.lastIndexOf("."))

        // /sdcard/myFolder/temp_cropped.jpg
        val newFilePath = FileUtil.getSDPath()+"/madengFolder"+ "/" + mName + bmp.generationId + sName
        file = File(newFilePath)
        try {
            file.createNewFile()
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos)
            fos.flush()
            fos.close()
            context.toast("文件已保存到$newFilePath")
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    //动态的ImageView
    fun getImageView(bmp: Bitmap): PhotoView {
        val iv = PhotoView(context)
        //宽高
        iv.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //设置Padding
        iv.setPadding(20, 20, 20, 20);
        //imageView设置图片
        iv.setImageBitmap(bmp)
        iv.setBackgroundColor(context.resources.getColor(R.color.white))
        return iv
    }
}