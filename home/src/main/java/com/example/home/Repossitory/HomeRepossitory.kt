package com.example.home.Repossitory


import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import com.example.home.net.homeapi
import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import javax.inject.Inject

class HomeRepossitory @Inject constructor(){
    fun search(Authorization: String,sendsearchuserdata: sendsearchuserdata): Observable<searchuserdata> {
        return RetrofitFactory.instance.creat(homeapi::class.java).search(Authorization,sendsearchuserdata)
    }
}