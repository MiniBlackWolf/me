package study.kotin.my.address.Addresspersenter.view

import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp

interface AddressView:BaseView{
    fun reslut()
    fun uploadimg(result: BaseResp<String>)
}