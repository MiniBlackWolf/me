package study.kotin.my.address.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.tencent.imsdk.TIMGroupManager
import com.tencent.imsdk.TIMGroupMemberInfo
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import kotlinx.android.synthetic.main.addgrouplayout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class AddGroupActivity : BaseMVPActivity<Addresspresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.grouphead -> {
            }
            R.id.newgroupbutton -> {
                val createGroupParam = TIMGroupManager.CreateGroupParam("Public", groupname.text.toString())
                createGroupParam.setFaceUrl("")
                createGroupParam.setMaxMemberNum(100)
                createGroupParam.setCustomInfo("school", school.text.toString().toByteArray())
                val info = TIMGroupMemberInfo(TIMManager.getInstance().loginUser)
                createGroupParam.setMembers(arrayListOf(info))
                TIMGroupManager.getInstance().createGroup(createGroupParam, object : TIMValueCallBack<String> {
                    override fun onSuccess(p0: String?) {
                        toast("创建成功,社团id:$p0")
                        TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(p0), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                            override fun onSuccess(p1: MutableList<TIMGroupDetailInfo>?) {
                                if (p1?.size == null) return
                                val edit = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                                edit.putString(p0 + "Gname", p1.get(0).groupName)
                                edit.putString(p0 + "Gheadurl", p1.get(0).faceUrl)
                                edit.putString(p0+"Gtype",p1[0].groupType)
                                edit.apply()
                            }

                            override fun onError(p0: Int, p1: String?) {
                            }

                        })
                        finish()
                        ActivityUtils.finishActivity(PublicGroupActivity::class.java)
                        startActivity<PublicGroupActivity>()

                    }

                    override fun onError(p0: Int, p1: String?) {
                        toast("创建失败:$p1")
                    }
                })
            }
            R.id.fh->{
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addgrouplayout)
        newgroupbutton.setOnClickListener(this)
        grouphead.setOnClickListener(this)
        fh.setOnClickListener(this)
    }
}