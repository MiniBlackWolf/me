package study.kotin.my.usercenter.data.protocol

import study.kotin.my.baselibrary.common.Poko

@Poko
data class loginregisterreq(val user:String,val pass:String) {

}