package study.kotin.my.address.ui.frament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import kotlinx.android.synthetic.main.addresshead.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import study.kotin.my.address.Addresslistadapter
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.data.AddressListLv0
import study.kotin.my.address.data.AddressListLv1
import study.kotin.my.address.injection.commponent.DaggerAddressCommponent
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.address.ui.activity.AddressActivity
import study.kotin.my.address.ui.activity.NewFriendActivity
import study.kotin.my.address.ui.activity.PublicGroupActivity
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import java.util.*

class AddressFrament : BaseMVPFragmnet<Addresspresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.groupjojn -> startActivity<AddressActivity>()
            R.id.Friendjoin -> {
                startActivity<NewFriendActivity>()
            }
            R.id.publicgroupjoin -> {
                startActivity<PublicGroupActivity>()
            }
            R.id.add -> {
                ARouter.getInstance().build("/home/searchactivity").navigation()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initinject()
        val view = inflater.inflate(R.layout.addresspasge, container, false)
        // iniview(view)
        view.find<TextView>(R.id.add).setOnClickListener(this)
        generateData()
        return view
    }

    //注入
    fun initinject() {
        DaggerAddressCommponent.builder().activityCommpoent(mActivityComponent).addressmodule(Addressmodule()).build().inject(this)

    }
//
//   fun iniview(view:View){
//
//
//   }

    private fun generateData() {
        TIMFriendshipManagerExt.getInstance().getFriendList(object : TIMValueCallBack<MutableList<TIMUserProfile>> {
            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                //        val lv0Count = 3
                val lv1Count = p0!!.size
                val lv0name = arrayOf("我的好友")
                val res = ArrayList<MultiItemEntity>()
                //我的好友
                val lv0 = AddressListLv0(lv0name[0], p0.size)
                for (j in 0 until lv1Count) {
                    val lv1: AddressListLv1
                    if (p0.get(j).remark == null) {
                        lv1 = AddressListLv1("", p0.get(j).nickName,p0[j].identifier)
                    }
                    else {
                        lv1 = AddressListLv1("", p0.get(j).remark,p0[j].identifier)
                    }
                    lv0.addSubItem(lv1)
                }
                res.add(lv0)
                showview(res)
            }

            override fun onError(p0: Int, p1: String?) {
                Log.i("eeeeee", p1)
            }
        })


    }

    fun showview(lsit: ArrayList<MultiItemEntity>) {
        val addresslistadapter = Addresslistadapter(lsit)
        addresslistadapter.setOnItemChildClickListener { adapter, view, position ->
           val fdid= ( adapter.data[position] as AddressListLv1).id
            ARouter.getInstance().build("/home/PersonalhomeActivity").withString("id",fdid).navigation()
        }
        addresslistadapter.expandAll()
        addresslistadapter.addHeaderView(activity!!.layoutInflater.inflate(R.layout.addresshead, null))
        addresslistadapter.headerLayout.findViewById<LinearLayout>(R.id.groupjojn).setOnClickListener(this)
        addresslistadapter.headerLayout.findViewById<LinearLayout>(R.id.Friendjoin).setOnClickListener(this)
        addresslistadapter.headerLayout.findViewById<LinearLayout>(R.id.publicgroupjoin).setOnClickListener(this)
        val recyclerView = view!!.find<RecyclerView>(R.id.addressgrouplist)
        recyclerView.adapter = addresslistadapter
        recyclerView.layoutManager = LinearLayoutManager(mpersenter.context)
    }

}