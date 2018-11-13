package study.kotin.my.baselibrary.rx

/*
    定义通用异常
 */
class BaseException(val status:Int,val msg:String) :Throwable()
