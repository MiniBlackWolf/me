package study.kotin.my.mycenter.persenter.view

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Part
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface ChangeInfoview : BaseView {
    fun Synchronizeinfo(result: BaseResp<String>)
    fun uploadimg(result: BaseResp<String>)
    fun loadProvince(result:List<ProvinceList.DataBean>)
    fun loadschool(result:List<SchoolList.DataBean>)
}