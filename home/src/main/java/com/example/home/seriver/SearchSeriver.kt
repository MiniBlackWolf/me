package com.example.home.seriver

import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import io.reactivex.Observable

interface SearchSeriver {
 fun search(Authorization: String,sendsearchuserdata: sendsearchuserdata): Observable<searchuserdata>
}