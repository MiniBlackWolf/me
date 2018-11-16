package study.kotin.my.mycenter.injection.module

import com.example.home.seriver.HomeSeriver
import com.example.home.seriver.SearchSeriver
import com.example.home.seriver.imp.HomeSeriverImp
import com.example.home.seriver.imp.SearchServiceimp
import dagger.Module
import dagger.Provides
import study.kotin.my.mycenter.injection.HomeScope



@Module
class Homemodule {
    @HomeScope
    @Provides
    fun ProvideshomeSeriver(homeSeriverImp: HomeSeriverImp): HomeSeriver =homeSeriverImp
    @HomeScope
    @Provides
    fun ProvidesSearchService(SearchServiceimp: SearchServiceimp): SearchSeriver = SearchServiceimp
}