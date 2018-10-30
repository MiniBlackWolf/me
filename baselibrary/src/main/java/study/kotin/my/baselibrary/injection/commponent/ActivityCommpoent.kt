package study.kotin.my.baselibrary.injection.commponent

import android.app.Activity
import android.content.Context
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component
import study.kotin.my.baselibrary.injection.ActivityScope
import study.kotin.my.baselibrary.injection.module.ActivityModule
import study.kotin.my.baselibrary.injection.module.LifecycleProviderModule

@ActivityScope
@Component(dependencies = arrayOf(AppCommpoent::class),modules = arrayOf(ActivityModule::class,LifecycleProviderModule::class))
interface ActivityCommpoent {
    fun injectactivity():Activity
    fun LifecycleProvider(): LifecycleProvider<*>
    fun context(): Context
}