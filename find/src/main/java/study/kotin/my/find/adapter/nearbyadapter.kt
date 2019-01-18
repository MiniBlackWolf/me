package study.kotin.my.find.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.find.R
import study.kotin.my.find.data.neardata

/**
 * Creat by blackwolf
 * 2019/1/18
 * system username : Administrator
 */
class nearbyadapter(data: List<neardata>) : BaseQuickAdapter<neardata, BaseViewHolder>(R.layout.nearbyitem, data) {
    override fun convert(helper: BaseViewHolder, item: neardata) {
        helper.setText(R.id.name, item.name)
        if (item.selfSignature != "") {
            helper.setText(R.id.self, item.selfSignature)
        }
        helper.setText(R.id.rang, item.distance.toInt().toString()+"m")
        when (item.sex) {
            "男" -> {
                helper.setImageResource(R.id.sex, R.drawable.men)
            }
            "女" -> {
                helper.setImageResource(R.id.sex, R.drawable.women)
            }
        }
        if (item.headUrl != "") {
            Glide.with(BaseApplication.context).load(item.headUrl).apply(RequestOptions().error(R.drawable.a4_2)).into(helper.getView(R.id.head))
        }
    }
}