package com.example.home.seriver

import com.example.home.data.articledata
import io.reactivex.Observable
import study.kotin.my.baselibrary.protocol.BaseResp

interface HomeSeriver {
    fun getdata(): Observable<Boolean>

}