package study.kotin.my.find.injection.commponent

import dagger.Component
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.find.injection.findScope
import study.kotin.my.find.injection.module.findmodule
import study.kotin.my.find.ui.Activity.AddFriendDtActivity
import study.kotin.my.find.ui.Activity.FriendDtActivity
import study.kotin.my.find.ui.Activity.nearbyActivity

@findScope
@Component(dependencies = arrayOf(ActivityCommpoent::class),modules = arrayOf(findmodule::class))
interface FindCommponent {
    fun inject(nearbyActivity: nearbyActivity)
    fun inject(AddFriendDtActivity: AddFriendDtActivity)
    fun inject(FriendDtActivity: FriendDtActivity)
}