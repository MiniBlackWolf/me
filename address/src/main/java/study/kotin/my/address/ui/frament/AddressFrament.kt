package study.kotin.my.address.ui.frament

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
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
import androidx.core.view.isVisible
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.group.TIMGroupPendencyGetParam
import com.tencent.imsdk.ext.group.TIMGroupPendencyHandledStatus
import com.tencent.imsdk.ext.group.TIMGroupPendencyListGetSucc
import com.tencent.imsdk.ext.sns.TIMFriendFutureMeta
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import com.tencent.imsdk.ext.sns.TIMGetFriendFutureListSucc
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.addresshead.*
import kotlinx.android.synthetic.main.newfriend.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import study.kotin.my.address.Addresslistadapter
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.data.AddressListLv0
import study.kotin.my.address.data.AddressListLv1
import study.kotin.my.address.data.addnewFDdata
import study.kotin.my.address.injection.commponent.DaggerAddressCommponent
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.address.ui.activity.AddressActivity
import study.kotin.my.address.ui.activity.NewFriendActivity
import study.kotin.my.address.ui.activity.PublicGroupActivity
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import java.util.*

class AddressFrament : BaseMVPFragmnet<Addresspresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (TIMManager.getInstance().loginUser == "") {
            toast("请先登录")
            ARouter.getInstance().build("/usercenter/RegisterActivity").navigation()
            return
        }
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
      //  generateData()
        Bus.observe<Int>()
                .subscribe { t: Int ->
                    newfdtips.isVisible=true
                    newfdtips.text=(newfdtips.text.toString().toInt()+t).toString()
                }.registerInBus(this)
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
                    if (p0.get(j).remark == "") {
                        lv1 = AddressListLv1(p0[j].faceUrl, p0.get(j).nickName, p0[j].identifier)
                    } else {
                        lv1 = AddressListLv1(p0[j].faceUrl, p0.get(j).remark, p0[j].identifier)
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

    lateinit var newfdtips:TextView
    fun showview(lsit: ArrayList<MultiItemEntity>) {
        val addresslistadapter = Addresslistadapter(lsit)
        addresslistadapter.setOnItemChildClickListener { adapter, view, position ->
            val fdid = (adapter.data[position] as AddressListLv1).id
            ARouter.getInstance().build("/home/PersonalhomeActivity").withString("id", fdid).navigation()
        }
        addresslistadapter.expandAll()
        addresslistadapter.addHeaderView(activity!!.layoutInflater.inflate(R.layout.addresshead, null))
        addresslistadapter.headerLayout.findViewById<LinearLayout>(R.id.groupjojn).setOnClickListener(this)
        addresslistadapter.headerLayout.findViewById<LinearLayout>(R.id.Friendjoin).setOnClickListener(this)
        addresslistadapter.headerLayout.findViewById<LinearLayout>(R.id.publicgroupjoin).setOnClickListener(this)
        newfdtips = addresslistadapter.headerLayout.findViewById<TextView>(R.id.newfdtips)
        newfdtips.isVisible = newfdtips.text.toString() != "0"
        val recyclerView = view!!.find<RecyclerView>(R.id.addressgrouplist)
        recyclerView.adapter = addresslistadapter
        recyclerView.layoutManager = LinearLayoutManager(mpersenter.context)
        getnoreadmsgcount()
    }
    //新消息小红点
    fun getnoreadmsgcount(){
        TIMFriendshipManagerExt.getInstance().getFutureFriends(TIMFriendshipManager.TIM_PROFILE_FLAG_NICK.toLong(),
                TIMFriendshipManagerExt.TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE.toLong(), null, TIMFriendFutureMeta(),
                object : TIMValueCallBack<TIMGetFriendFutureListSucc> {
                    override fun onSuccess(p0: TIMGetFriendFutureListSucc) {
                        val size = p0.items.size
                        newfdtips.text = (newfdtips.text.toString().toInt()+size).toString()
                        if(newfdtips.text.toString().toInt()!=0){
                            newfdtips.isVisible=true
                        }
                    }

                    override fun onError(p0: Int, p1: String?) {
                    }
                })
        val param = TIMGroupPendencyGetParam()
        val nextStartTimestamp = activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getLong("nextStartTimestamp", 0L)
        param.setTimestamp(nextStartTimestamp)//首次获取填 0
        param.setNumPerPage(9999)
        TIMGroupManagerExt.getInstance().getGroupPendencyList(param, object : TIMValueCallBack<TIMGroupPendencyListGetSucc> {
            override fun onError(code: Int, desc: String) {

            }

            override fun onSuccess(timGroupPendencyListGetSucc: TIMGroupPendencyListGetSucc) {
                if (timGroupPendencyListGetSucc.pendencyMeta.nextStartTimestamp.toInt() != 0) {
                    val edit = activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                    edit.putLong("nextStartTimestamp", timGroupPendencyListGetSucc.pendencyMeta.nextStartTimestamp)
                    edit.apply()
                }
                val pendencyItems = timGroupPendencyListGetSucc.pendencies
                var Gcount=0
                for (item in pendencyItems) {
                    if (item.handledStatus == TIMGroupPendencyHandledStatus.NOT_HANDLED) {
                        Gcount++
                    }
                }

                newfdtips.text=(newfdtips.text.toString().toInt()+Gcount).toString()
                if(newfdtips.text.toString().toInt()!=0){
                    newfdtips.isVisible=true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (TIMManager.getInstance().loginUser == "") {
            showview(ArrayList())
        } else {
            generateData()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            //获取图片路径
            if(data==null){
                return
            }
            val obtainPathResult = Matisse.obtainPathResult(data)
            val bitmap = BitmapFactory.decodeFile(obtainPathResult.get(0))
            //mEditor.insertImage(obtainPathResult[0], "huangxiaoguo\" style=\"max-width:100%")
            Log.d("Matisse", "mSelected: $obtainPathResult")
        }
    }
}