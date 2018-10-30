package study.kotin.my.address.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet

class AddressFrament:BaseMVPFragmnet<Addresspresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.addresspasge,container,false)
    }
}