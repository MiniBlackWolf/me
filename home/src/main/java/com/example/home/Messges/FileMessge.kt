package com.example.home.Messges

import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.home.R
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.*
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.utils.FileUtil

object FileMessge {


    fun showfileMessge(message: TIMMessage?, mview: HomeView) {
        for (i in 0 until message!!.getElementCount()) {
            val elem = message.getElement(i.toInt()) as TIMFileElem
            val str = elem.getFileName().split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            val filename = str[str.size - 1]
            if (FileUtil.isFileExist(filename, Environment.DIRECTORY_DOWNLOADS)) {
                Toast.makeText(BaseApplication.context, BaseApplication.context.getString(R.string.save_exist), Toast.LENGTH_SHORT).show()
                return
            }
            mview.showFilemsg(elem)
//            elem.getToFile(FileUtil.getCacheFilePath(filename), object : TIMCallBack {
//                override fun onSuccess() {
//
//                }
//
//                override fun onError(p0: Int, p1: String?) {
//                    Log.e("eeeeee", p1)
//                }
//            })
        }

    }
}