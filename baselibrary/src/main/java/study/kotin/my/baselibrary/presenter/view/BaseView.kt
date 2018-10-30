package study.kotin.my.baselibrary.presenter.view

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError()
}