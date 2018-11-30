package com.example.home.Utils


import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.isVisible
import com.ajguan.library.EasyRefreshLayout
import com.ajguan.library.IRefreshHeader
import com.ajguan.library.State
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.home.HomeAdapter.ChatListAdapter
import com.example.home.R
import com.example.home.R.layout.homerefreshhead
import com.example.home.data.UserList
import com.example.home.ui.activity.HomeActivity
import com.google.gson.annotations.Until
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.refreshhead.view.*
import okio.JvmField
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity


class HomeRefreshView(context: Context) : ConstraintLayout(context), RefreshHeader {
    val views: View = LayoutInflater.from(context).inflate(R.layout.homerefreshhead, null)
    lateinit var recyclerView: RecyclerView
    val test1 = views.find<TextView>(R.id.test1)
    open val chatlistrc = views.find<RecyclerView>(R.id.chatlistrc)
    lateinit var smartRefreshLayout: SmartRefreshLayout
    lateinit var updataview: () -> Unit
    val sus = views.find<ImageView>(R.id.sus)
    val loading = views.find<ImageView>(R.id.loading)
    val arrow = views.find<ImageView>(R.id.arrow)
    val right2 = views.find<ImageView>(R.id.right2)
    val left2 = views.find<ImageView>(R.id.left2)
    val ofFloat = ObjectAnimator.ofFloat(loading, "rotation", 0f, 360f)
    val ofFloat2 = ObjectAnimator.ofFloat(arrow, "rotation", 0f, 180f)
    val ofFloat3 = ObjectAnimator.ofFloat(arrow, "rotation", 180f, 0f)
    var arrowset: Boolean = true
    fun hideview() {
        arrow.isVisible = false
        loading.isVisible = false
        sus.isVisible = false
    }

    fun initview(recyclerView: RecyclerView, smartRefreshLayout: SmartRefreshLayout, updataview: () -> Unit) {
        this.recyclerView = recyclerView
        this.smartRefreshLayout = smartRefreshLayout
        this.updataview = updataview
    }

    override fun getView(): View {
        return views
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        //    ofFloat.end()
        hideview()
        sus.isVisible = true
        return 1000
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
        recyclerView.isVisible = false
        hideview()
        loading.isVisible = true
        ofFloat.setDuration(1000)
        ofFloat.setRepeatCount(Animation.INFINITE)
        ofFloat.start()
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        when {
            offset >= 300 -> {
                smartRefreshLayout.setHeaderHeight(100f)
                smartRefreshLayout.setHeaderTriggerRate(1f)
                smartRefreshLayout.setOnRefreshListener(object : OnRefreshListener {
                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        smartRefreshLayout.finishRefresh(666666)
                    }
                })
                hideview()
                chatlistrc.isVisible = true
                test1.isVisible = false
            }
            offset in 90 until 180 -> {
                smartRefreshLayout.setHeaderHeight(60f)
                smartRefreshLayout.setHeaderTriggerRate(0.5f)
                smartRefreshLayout.setOnRefreshListener(object : OnRefreshListener {
                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        updataview()
                    }
                })
                test1.text = "释放刷新"
             //   arrow.isVisible = true
                test1.isVisible = true
                chatlistrc.isVisible = false
            }
            offset in 181..299 -> test1.text = "继续下滑打开社团列表"
            offset == 0 -> {

                smartRefreshLayout.finishRefresh()
            }
            offset <= 90 -> {
                right2.isVisible=false
                left2.isVisible=false
                hideview()
                arrow.isVisible = true
                test1.text = "下拉刷新"
            }
        }
        if (!isDragging) {
            test1.text = "刷新中"
        }
        if(!isDragging&&offset >= 300){
            right2.isVisible=true
        }
        Log.i("iiiiiiiiiiiii", "percent:$percent,offset:$offset,height:$height,maxDragHeight:$maxDragHeight")
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }


}