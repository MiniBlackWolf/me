package com.example.home.ui.Frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.tencent.imsdk.TIMGroupMemberRoleType
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.group.TIMGroupSelfInfo
import io.reactivex.internal.operators.flowable.FlowableSequenceEqual
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.ui.fragment.BaseMVPFragmnet

class PublicGroupFarment_1 : BaseMVPFragmnet<HomePersenter>() {
    val id by lazy { activity?.intent?.extras?.getString("id") }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.publicgroupitem_1, container, false)
        val edits = view.find<TextView>(R.id.edit)
        val edoks = view.find<TextView>(R.id.edok)
        val edtxs = view.find<EditText>(R.id.edtx)
        edtxs.isEnabled  = false
        edits.setOnClickListener {
            edtxs.isEnabled = true
            edoks.isVisible = true
        }
        edoks.setOnClickListener {
            edtxs.isEnabled = false
            edoks.isVisible = false
        }
        TIMGroupManagerExt.getInstance().getSelfInfo(id!!, object : TIMValueCallBack<TIMGroupSelfInfo> {
            override fun onSuccess(p0: TIMGroupSelfInfo?) {
                if (p0 == null) return
                edits.isVisible = p0.role == TIMGroupMemberRoleType.Admin || p0.role == TIMGroupMemberRoleType.Owner

            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
        return view
    }

}