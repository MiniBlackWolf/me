package study.kotin.my.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.usercenter.persenter.HomePersenter

class someFragment : BaseMVPFragmnet<HomePersenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}