package study.kotin.my.baselibrary.presenter.view

import android.app.Activity

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
    fun onDataIsNull(){}//默认实现

}