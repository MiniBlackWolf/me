package com.example.home.seriver

import com.example.home.data.articledata
import io.reactivex.Observable
import study.kotin.my.baselibrary.protocol.BaseResp

interface articleService {
    fun addarticle(Authorization: String, articledata: articledata): Observable<BaseResp<String>>
    fun findarticle(id:Map<String,String>):Observable<List<articledata>>

}