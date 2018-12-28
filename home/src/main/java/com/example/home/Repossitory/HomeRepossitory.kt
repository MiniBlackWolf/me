package com.example.home.Repossitory



import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.net.homeapi
import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp
import javax.inject.Inject

class HomeRepossitory @Inject constructor(){
    fun search(Authorization: String, keywords:String ): Observable<searchuserdata> {
        return RetrofitFactory.instance.creat(homeapi::class.java).search(Authorization,keywords)
    }
    fun GroupSearch(Authorization: String, name:String): Observable<searchgroupdata> {
        return RetrofitFactory.instance.creat(homeapi::class.java).GroupSearch(Authorization,name)
    }
}