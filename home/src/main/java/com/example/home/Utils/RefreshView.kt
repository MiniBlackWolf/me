package com.example.home.Utils


import android.animation.ObjectAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.animation.Animation
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.ajguan.library.IRefreshHeader
import com.ajguan.library.State
import com.example.home.R
import kotlinx.android.synthetic.main.refreshhead.view.*


class RefreshView ( context: Context): ConstraintLayout(context),IRefreshHeader {
  init {
      LayoutInflater.from(context).inflate(R.layout.refreshhead, this)
  }
    private var trun :Boolean=true
    override fun refreshing() {
      initview()
      loading.isVisible=true
      val loading = ObjectAnimator.ofFloat(loading, "rotation", 0f, 360f,0f)
      loading.duration=1000
      loading.setRepeatCount(Animation.INFINITE)
      loading.start()
      test1.setText("正在加载")
    }

    override fun onPositionChange(p0: Float, p1: Float, p2: Float, p3: Boolean, p4: State?) {
      if(p0>=p2){
          if(trun) {
              val arrowam = ObjectAnimator.ofFloat(arrow, "rotationX", 0f, 180f)
              arrowam.duration = 1000
              arrowam.start()
              test1.setText("松手加载更多")
              trun=false
          }
      }

    }

    override fun complete() {
      initview()
      sus.isVisible=true
      test1.setText("加载成功")
      val arrowam = ObjectAnimator.ofFloat(arrow, "rotationX", 0f, 0f)
      arrowam.duration = 0
      arrowam.start()
        trun=true
    }

    override fun pull() {
      initview()
      arrow.isVisible=true
      test1.setText("下拉加载更多")
    }

    override fun reset() {
    }

  fun initview(){
    loading.isVisible=false
    arrow.isVisible=false
    sus.isVisible=false
  }


}