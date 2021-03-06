package study.kotin.my.address.injection.commponent

import dagger.Component
import study.kotin.my.address.injection.AddressScope
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.address.service.imp.AddressServiceimp
import study.kotin.my.address.ui.activity.AddGroupActivity
import study.kotin.my.address.ui.activity.AddressActivity
import study.kotin.my.address.ui.frament.AddressFrament
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent

@AddressScope
@Component(dependencies = arrayOf(ActivityCommpoent::class),modules = arrayOf(Addressmodule::class))
interface AddressCommponent {
    fun inject(addressActivity: AddressActivity)
    fun inject(addressFrament: AddressFrament)
    fun inject(AddGroupActivity: AddGroupActivity)
}