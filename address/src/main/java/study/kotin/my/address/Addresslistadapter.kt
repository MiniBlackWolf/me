package study.kotin.my.address

import android.animation.ObjectAnimator
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import study.kotin.my.address.data.AddressListLv0
import study.kotin.my.address.data.AddressListLv1
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.common.CircleImageView


class Addresslistadapter(data: List<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(TYPE_LEVEL_0, R.layout.addressitem0)
        addItemType(TYPE_LEVEL_1, R.layout.addressitem1)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when (helper!!.itemViewType) {
            TYPE_LEVEL_0 -> {
                val lv0 = item as AddressListLv0
                helper.setText(R.id.groupname, lv0.groupname)
                helper.setText(R.id.fdcount, "共${lv0.fdcount}人")
                if(lv0.fdcount==0){
                    return
                }
                helper.itemView.setOnClickListener {
                    val pos = helper.adapterPosition
                    Log.d(TAG, "Level 0 item pos: " + pos)
                    if (lv0.isExpanded) {
//                        val ofFloat = ObjectAnimator.ofFloat(helper.getView<ImageView>(R.id.groupimg), "rotationX", 180f, 0f)
//                        ofFloat.setDuration(200)
//                        ofFloat.start()
                        collapse(pos)
                    } else {
//                        val ofFloat = ObjectAnimator.ofFloat(helper.getView<ImageView>(R.id.groupimg), "rotationX", 0f, 180f)
//                        ofFloat.setDuration(200)
//                        ofFloat.start()
                        expand(pos)

                    }
                }
            }
            TYPE_LEVEL_1 -> {
                val lv1 = item as AddressListLv1
                helper.setText(R.id.fdname, lv1.name)
                helper.addOnClickListener(R.id.thissd)
                val headurl = helper.getView<CircleImageView>(R.id.headurl)
                val options = RequestOptions()
                        .error(R.drawable.a4_2)
                Glide.with(BaseApplication.context).load(lv1.headurl).apply(options).into(headurl)
            }
        }
    }

    companion object {
        private val TAG = Addresslistadapter::class.java.simpleName
        val TYPE_LEVEL_0 = 0
        val TYPE_LEVEL_1 = 1
    }
}