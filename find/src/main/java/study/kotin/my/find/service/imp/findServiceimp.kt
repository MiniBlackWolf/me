package study.kotin.my.find.service.imp


import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.find.Repossitory.findRepossitory
import study.kotin.my.find.data.ConmentData
import study.kotin.my.find.data.Getfriendcicledata
import study.kotin.my.find.data.friendcicledata
import study.kotin.my.find.data.neardata
import study.kotin.my.find.service.findService
import javax.inject.Inject

class findServiceimp @Inject constructor() : findService {
    @Inject
    lateinit var findRepossitory: findRepossitory


    override fun addlike(Authorization: String, id: Int): Observable<BaseResp<String>> {
        return findRepossitory.addlike(Authorization, id)
    }


    override fun findByNear(Authorization: String): Observable<List<neardata>> {
        return findRepossitory.findByNear(Authorization).flatMap {
            Observable.just(it.rows)
        }
    }

    override fun friendcicle(Authorization: String, friendcicledata: friendcicledata): Observable<BaseResp<String>> {
        return findRepossitory.friendcicle(Authorization, friendcicledata)
    }

    override fun uploadimg(Authorization: String, file: List<MultipartBody.Part>): Observable<BaseResp<String>> {
        return findRepossitory.uploadimg(Authorization, file)
    }

    override fun addConment(Authorization: String, ConmentData: ConmentData): Observable<BaseResp<String>> {
        return findRepossitory.addConment(Authorization, ConmentData)
    }

    override fun getfriendcicle(Authorization: String, page: Int, size: Int): Observable<List<Getfriendcicledata>> {
        return findRepossitory.getfriendcicle(Authorization, page, size).flatMap {
            Observable.just(it.rows)
        }
    }

    override fun getone(Authorization: String, userid: String, page: Int, size: Int): Observable<List<Getfriendcicledata>> {
        return findRepossitory.getone(Authorization, userid, page, size).flatMap {
            Observable.just(it.rows)
        }
    }
}