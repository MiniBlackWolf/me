package com.example.home.seriver.imp

import com.example.home.Repossitory.HomeRepossitory
import com.example.home.data.searchuserdata
import com.example.home.data.sendsearchuserdata
import com.example.home.seriver.SearchSeriver
import io.reactivex.Observable
import javax.inject.Inject

class SearchServiceimp @Inject constructor():SearchSeriver {

    @Inject
    lateinit var HomeRepossitory: HomeRepossitory
    override fun search(Authorization: String, sendsearchuserdata: sendsearchuserdata): Observable<searchuserdata> {
       return HomeRepossitory.search(Authorization,sendsearchuserdata)
    }


}