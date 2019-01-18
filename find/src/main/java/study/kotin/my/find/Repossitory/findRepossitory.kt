package study.kotin.my.find.Repossitory

import io.reactivex.Observable
import retrofit2.http.Header
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.find.data.neardata
import study.kotin.my.find.data.rowsdata
import study.kotin.my.find.net.FindApi
import javax.inject.Inject

/**
 * Creat by blackwolf
 * 2019/1/18
 * system username : Administrator
 */
class findRepossitory @Inject constructor() {
    fun findByNear(Authorization: String): Observable<rowsdata<neardata>>{
        return RetrofitFactory.instance.creat(FindApi::class.java).findByNear(Authorization)
    }
}