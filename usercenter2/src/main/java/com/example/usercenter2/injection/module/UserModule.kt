package com.example.usercenter2.injection.module

import dagger.Module
import dagger.Provides
import com.example.usercenter2.injection.UsercommponerScope
import com.example.usercenter2.service.Userservice
import com.example.usercenter2.service.impl.Userserviceimpl

@Module
class UserModule{
    @UsercommponerScope
    @Provides
    fun ProdivesUserService(Userserviceimpl: Userserviceimpl): Userservice =Userserviceimpl

}