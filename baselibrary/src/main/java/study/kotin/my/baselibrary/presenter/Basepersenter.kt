package study.kotin.my.baselibrary.presenter


import android.content.Context
import com.trello.rxlifecycle2.LifecycleProvider
import study.kotin.my.baselibrary.presenter.view.BaseView
import javax.inject.Inject

open class Basepersenter <T:BaseView>{
    lateinit var mView:T
    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
    @Inject
    lateinit var context: Context
}