package study.kotin.my.find.ui.Activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.frienddtlayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.ui.frament.FriendDtFrament

class FriendDtActivity:BaseMVPActivity<Findpresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.more->{
                FriendDtFrament().show(supportFragmentManager,"1")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frienddtlayout)
        more.setOnClickListener(this)
    }
}