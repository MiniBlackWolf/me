package study.kotin.my.find.ui.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.tencent.imsdk.ext.sns.TIMFriendshipProxy
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import kotlinx.android.synthetic.main.addfrienddtlatout.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.data.net.RetrofitFactory
import study.kotin.my.baselibrary.ext.getjwt
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.GifSizeFilter
import study.kotin.my.find.R
import study.kotin.my.find.data.friendcicledata
import study.kotin.my.find.data.neardata
import study.kotin.my.find.injection.commponent.DaggerFindCommponent
import study.kotin.my.find.injection.module.findmodule
import study.kotin.my.find.presenter.Findpresenter
import study.kotin.my.find.presenter.view.Findview
import java.io.File


class AddFriendDtActivity : BaseMVPActivity<Findpresenter>(), Findview, View.OnClickListener {
    override fun uploadimg(t: BaseResp<String>) {
        if (t.success) {
            var replace = t.message.replace("[", "")
            replace = replace.replace("]", "")
            val split = replace.split(",")
            val getjwt = getjwt()
            val friends = TIMFriendshipProxy.getInstance().friends
            val friendsid = ArrayList<String>()
            if(friends!=null){
                for (f in friends) {
                    friendsid.add(f.identifier)
                }
            }
            mpersenter.friendcicle(getjwt!!, friendcicledata(friendsid, editText.text.toString(), split.toString()))
        } else {
            toast("图片上传失败")
            hideLoading()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.chf->{
                finish()
            }
            R.id.ok -> {
                if (editText.text.toString() == "") {
                    toast("内容不能为空")
                    return
                }
                showLoading()
                val getjwt = getjwt()
                val Builder = MultipartBody.Builder()
                Builder.setType(MultipartBody.FORM)
                for (file in pathall) {
                    val file1 = File(file)
                    val create = RequestBody.create(MediaType.parse("multipart/form-data"), file1)
                    Builder.addFormDataPart("files", file1.name, create)
                }
                val parts = Builder.build().parts()
                mpersenter.uploadimg(getjwt!!, this, parts)
            }
            R.id.img -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(8)
                        .capture(false)
                        .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                      .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(study.kotin.my.baselibrary.common.GlideEngine())
                        .forResult(1)
            }
        }
    }

    override fun friendcicle(t: BaseResp<String>) {
        hideLoading()
        if(t.success){
            toast("发布成功")
            finish()
        }else{
            toast("发布失败")
        }

    }

    override fun findByNear(t: List<neardata>) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addfrienddtlatout)
        initinject()
        initview()

    }

    val pathall = ArrayList<String>()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val obtainPathResult = Matisse.obtainPathResult(data)
            if(pathall.size>=8){
                toast("最多8张图片")
                return
            }
            pathall.addAll(obtainPathResult)
            for (path in obtainPathResult) {
                val decodeFile = BitmapFactory.decodeFile(path)
                val imageView = ImageView(this)
                val layoutParams = LinearLayout.LayoutParams(210, 210)
                imageView.layoutParams=layoutParams
                imageView.setImageBitmap(decodeFile)
                moreimg.addView(imageView)
            }

        }
    }

    fun initview() {
        ok.setOnClickListener(this)
        img.setOnClickListener(this)
        chf.setOnClickListener(this)
    }

    fun initinject() {
        DaggerFindCommponent.builder().activityCommpoent(activityCommpoent).findmodule(findmodule()).build().inject(this)
        mpersenter.mView = this
    }
}