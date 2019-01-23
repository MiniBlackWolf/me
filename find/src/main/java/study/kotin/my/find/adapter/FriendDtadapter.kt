package study.kotin.my.find.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.support.v7.widget.GridLayout
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.addfrienddtlatout.*
import org.jetbrains.anko.find
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.getjwt
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.presenter.view.BaseView
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.find.R
import study.kotin.my.find.data.ConmentData
import study.kotin.my.find.data.Getfriendcicledata
import study.kotin.my.find.presenter.friendsCirclePresenter

/**
 * Creat by blackwolf
 * 2019/1/23
 * system username : Administrator
 */
class FriendDtadapter(val context: BaseMVPActivity<friendsCirclePresenter>,data:List<Getfriendcicledata>):BaseQuickAdapter<Getfriendcicledata,BaseViewHolder>(R.layout.frienddtitem,data) {
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: Getfriendcicledata) {
        helper.setText(R.id.name,item.circle.username)
        helper.setText(R.id.content,item.circle.content)
        val substring = item.circle.createtime.substring(0, item.circle.createtime.indexOf("."))
        val replace = substring.replace("T", " ")
        helper.setText(R.id.time,replace)
        helper.setText(R.id.content,item.circle.content)
        helper.addOnClickListener(R.id.comment)
        helper.addOnClickListener(R.id.good)
        addimg(item, helper)
        helper.setText(R.id.goodcount,item.circle.likeCount.toString())
        helper.setText(R.id.commentcount,item.conment.size.toString())
        Glide.with(BaseApplication.context).load(item.circle.headimg).apply(RequestOptions().error(R.drawable.a4_2)).into(helper.getView<ImageView>(R.id.head))
        for(i in item.conment){
            val textView = TextView(context)
            if(i.conmentId==0){
                textView.text="${i.uname} : ${i.content}"
                textView.setOnClickListener {
                    val Dia = Dialog(context,R.style.ActionSheetDialogStyle)
                    val inflate = context.layoutInflater.inflate(R.layout.dialogitem, null)
                    val find = inflate.find<EditText>(R.id.tttt)
                    inflate.find<Button>(R.id.bbbb).setOnClickListener {
                        context.mpersenter.addConment(context.getjwt()!!,helper.getView<LinearLayout>(R.id.commentcontent), ConmentData(find.text.toString(),i.fcmid,i.id,i.uname,i.uid))
                        Dia.dismiss()
                    }
                    Dia.setContentView(inflate)
                    val window = Dia.window
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    window.setGravity(Gravity.BOTTOM)
                    Dia.show()
                }
            }else{
                textView.text="${i.uname} 回复 ${i.hfname} : ${i.content}"
            }
            helper.getView<LinearLayout>(R.id.commentcontent).addView(textView)
        }
    }


    private fun addimg(item: Getfriendcicledata, helper: BaseViewHolder) {
        var picture = item.circle.picture
        picture = picture.replace("[", "")
        picture = picture.replace("]", "")
        val split = picture.split(",")
        for (p in split) {
            val imageView = ImageView(context)
            val layoutParams = LinearLayout.LayoutParams(210, 210)
            imageView.layoutParams = layoutParams
            Glide.with(BaseApplication.context).load(p.trim()).apply(RequestOptions().error(R.drawable.a4_2)).into(imageView)
            helper.getView<GridLayout>(R.id.imags).addView(imageView)
        }
    }
}