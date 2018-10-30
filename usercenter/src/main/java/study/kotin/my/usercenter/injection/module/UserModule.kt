package study.kotin.my.usercenter.injection.module

import dagger.Module
import dagger.Provides
import study.kotin.my.usercenter.injection.UsercommponerScope
import study.kotin.my.usercenter.service.Userservice
import study.kotin.my.usercenter.service.impl.Userserviceimpl

@Module
class UserModule{
    @UsercommponerScope
    @Provides
    fun ProdivesUserService(Userserviceimpl:Userserviceimpl):Userservice=Userserviceimpl

}