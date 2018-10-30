package com.example.home.ui.Frament

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.eightbitlab.rxbus.Bus
import com.example.home.R
import com.example.home.common.UpdateMessgeSizeEvent
import com.example.home.persenter.HomePersenter
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet


class HomeFarment: BaseMVPFragmnet<HomePersenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homemain_layout, container, false)
        view.find<ImageView>(R.id.search).let { it ->
            it.setOnClickListener{
                Bus.send(UpdateMessgeSizeEvent(5))
            }

        }

        return view
    }
}