package study.kotin.my.address.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.CheckBox
import com.alibaba.android.arouter.facade.annotation.Route
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.group.TIMGroupMemberResult
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import kotlinx.android.synthetic.main.friendlistlayout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.adapter.FriendListadapter
import study.kotin.my.address.data.UserInfoData
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

@Route(path = "/address/FriendActivity")
class FriendActivity : BaseMVPActivity<Addresspresenter>(), View.OnClickListener {
    lateinit var ap: FriendListadapter
    var type: String? = ""
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fh->finish()
            R.id.inifd -> {
                val userlist = ArrayList<TIMGroupMemberInfo>()
                for (i in 0 until ap.data.size) {
                    val checkBox = ap.getViewByPosition(myfriendlist, i, R.id.checkBox) as CheckBox
                    if (checkBox.isChecked) {
                        userlist.add(TIMGroupMemberInfo(ap.data[i].userid))
                    }
                }
                if(userlist.isEmpty()){
                    toast("至少选择一个好友!")
                    return
                }
                val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val name = sharedPreferences.getString("myname", "")
                val createGroupParam = TIMGroupManager.CreateGroupParam("Private", name!! + "的聊天室")
                createGroupParam.setFaceUrl("")
                createGroupParam.setMaxMemberNum(100)
                //     createGroupParam.setCustomInfo("school",school.text.toString().toByteArray())
                val info = TIMGroupMemberInfo(TIMManager.getInstance().loginUser)
                userlist.add(info)
                createGroupParam.setMembers(userlist)
                TIMGroupManager.getInstance().createGroup(createGroupParam, object : TIMValueCallBack<String> {
                    override fun onSuccess(p0: String?) {
                        toast("创建成功")
                        TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(p0), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                            override fun onSuccess(p1: MutableList<TIMGroupDetailInfo>?) {
                                if (p1?.size == null) return
                                val edit = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                                edit.putString(p0 + "Gname", p1.get(0).groupName)
                                edit.putString(p0 + "Gheadurl", p1.get(0).faceUrl)
                                edit.putString(p0 + "Gtype", p1[0].groupType)
                                edit.apply()
                            }

                            override fun onError(p0: Int, p1: String?) {
                            }

                        })
                        startActivity<AddressActivity>()
                        finish()
                    }

                    override fun onError(p0: Int, p1: String?) {
                        toast("创建失败:$p1")
                    }
                })

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friendlistlayout)
        type = intent.extras?.getString("type")
        fh.setOnClickListener(this)
        TIMFriendshipManagerExt.getInstance().getFriendList(object : TIMValueCallBack<MutableList<TIMUserProfile>> {
            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                val list = ArrayList<UserInfoData>()
                if (p0 == null) return
                for (p in p0) {
                    list.add(UserInfoData(p.faceUrl, p.nickName, p.identifier))
                }
                ap = FriendListadapter(list)
                myfriendlist.adapter = ap
                myfriendlist.layoutManager = LinearLayoutManager(this@FriendActivity)
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
        if (type == "creat") {
            inifd.text = "创建"

            inifd.setOnClickListener(this)
        } else if (type == "iniv") {
            inifd.text = "邀请"
            val Gid = intent.extras?.getString("Gid")
            inifd.setOnClickListener {
                val userlist = ArrayList<String>()
                for (i in 0 until ap.data.size) {
                    val checkBox = ap.getViewByPosition(myfriendlist, i, R.id.checkBox) as CheckBox
                    if (checkBox.isChecked) {
                        userlist.add(ap.data[i].userid)
                    }
                }
                if(userlist.isEmpty()){
                    toast("至少选择一个好友!")
                    return@setOnClickListener
                }
                TIMGroupManagerExt.getInstance().inviteGroupMember(Gid!!, userlist, object : TIMValueCallBack<MutableList<TIMGroupMemberResult>> {
                    override fun onSuccess(p0: MutableList<TIMGroupMemberResult>?) {
                        toast("邀请成功")
                        startActivity<AddressActivity>()
                        finish()
                    }

                    override fun onError(p0: Int, p1: String?) {
                        toast("邀请失败$p1")
                    }

                })
            }
        }
    }
}