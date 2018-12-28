package study.kotin.my.address.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.NetworkUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import kotlinx.android.synthetic.main.publicgroup.*
import org.jetbrains.anko.HDPI
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.adapter.PublicGroupAdapter
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

@Route(path = "/address/PublicGroupActivity")
class PublicGroupActivity:BaseMVPActivity<Addresspresenter>(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.newgroup->startActivity<AddGroupActivity>()
            R.id.fh->finish()
            R.id.seach->{
                ARouter.getInstance().build("/home/searchactivity").navigation()
            }

        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgroup)
        newgroup.setOnClickListener(this)
        fh.setOnClickListener(this)
        seach.setOnClickListener(this)
        showLoading()
        val connected = NetworkUtils.isConnected()
        if(!connected){
            toast("没有网络!")
            hideLoading()
            return
        }
        //自己的社团
        showmygrouplist()

    }

    private fun showmygrouplist() {

        TIMGroupManagerExt.getInstance().getGroupList(object : TIMValueCallBack<MutableList<TIMGroupBaseInfo>> {
            override fun onSuccess(p0: MutableList<TIMGroupBaseInfo>?) {
                hideLoading()
                if (p0?.size == null) return
                val iterator = p0.iterator()
                while (iterator.hasNext()) {
                    if (iterator.next().groupType == "Private") {
                        iterator.remove()
                    }
                }
                val publicGroupAdapter = PublicGroupAdapter(this@PublicGroupActivity,p0)
                mygroup.adapter = publicGroupAdapter
                mygroup.layoutManager = LinearLayoutManager(this@PublicGroupActivity)
                publicGroupAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
                    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                        if (adapter?.data?.size == null) return
                        ARouter.getInstance().build("/home/HomeActivity").withString("id", (adapter.data[position] as TIMGroupBaseInfo).groupId).navigation()

                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {
                toast("请检查网络连接")
                hideLoading()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        showmygrouplist()

    }

}