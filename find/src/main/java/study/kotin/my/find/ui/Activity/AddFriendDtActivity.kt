package study.kotin.my.find.ui.Activity

import android.os.Bundle
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.R
import study.kotin.my.find.presenter.Findpresenter


class AddFriendDtActivity:BaseMVPActivity<Findpresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addfrienddtlatout)
    }
}