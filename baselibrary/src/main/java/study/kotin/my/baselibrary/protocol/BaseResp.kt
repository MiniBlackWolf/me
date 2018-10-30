package study.kotin.my.baselibrary.protocol

class BaseResp<out T>(val status:Int,val message:String,val data:T) {
}