package study.kotin.my.address.ui.activity

import android.os.Bundle
import android.view.View
import com.tencent.imsdk.TIMGroupManager
import com.tencent.imsdk.TIMGroupMemberInfo
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMValueCallBack
import kotlinx.android.synthetic.main.addgrouplayout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.address.Addresspersenter.Addresspresenter
import study.kotin.my.address.R
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
           //     createGroupParam.setCustomInfo("school",school.text.toString().toByteArray())
                val info=TIMGroupMemberInfo(TIMManager.getInstance().loginUser)
                createGroupParam.setMembers(arrayListOf(info))
                TIMGroupManager.getInstance().createGroup(createGroupParam, object : TIMValueCallBack<String> {
                    override fun onSuccess(p0: String?) {
                        toast("创建成功,社团id:$p0")
                        startActivity<PublicGroupActivity>()
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
        setContentView(R.layout.addgrouplayout)
        newgroupbutton.setOnClickListener(this)
        grouphead.setOnClickListener(this)

    }
}