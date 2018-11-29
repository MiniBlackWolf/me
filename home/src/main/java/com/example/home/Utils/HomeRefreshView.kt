package com.example.home.Utils


import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.isVisible
import com.ajguan.library.EasyRefreshLayout
import com.ajguan.library.IRefreshHeader
import com.ajguan.library.State
import com.example.home.R
import com.example.home.R.layout.homerefreshhead
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import kotlinx.android.synthetic.main.refreshhead.view.*
import okio.JvmField


class HomeRefreshView(context: Context) : ConstraintLayout(context), RefreshHeader {
    val views: View = LayoutInflater.from(context).inflate(R.layout.homerefreshhead, null)
    lateinit var recyclerView:RecyclerView
    fun initview(recyclerView: RecyclerView) {
       this.recyclerView=recyclerView
    }

    override fun getView(): View {
        return views
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        return 500
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }


    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        recyclerView.isVisible=false

    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }
}