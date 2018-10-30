package study.kotin.my.find.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.presenter.view.Findview

class Findfragment:BaseMVPFragmnet<Findpresenter>(),Findview {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.findlayout,container,false)
    }
}