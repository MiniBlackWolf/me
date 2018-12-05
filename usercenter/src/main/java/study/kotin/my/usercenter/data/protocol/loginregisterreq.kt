package study.kotin.my.usercenter.data.protocol

import study.kotin.my.baselibrary.common.Poko

@Poko
data class loginregisterreq(val username:String,val password:String) {

}