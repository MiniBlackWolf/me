package com.example.home.Messges


import android.graphics.BitmapFactory
import android.util.Log
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.*
import study.kotin.my.baselibrary.utils.FileUtil


object ImgMessge {

    fun showimgmessge(message: TIMMessage?, mview: HomeView) {
        for (i in 0 until message!!.getElementCount()) {
            //图片元素
            val e = message.getElement(i.toInt()) as TIMImageElem
            for (image in e.imageList) {

                //获取图片类型, 大小, 宽高
                Log.d("imgs--d", "image type: " + image.type +
                        " image size " + image.size +
                        " image height " + image.height +
                        " image width " + image.width)
                val uuid = image.uuid
                image.getImage(FileUtil.getCacheFilePath(uuid), object : TIMCallBack {
                    override fun onError(code: Int, desc: String) {//获取图片失败
                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 含义请参见错误码表
                        Log.d("imgs--d", "getImage failed. code: $code errmsg: $desc")
                    }

                    override fun onSuccess() {//成功，参数为图片数据
                        //doSomething
                        val bitmap = BitmapFactory.decodeFile(FileUtil.getCacheFilePath(uuid))
                        mview.showimgmsg(bitmap,message.sender)
                        Log.d("imgs--d", "getImage success.")
                    }
                })
            }
        }

    }
}