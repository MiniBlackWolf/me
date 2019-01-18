package study.kotin.my.find.injection.module

import dagger.Module
import dagger.Provides
import study.kotin.my.find.injection.findScope
import study.kotin.my.find.service.findService
import study.kotin.my.find.service.imp.findServiceimp

@Module
class findmodule {

    @findScope
    @Provides
    fun ProvidesfindService(findServiceimp: findServiceimp): findService = findServiceimp
}