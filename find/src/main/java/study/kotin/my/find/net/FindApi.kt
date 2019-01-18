package study.kotin.my.find.net

import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST
import study.kotin.my.find.data.neardata
import study.kotin.my.find.data.rowsdata

/**
 * Creat by blackwolf
 * 2019/1/18
 * system username : Administrator
 */
interface FindApi {
    @POST("/user/findByNear?page=1&size=10")
    fun findByNear(@Header("Authorization") Authorization: String): Observable<rowsdata<neardata>>
}