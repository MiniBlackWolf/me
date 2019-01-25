package study.kotin.my.find.presenter.view

import android.widget.LinearLayout
import android.widget.TextView
import io.reactivex.Observable
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.find.data.ConmentData
import study.kotin.my.find.data.Getfriendcicledata

/**
 * Creat by blackwolf
 * 2019/1/23
 * system username : Administrator
 */
interface friendsCircleView:BaseView {
    fun addConment(t:BaseResp<String>, LinearLayout: LinearLayout, content: ConmentData, type:Int)
    fun getfriendcicle(t:List<Getfriendcicledata>)
    fun addlike(t:BaseResp<String>,goodcount: TextView)
    fun getone(t:List<Getfriendcicledata>)
}