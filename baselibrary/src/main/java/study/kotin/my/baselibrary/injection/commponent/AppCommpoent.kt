package study.kotin.my.baselibrary.injection.commponent

import android.app.Activity
import android.content.Context
import dagger.Component
import study.kotin.my.baselibrary.injection.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppCommpoent {
    fun injectContext(): Context
}