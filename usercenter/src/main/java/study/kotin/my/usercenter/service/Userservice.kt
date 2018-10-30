package study.kotin.my.usercenter.service

import io.reactivex.Observable


interface Userservice {
    fun Login(user:String,pass:String): Observable<Boolean>
}