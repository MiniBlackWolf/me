package study.kotin.my.mycenter.injection.commponent

import com.example.home.ui.Frament.HomeFarment
import com.example.home.ui.activity.HomeActivity
import com.example.home.ui.activity.PublicGroupFarment_3_Article_Activity
import com.example.home.ui.activity.SearchActivity
import dagger.Component
import study.kotin.my.baselibrary.injection.commponent.ActivityCommpoent
import study.kotin.my.mycenter.injection.HomeScope
import study.kotin.my.mycenter.injection.module.Homemodule


@HomeScope
@Component(dependencies = arrayOf(ActivityCommpoent::class),modules = arrayOf(Homemodule::class))
interface HomeCommponent{
    fun inject(HomeFarment: HomeFarment)
    fun inject(HomeActivity: HomeActivity)
    fun inject(SearchActivity: SearchActivity)
    fun inject(PublicGroupFarment_3_Article_Activity: PublicGroupFarment_3_Article_Activity)
}