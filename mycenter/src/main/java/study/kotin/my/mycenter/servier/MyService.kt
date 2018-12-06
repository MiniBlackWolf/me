package study.kotin.my.mycenter.servier

import io.reactivex.Observable
import study.kotin.my.baselibrary.protocol.BaseResp

interface MyService {
     fun Logout(): Observable<BaseResp<String>>
}