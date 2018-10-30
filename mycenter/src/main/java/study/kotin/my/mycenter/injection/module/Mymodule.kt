package study.kotin.my.mycenter.injection.module

import dagger.Module
import dagger.Provides
import study.kotin.my.mycenter.injection.MyScope
import study.kotin.my.mycenter.servier.MyService
import study.kotin.my.mycenter.servier.imp.MyServiceimp


@Module
class Mymodule {
    @MyScope
    @Provides
    fun ProvidesMyService(myServiceimp: MyServiceimp):MyService=myServiceimp
}