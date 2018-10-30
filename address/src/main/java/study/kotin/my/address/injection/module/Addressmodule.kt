package study.kotin.my.address.injection.module

import dagger.Module
import dagger.Provides
import study.kotin.my.address.injection.AddressScope
import study.kotin.my.address.service.AddressService
import study.kotin.my.address.service.imp.AddressServiceimp

@Module
class Addressmodule {

    @AddressScope
    @Provides
    fun ProvidesAddressService(addressServiceimp: AddressServiceimp): AddressService = addressServiceimp
}