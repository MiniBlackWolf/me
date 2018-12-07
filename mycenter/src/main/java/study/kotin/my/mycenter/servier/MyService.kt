package study.kotin.my.mycenter.servier

import io.reactivex.Observable
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.mycenter.net.Mycenterapi

interface MyService {
     fun Logout(): Observable<BaseResp<String>>
     fun changePassword(Authorization:String,oldepsd:String,newpsd:String): Observable<BaseResp<String>>
     fun Synchronizeinfo(Authorization:String): Observable<BaseResp<String>>
}