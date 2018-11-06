package study.kotin.my.address.ui.frament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.tencent.imsdk.TIMUserProfile
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import org.jetbrains.anko.find
import study.kotin.my.address.Addresslistadapter
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.data.AddressListLv0
import study.kotin.my.address.data.AddressListLv1
import study.kotin.my.address.injection.commponent.DaggerAddressCommponent
import study.kotin.my.address.injection.module.Addressmodule
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet
import java.util.*

class AddressFrament:BaseMVPFragmnet<Addresspresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initinject()
        val view = inflater.inflate(R.layout.addresspasge, container, false)
       // iniview(view)
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

    private fun generateData(){
        TIMFriendshipManagerExt.getInstance().getFriendList(object : TIMValueCallBack<MutableList<TIMUserProfile>>{
            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                val lv0Count = 3
                val lv1Count =p0!!.size
                val lv0name = arrayOf("我的好友", "其他好友", "设备")
                val res = ArrayList<MultiItemEntity>()
                for (i in 0 until lv0Count) {
                    val lv0 = AddressListLv0(lv0name[i], p0.size)
                    for (j in 0 until lv1Count) {
                        val lv1 = AddressListLv1("", p0.get(j).nickName)
                        lv0.addSubItem(lv1)
                    }
                    res.add(lv0)
                }
                showview(res)
            }

            override fun onError(p0: Int, p1: String?) {
                Log.i("eeeeee",p1)
            }
        })


    }

fun showview(lsit:ArrayList<MultiItemEntity>){
    val addresslistadapter = Addresslistadapter(lsit)
    addresslistadapter.addHeaderView(activity!!.layoutInflater.inflate(R.layout.addresshead,null))
    val recyclerView = view!!.find<RecyclerView>(R.id.addressgrouplist)
    recyclerView.adapter=addresslistadapter
    recyclerView.layoutManager=LinearLayoutManager(mpersenter.context)
}

}