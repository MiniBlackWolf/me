package com.example.home.Utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichTextConfig
import com.zzhoujay.richtext.callback.ImageGetter
import com.zzhoujay.richtext.callback.ImageLoadNotify
import kotlinx.android.synthetic.main.chatlayout.*

class MyImageGetter(val context: Context) : ImageGetter {
    override fun registerImageLoadNotify(imageLoadNotify: ImageLoadNotify?) {

    }

    override fun getDrawable(holder: ImageHolder?, config: RichTextConfig?, textView: TextView?): Drawable {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(holder!!.source, options)
        val ratio = Math.max(options.outWidth * 1.0 / 1024f, options.outHeight * 1.0 / 1024f)
        options.inSampleSize = Math.ceil(ratio).toInt()
        options.inJustDecodeBounds = false
        val photoImg = BitmapFactory.decodeFile(holder.source, options)
        return BitmapDrawable(photoImg)
    }

    override fun recycle() {
    }
}