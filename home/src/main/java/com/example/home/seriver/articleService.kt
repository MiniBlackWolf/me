package com.example.home.seriver

import com.example.home.data.articledata
import com.example.home.data.listarticledata
import com.example.home.data.madengdata
import com.example.home.net.homeapi
import io.reactivex.Observable
import okhttp3.MultipartBody
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.protocol.BaseResp

interface articleService {
    fun addarticle(Authorization: String, articledata: articledata): Observable<BaseResp<String>>//发布文章
    fun findarticle(id:Map<String,String>):Observable<List<articledata>> //查找文章
    fun addactive( articledata: articledata):Observable<BaseResp<String>> //发布活动
    fun findactive(id:Map<String,String>):Observable<List<articledata>>//查找活动
    fun uploadimg(file: List<MultipartBody.Part>): Observable<BaseResp<String>> //上传图片
    fun join(Activeid: Int): Observable<BaseResp<String>> //加入活动
    fun quit(Activeid: Int): Observable<BaseResp<String>>//退出活动
    fun helper(page:Int): Observable<List<madengdata>>//马镫助手
}