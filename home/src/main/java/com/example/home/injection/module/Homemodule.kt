package study.kotin.my.mycenter.injection.module

import com.example.home.seriver.HomeSeriver
import com.example.home.seriver.imp.HomeSeriverImp
import dagger.Module
import dagger.Provides
import study.kotin.my.mycenter.injection.HomeScope



@Module
class Homemodule {
    @HomeScope
    @Provides
    fun ProvidesMyService(homeSeriverImp: HomeSeriverImp): HomeSeriver =homeSeriverImp
}