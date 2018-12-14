package study.kotin.my.address.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import kotlinx.android.synthetic.main.grouplayout.*
import org.jetbrains.anko.startActivity
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.Addresspersenter.view.AddressView
import study.kotin.my.address.R
import study.kotin.my.address.adapter.PublicGroupAdapter
import study.kotin.my.address.injection.commponent.DaggerAddressCommponent
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class AddressActivity : BaseMVPActivity<Addresspresenter>(), AddressView,View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.newgroup->startActivity<FriendActivity>("type" to "creat")
            R.id.fh->finish()
        }
    }

    override fun reslut() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grouplayout)
        iniinject()
        mpersenter.ss()
        newgroup.setOnClickListener(this)
        showmygrouplist()
        fh.setOnClickListener(this)
    }

    private fun showmygrouplist() {
        TIMGroupManagerExt.getInstance().getGroupList(object : TIMValueCallBack<MutableList<TIMGroupBaseInfo>> {
            override fun onSuccess(p0: MutableList<TIMGroupBaseInfo>?) {
                if (p0?.size == null) return
                val iterator = p0.iterator()
                while (iterator.hasNext()) {
                    if (iterator.next().groupType == "Public") {
                        iterator.remove()
                    }
                }
                val publicGroupAdapter = PublicGroupAdapter(this@AddressActivity,p0)
                mygroup.adapter = publicGroupAdapter
                mygroup.layoutManager = LinearLayoutManager(this@AddressActivity)
                publicGroupAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
                    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                        if (adapter?.data?.size == null) return
                        ARouter.getInstance().build("/home/HomeActivity").withString("id", (adapter.data[position] as TIMGroupBaseInfo).groupId).navigation()
                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
    }

    private fun iniinject() {
        DaggerAddressCommponent.builder().activityCommpoent(activityCommpoent).addressmodule(Addressmodule()).build().inject(this)
        mpersenter.mView=this
    }

    override fun onResume() {
        super.onResume()
        showmygrouplist()
    }
}