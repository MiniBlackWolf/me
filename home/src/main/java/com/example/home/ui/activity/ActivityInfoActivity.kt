package com.example.home.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.home.R
import com.example.home.Utils.ImgUtils
import com.example.home.Utils.MyImageGetter
import com.example.home.data.articledata
import com.example.home.persenter.articlepersenter
import com.zzhoujay.richtext.CacheType
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import com.zzhoujay.richtext.RichTextConfig
import com.zzhoujay.richtext.callback.DrawableGetter
import com.zzhoujay.richtext.callback.ImageFixCallback
import com.zzhoujay.richtext.callback.OnImageClickListener
import kotlinx.android.synthetic.main.activityinfo.*
import kotlinx.android.synthetic.main.chatlayout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import java.lang.Exception



class ActivityInfoActivity:BaseMVPActivity<articlepersenter>(),View.OnClickListener {
    override fun onClick(v: View) {
        when(v.id){
            R.id.a3->{
                userlists.isVisible = userlists.isVisible != true
                userlist.isVisible = userlist.isVisible != true
                userlist.isFocusable = userlist.isFocusable != true
            }
            R.id.chf->{
                finish()
            }
        }
    }

    val datas by lazy { intent.extras!!.get("articledata") as articledata }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityinfo)
        initview()
        if(datas.address==null){
            z1.isVisible=false
            z2.isVisible=false
            a3.isVisible=false
        }
        myscrollview.smoothScrollTo(0,0)
        userlist.isFocusable=false
        val list=ArrayList<String>()
        if (datas.num.isEmpty()){
            userlist.text="无"
        }else {
            //list添加数据
            for (i in datas.num) {
                list.add(i.tagProfileImNick!!)
            }
            userlist.text=list.toString()
            val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
            userlists.adapter = arrayAdapter
            //转跳个人界面
            userlists.setOnItemClickListener { parent, view, position, id ->
               startActivity<PersonalhomeActivity>("id" to datas.num[position].toAccount)
            }
        }
        setdata()
        richtext()
    }


    fun setdata(){
        titles.text=datas.title
        starttime.text=datas.starttime+" --"
        endtime.text=datas.endtime
        address.text=datas.address
    }
    fun richtext(){
        RichText.initCacheDir(this)
        RichText.fromHtml(datas.content)
                .bind(this)
                .errorImage(object : DrawableGetter {
                    override fun getDrawable(holder: ImageHolder?, config: RichTextConfig?, textView: TextView?): Drawable {
                        return resources.getDrawable(R.drawable.error)
                    }
                })
                .cache(CacheType.none)
                .scaleType(ImageHolder.ScaleType.fit_xy)
                .autoFix(false)
                .fix(object : ImageFixCallback {
                    override fun onInit(holder: ImageHolder?) {
                        holder!!.isAutoFix=true
                    }

                    override fun onFailure(holder: ImageHolder?, e: Exception?) {
                    }

                    override fun onLoading(holder: ImageHolder) {

                    }

                    override fun onSizeReady(
                            holder: ImageHolder,
                            imageWidth: Int,
                            imageHeight: Int,
                            sizeHolder: ImageHolder.SizeHolder
                    ) {
                        holder.scaleType = ImageHolder.ScaleType.fit_xy
                        holder.height = imageHeight/3
                        holder.width = imageWidth/3
                        sizeHolder.setSize(imageWidth/4, imageHeight/4)
                    }

                    override fun onImageReady(holder: ImageHolder, width: Int, height: Int) {
//                        holder.scaleType = ImageHolder.ScaleType.fit_xy


//                        holder.height = height/4
//                        holder.width = width/4
                    }
                })
                .imageClick(object : OnImageClickListener {
                    override fun imageClicked(imageUrls: MutableList<String>, position: Int) {

                        Glide.with(this@ActivityInfoActivity).load(imageUrls[position]).into(object:SimpleTarget<Drawable>(){
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                val dialog = ImgUtils((resource as BitmapDrawable).bitmap, this@ActivityInfoActivity).init()
                                dialog.show()
                            }
                        })



                    }
                })
             //   .imageGetter(MyImageGetter(this))
                .into(contents)
        contents.isFocusable=false
    }

    fun initview(){
        a3.setOnClickListener(this)
        chf.setOnClickListener(this)
    }
}