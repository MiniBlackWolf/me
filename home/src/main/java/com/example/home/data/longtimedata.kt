package com.example.home.data

import study.kotin.my.baselibrary.common.Poko
import java.io.Serializable

@Poko
data class longtimedata(val data: Any,val id:String, val time: Long,val type:Int,val isseft:Boolean)  : Serializable {


}