package study.kotin.my.find.ui.Activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.tencent.imsdk.ext.sns.TIMFriendshipProxy
import kotlinx.android.synthetic.main.frienddtlayout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.getjwt
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.R
import study.kotin.my.find.adapter.FriendDtadapter
import study.kotin.my.find.data.ConmentData
import study.kotin.my.find.data.Getfriendcicledata
import study.kotin.my.find.injection.commponent.DaggerFindCommponent
import study.kotin.my.find.injection.module.findmodule
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.presenter.friendsCirclePresenter
import study.kotin.my.find.presenter.view.friendsCircleView
import study.kotin.my.find.ui.frament.FriendDtFrament

class FriendDtActivity : BaseMVPActivity<friendsCirclePresenter>(), View.OnClickListener, friendsCircleView {
    override fun addlike(t: BaseResp<String>,goodcount: TextView) {
        if (t.success) {
         if(t.message=="成功"){
             goodcount.text=(goodcount.text.toString().toInt()+1).toString()
         }else{
             goodcount.text=(goodcount.text.toString().toInt()-1).toString()
         }
        }else{
            toast("点赞失败")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun addConment(t: BaseResp<String>, LinearLayout: LinearLayout, content:ConmentData, type:Int) {
        if(t.success){
            val textView = TextView(this)
            val string = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("myname", "")
            if(type==0){
                textView.text="${string} : ${content.content}"
            }else{
                textView.text="${string} 回复 ${content.hfname} : ${content.content}"
            }
            LinearLayout.addView(textView)
            toast("评论成功")
        }else{
            toast("评论失败")
        }

    }

    override fun getfriendcicle(t: List<Getfriendcicledata>) {
        friendDtadapter.addData(t)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.more -> {
                FriendDtFrament().show(supportFragmentManager, "1")
            }
        }
    }

    val jwt by lazy { getjwt() }
    lateinit var friendDtadapter: FriendDtadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frienddtlayout)
        initinject()
        more.setOnClickListener(this)
        friendDtadapter = FriendDtadapter(this@FriendDtActivity, ArrayList())
        val textView = TextView(this)
        textView.gravity=Gravity.CENTER
        textView.text="没有更多动态"
        friendDtadapter.setEmptyView(textView)
        rc.adapter = friendDtadapter
        rc.layoutManager = LinearLayoutManager(this)
        mpersenter.getfriendcicle(jwt!!, 1, 5)
        friendDtadapter.setOnItemChildClickListener { adapter, view, position ->
            val data = adapter.data as List<Getfriendcicledata>
            when (view.id) {
                R.id.good -> {
                    val viewByPosition = adapter.getViewByPosition(rc, position, R.id.goodcount) as TextView
                    mpersenter.addlike(jwt!!, data[position].circle.id,viewByPosition)
                }
                R.id.comment->{
                    val LinearLayout = adapter.getViewByPosition(rc, position, R.id.commentcontent)as LinearLayout
                    val Dia = Dialog(this@FriendDtActivity,R.style.ActionSheetDialogStyle)
                    val inflate = layoutInflater.inflate(R.layout.dialogitem, null)
                    val find = inflate.find<EditText>(R.id.tttt)
                    inflate.find<Button>(R.id.bbbb).setOnClickListener {
                        mpersenter.addConment(jwt!!,LinearLayout,ConmentData(find.text.toString(),data[position].circle.id,0,"",""))
                        Dia.dismiss()
                    }
                    Dia.setContentView(inflate)
                    val window = Dia.window
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    window.setGravity(Gravity.BOTTOM)
                    Dia.show()

                }
            }

        }
    }

    fun initinject() {
        DaggerFindCommponent.builder().activityCommpoent(activityCommpoent).findmodule(findmodule()).build().inject(this)
        mpersenter.mView = this
    }
}