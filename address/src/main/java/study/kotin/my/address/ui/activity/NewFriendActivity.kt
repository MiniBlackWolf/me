package study.kotin.my.address.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMFriendGenderType
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.*
import kotlinx.android.synthetic.main.newfriend.*
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.adapter.NewFriendAdapter
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.tencent.imsdk.ext.sns.*
import org.jetbrains.anko.toast
import study.kotin.my.address.data.addnewFDdata


class NewFriendActivity : BaseMVPActivity<Addresspresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.adds -> {
                ARouter.getInstance().build("/home/searchactivity").navigation()
            }
            R.id.fh -> finish()
        }
    }

    lateinit var newFriendAdapter: NewFriendAdapter
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newfriend)
        adds.setOnClickListener(this)
        fh.setOnClickListener(this)
        newFriendAdapter = NewFriendAdapter(ArrayList())
        myfdadd.adapter = newFriendAdapter
        myfdadd.layoutManager = LinearLayoutManager(this@NewFriendActivity)
        //更新Ui
        updataview()

        newFriendAdapter.onItemChildClickListener = object :BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val data = adapter!!.data as List<addnewFDdata>
                when (view!!.id) {
                    R.id.msg->{
                        val msg = adapter.getViewByPosition(myfdadd, position, R.id.msg) as TextView
                        val tag = msg.tag
                        if(msg.tag as Boolean){
                            msg.setSingleLine(true)
                            msg.tag = false
                        }else{
                            msg.setSingleLine(false)
                            msg.tag = true
                        }

                    }
                    R.id.headurl -> {
                        ARouter.getInstance().build("/home/PersonalhomeActivity").withString("id", data[position].id).navigation()
                    }
                    R.id.acc -> {
                        when (data[position].type) {
                            0 -> {
                                val timFriendAddResponse = TIMFriendAddResponse(data[position].id)
                                timFriendAddResponse.setType(TIMFriendResponseType.AgreeAndAdd)
                                TIMFriendshipManagerExt.getInstance().addFriendResponse(timFriendAddResponse, object : TIMValueCallBack<TIMFriendResult> {
                                    override fun onSuccess(p0: TIMFriendResult?) {
                                        updataview()

                                        toast("操作成功!")
                                    }

                                    override fun onError(p0: Int, p1: String?) {
                                    }
                                })
                            }
                            1 -> {
                                data[position].TIMGroupPendencyItem!!.accept("接受", object : TIMCallBack {
                                    override fun onSuccess() {
                                        updataview()
                                        //   upread(data, position)
                                        toast("操作成功!")
                                    }

                                    override fun onError(p0: Int, p1: String?) {
                                        //   upread(data, position)
                                    }
                                })
                            }
                        }

                    }
                    R.id.refuse -> {
                        when (data[position].type) {
                            0 -> {
                                val timFriendAddResponse = TIMFriendAddResponse(data[position].id)
                                timFriendAddResponse.setType(TIMFriendResponseType.Reject)
                                TIMFriendshipManagerExt.getInstance().addFriendResponse(timFriendAddResponse, object : TIMValueCallBack<TIMFriendResult> {
                                    override fun onSuccess(p0: TIMFriendResult?) {
                                        updataview()
                                        toast("操作成功!")
                                    }

                                    override fun onError(p0: Int, p1: String?) {
                                    }
                                })
                            }
                            1 -> {

                                data[position].TIMGroupPendencyItem!!.refuse("拒绝", object : TIMCallBack {
                                    override fun onSuccess() {
                                        updataview()
                                        // upread(data, position)
                                        toast("操作成功!")
                                    }

                                    override fun onError(p0: Int, p1: String?) {
                                        //   upread(data, position)
                                    }
                                })
                            }
                        }
                    }
                }

            }


        }

    }

    private fun upread(data: List<addnewFDdata>, position: Int) {
        TIMGroupManagerExt.getInstance().reportGroupPendency(data[position].addtime, object : TIMCallBack {
            override fun onError(p0: Int, p1: String?) {

            }

            override fun onSuccess() {
            }
        })
    }

    private fun updataview() {
        newFriendAdapter.data.clear()
        myfdadd.removeAllViewsInLayout()
        TIMFriendshipManagerExt.getInstance().getFutureFriends(TIMFriendshipManager.TIM_PROFILE_FLAG_NICK.toLong(),
                TIMFriendshipManagerExt.TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE.toLong(), null, TIMFriendFutureMeta(),
                object : TIMValueCallBack<TIMGetFriendFutureListSucc> {
                    override fun onSuccess(p0: TIMGetFriendFutureListSucc) {
                        val List = java.util.ArrayList<addnewFDdata>()
                        for (p in p0.items) {
                            val addnewFDdata = addnewFDdata(0,"" ,p.profile.gender, p.identifier, p.profile.nickName, p.profile.faceUrl, p.addWording, p.addTime, null)
                            List.add(addnewFDdata)
                        }
                        newFriendAdapter.addData(List)
                    }

                    override fun onError(p0: Int, p1: String?) {
                    }
                })
        val param = TIMGroupPendencyGetParam()
        val nextStartTimestamp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getLong("nextStartTimestamp", 0L)
        param.setTimestamp(nextStartTimestamp)//首次获取填 0
        param.setNumPerPage(100)
        TIMGroupManagerExt.getInstance().getGroupPendencyList(param, object : TIMValueCallBack<TIMGroupPendencyListGetSucc> {
            override fun onError(code: Int, desc: String) {

            }

            override fun onSuccess(timGroupPendencyListGetSucc: TIMGroupPendencyListGetSucc) {
                if (timGroupPendencyListGetSucc.pendencyMeta.nextStartTimestamp.toInt() != 0) {
                    val edit = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                    edit.putLong("nextStartTimestamp", timGroupPendencyListGetSucc.pendencyMeta.nextStartTimestamp)
                    edit.apply()
                }
                val List = java.util.ArrayList<addnewFDdata>()
                val pendencyItems = timGroupPendencyListGetSucc.pendencies
                for (item in pendencyItems) {
                    if (item.handledStatus != TIMGroupPendencyHandledStatus.NOT_HANDLED) {
                        continue
                    }
                    val addnewFDdata = addnewFDdata(1,item.groupId, TIMFriendGenderType.Unknow, item.fromUser, "", "", item.requestMsg, item.addTime, item)
                    List.add(addnewFDdata)
                }
                newFriendAdapter.addData(List)
            }
        })
    }
}