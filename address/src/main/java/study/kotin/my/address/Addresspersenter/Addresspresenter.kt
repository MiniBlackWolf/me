package study.kotin.my.address.Addresspersenter

import study.kotin.my.address.Addresspersenter.view.AddressView
import study.kotin.my.address.service.AddressService
import study.kotin.my.baselibrary.presenter.Basepersenter
import javax.inject.Inject

class Addresspresenter @Inject constructor() : Basepersenter<AddressView>() {
    @Inject
    lateinit var AddressServiceimp: AddressService

    fun ss() {
        AddressServiceimp.aa()
        mView.reslut()
    }
}