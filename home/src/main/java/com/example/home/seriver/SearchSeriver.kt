package com.example.home.seriver

import com.example.home.data.articledata
import com.example.home.data.searchgroupdata
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import io.reactivex.Observable
import study.kotin.my.baselibrary.protocol.BaseResp

interface SearchSeriver {
 fun search(Authorization: String, keywords:String ): Observable<searchuserdata>
 fun GroupSearch(Authorization: String, name:String): Observable<searchgroupdata>

}