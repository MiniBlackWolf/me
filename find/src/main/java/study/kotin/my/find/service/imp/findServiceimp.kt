package study.kotin.my.find.service.imp


import io.reactivex.Observable
import study.kotin.my.find.Repossitory.findRepossitory
import study.kotin.my.find.data.neardata
import study.kotin.my.find.service.findService
import javax.inject.Inject

class findServiceimp @Inject constructor() : findService {
    @Inject
    lateinit var findRepossitory: findRepossitory
    override fun findByNear(Authorization: String): Observable<List<neardata>> {
        return findRepossitory.findByNear(Authorization).flatMap {
            Observable.just(it.rows)
        }
    }
}