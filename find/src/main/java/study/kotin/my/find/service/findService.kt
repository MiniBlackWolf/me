package study.kotin.my.find.service

import io.reactivex.Observable
import study.kotin.my.find.data.neardata
import study.kotin.my.find.data.rowsdata

interface findService {
    fun findByNear(Authorization: String): Observable<List<neardata>>
}