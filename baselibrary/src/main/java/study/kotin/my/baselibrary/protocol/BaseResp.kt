package study.kotin.my.baselibrary.protocol

class BaseResp<out T>(val success:Boolean,val message:String,val jwt:T,val sig:String) {
}