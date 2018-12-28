package study.kotin.my.address.Addresspersenter.view


import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface AddressView:BaseView{
    fun reslut()
    fun uploadimg(result: BaseResp<String>)
    fun loadProvince(result:List<ProvinceList.DataBean>)
    fun loadschool(result:List<SchoolList.DataBean>)
}