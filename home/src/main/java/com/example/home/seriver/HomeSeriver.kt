package com.example.home.seriver

import io.reactivex.Observable

interface HomeSeriver {
    fun getdata(): Observable<Boolean>
}