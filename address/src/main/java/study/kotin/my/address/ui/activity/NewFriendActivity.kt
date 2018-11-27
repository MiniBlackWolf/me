package study.kotin.my.address.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.sns.TIMFriendFutureMeta
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import com.tencent.imsdk.ext.sns.TIMGetFriendFutureListSucc
import kotlinx.android.synthetic.main.newfriend.*
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.adapter.NewFriendAdapter
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class NewFriendActivity : BaseMVPActivity<Addresspresenter>(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.adds -> {
                ARouter.getInstance().build("/home/searchactivity").navigation()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newfriend)
        adds.setOnClickListener(this)
        TIMFriendshipManagerExt.getInstance().getFutureFriends(TIMFriendshipManager.TIM_PROFILE_FLAG_NICK.toLong(),
                TIMFriendshipManagerExt.TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE.toLong(), null, TIMFriendFutureMeta(),
                object : TIMValueCallBack<TIMGetFriendFutureListSucc> {
                    override fun onSuccess(p0: TIMGetFriendFutureListSucc?) {
                        val newFriendAdapter = NewFriendAdapter(p0!!.items)
                        myfdadd.adapter=newFriendAdapter
                        myfdadd.layoutManager=LinearLayoutManager(this@NewFriendActivity)
                    }

                    override fun onError(p0: Int, p1: String?) {
                    }
                })
    }
}