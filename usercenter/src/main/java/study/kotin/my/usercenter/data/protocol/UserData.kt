package study.kotin.my.usercenter.data.protocol

import study.kotin.my.baselibrary.common.Poko

@Poko
data class UserData(val Tag_Profile_Custom_email:String,
                    val Tag_Profile_Custom_password:String,
                    val Tag_Profile_Custom_uid:String,
                    val Tag_Profile_Custom_username:String ){

}