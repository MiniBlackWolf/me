package study.kotin.my.baselibrary.injection.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import study.kotin.my.baselibrary.injection.ActivityScope


@Module
class ActivityModule(private val activity: Activity) {
    @ActivityScope
    @Provides
    fun ProvideContext():Activity=activity
}