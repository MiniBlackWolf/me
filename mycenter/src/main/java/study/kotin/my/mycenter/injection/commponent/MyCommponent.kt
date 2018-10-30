package study.kotin.my.mycenter.injection.commponent

import dagger.Component
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.baselibrary.injection.commponent.AppCommpoent
import study.kotin.my.mycenter.injection.MyScope
import study.kotin.my.mycenter.injection.module.Mymodule
import study.kotin.my.mycenter.ui.activity.MyActivity
import study.kotin.my.mycenter.ui.activity.MyClassActivity
import study.kotin.my.mycenter.ui.frament.MyFragment

@MyScope
@Component(dependencies = arrayOf(ActivityCommpoent::class),modules = arrayOf(Mymodule::class))
interface MyCommponent{
    fun inject(MyFragment: MyFragment)
    fun inject(MyActivity: MyActivity)
    fun inject(myClassActivity: MyClassActivity)
}