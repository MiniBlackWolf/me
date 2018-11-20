package study.kotin.my.address.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.CheckBox
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import kotlinx.android.synthetic.main.friendlistlayout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.address.adapter.FriendListadapter
import study.kotin.my.address.data.UserInfoData
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class FriendActivity:BaseMVPActivity<Addresspresenter>(),View.OnClickListener {
    lateinit var ap:FriendListadapter
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.inifd->{
                val userlist=ArrayList<TIMGroupMemberInfo>()
                for (i in 0 until ap.data.size){
                    val checkBox = ap.getViewByPosition(myfriendlist, i, R.id.checkBox) as CheckBox
                    if(checkBox.isChecked){
                        userlist.add(TIMGroupMemberInfo(ap.data[i].userid))
                    }
                }
                val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
               val name = sharedPreferences.getString("myname", "")
                val createGroupParam = TIMGroupManager.CreateGroupParam("ChatRoom", name!!+"的聊天室")
                createGroupParam.setFaceUrl("")
                createGroupParam.setMaxMemberNum(100)
                //     createGroupParam.setCustomInfo("school",school.text.toString().toByteArray())
                val info= TIMGroupMemberInfo(TIMManager.getInstance().loginUser)
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
                                edit.apply()
                            }

                            override fun onError(p0: Int, p1: String?) {
                            }

                        })
                        startActivity<AddressActivity>()
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
        TIMFriendshipManagerExt.getInstance().getFriendList(object: TIMValueCallBack<MutableList<TIMUserProfile>>{
            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                val list= ArrayList<UserInfoData>()
                if (p0==null)return
                for (p in p0){
                    list.add( UserInfoData(p.faceUrl,p.nickName,p.identifier))
                }
                 ap=FriendListadapter(list)
                myfriendlist.adapter=ap
                myfriendlist.layoutManager=LinearLayoutManager(this@FriendActivity)
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
        inifd.setOnClickListener(this)
    }
}