package com.example.home.data

import study.kotin.my.baselibrary.common.Poko
import java.io.Serializable

@Poko
 data class Sounddata (val path:String,val time:Long) : Serializable{
}