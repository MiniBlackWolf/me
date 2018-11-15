package com.example.home.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.home.HomeAdapter.chatadapter
import com.example.home.R
import com.example.home.common.ChatViewSet
import com.example.home.common.Msg
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import com.example.home.persenter.HomePersenter
import com.example.home.persenter.view.HomeView
import kotlinx.android.synthetic.main.chatlayout.*
import study.kotin.my.mycenter.injection.commponent.DaggerHomeCommponent
import study.kotin.my.mycenter.injection.module.Homemodule
import kotlinx.android.synthetic.main.chatitem2.*
import study.kotin.my.baselibrary.common.BaseApplication
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.home.HomeAdapter.emoji
import com.example.home.Messges.SendImgMsg
import com.example.home.Messges.SoundMessge
import com.example.home.Utils.GifSizeFilter
import com.example.home.Utils.ImgUtils
import com.example.home.common.itemclickListener
import com.example.home.data.Sounddata
import com.example.home.data.longtimedata
import com.oden.syd_camera.SydCameraActivity
import com.oden.syd_camera.camera.CameraParaUtil
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMManagerExt
import com.tencent.imsdk.ext.message.TIMMessageExt
import com.tencent.qcloud.ui.VoiceSendingView
import com.zhihu.matisse.filter.Filter
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import study.kotin.my.baselibrary.ext.getsoundtime
import study.kotin.my.baselibrary.utils.EmoticonUtil
import study.kotin.my.baselibrary.utils.FileUtil
import study.kotin.my.baselibrary.utils.MediaUtil
import study.kotin.my.baselibrary.utils.SoftKeyBoardListener
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


@Suppress("DEPRECATION")
class HomeActivity : BaseMVPActivity<HomePersenter>(), HomeView, View.OnClickListener {
    val SEND_MSG_TYPE = 1
    val SHOW_MSG_TYPE = 0
    lateinit var id: String
    lateinit var chatadapter: chatadapter

    //发送语音消息
    override fun sendSoundmsg() {
        val audiosh = getSharedPreferences("sp_name_audio", MODE_PRIVATE)
        val adpath = audiosh.getString("audio_path", "")
        val adtime = audiosh.getLong("elpased", -1)
        if (adpath != "" && adtime != -1L) {
            val TIMSoundElem = TIMSoundElem()
            TIMSoundElem.path = adpath
            mpersenter.sendmessge(id, TIMSoundElem)
            //--updataview
            updataview(Sounddata(adpath!!, adtime), SEND_MSG_TYPE, 3)

        }
    }


    //发送消息
    override fun sendmsg() {
        val timTextElem = TIMTextElem()
        timTextElem.text = chatsendview.editText.text.toString()
        mpersenter.sendmessge(id, timTextElem)
        //----updataview
        updataview(chatsendview.editText.text.toString(), SEND_MSG_TYPE, 1)
        chatsendview.editText.setText("")

    }

    //文本信息
    var msglist = ArrayList<Msg>()

    override fun showtextmsg(TIMTextElem: TIMTextElem) {
        updataview(TIMTextElem.text.toString(), SHOW_MSG_TYPE, 1)
    }

    //图片信息
    override fun showimgmsg(bitmap: Bitmap) {
        updataview(bitmap, SHOW_MSG_TYPE, 2)
    }

    //语音消息
    override fun showSoundmsg(path: String, time: Long) {
//        try {
//            MediaUtil.getInstance().play(soundfile)
//            MediaUtil.getInstance().setEventListener(object : MediaUtil.EventListener {
//                override fun onStop() {
//
//                }
//            })
//        } catch (e: Exception) {
//
//        }

//        initview()
//        showimgmsg.isVisible = true
//        msglist.add(Msg("1", 0, 2))
//        chatadapter.addData(msglist)
//        chatadapter.notifyDataSetChanged()
        updataview(Sounddata(path, time), SHOW_MSG_TYPE, 3)
    }

    //文件消息
    override fun showFilemsg(TIMFileElem: TIMFileElem) {
        updataview(TIMFileElem, SHOW_MSG_TYPE, 4)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatlayout)
        initinject()
        //用户名获取
        val extras = intent.extras
        id = extras.get("id") as String
        //对话消息页面初始化
        chatadapter = chatadapter(msglist, this)
        chatrecyclerview.adapter = chatadapter
        chatrecyclerview.layoutManager = LinearLayoutManager(BaseApplication.context)
        mpersenter.mView = this
        chatsendview.setChatView(ChatViewSet(mpersenter.mView), R.id.btn_photo, R.id.btn_image, R.id.btn_file, voiceSendingView)
        btn_photo.setOnClickListener(this)
        btn_image.setOnClickListener(this)
        btn_file.setOnClickListener(this)
        val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(chatsendview.windowToken, 0)
        chatsendview.btnAdd.setOnClickListener {
            if (morePanel.isVisible) {
                morePanel.isVisible = false
                chatsendview.layoutParams.height = 168
                val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(chatsendview.windowToken, 0)
            } else {
                morePanel.isVisible = true
                chatsendview.layoutParams.height = 168 + morePanel.layoutParams.height
                val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(chatsendview.windowToken, 0)
            }


        }
        //      getdata()
        //软键盘跟随
        //  getkeyboy()
        //消息监听器
        mpersenter.showmessge(id)
        //历史消息读取
        getlangtimemsg()
        //已读消息上报
        UpdataReadMessage()
        //事件点击方法
        chatsendview.btnEmotion.setOnClickListener{
            emoticon.isVisible=!emoticon.isVisible

        }
        chatfh.setOnClickListener{
            finish()
        }
        chatadapter.onItemChildClickListener = itemclickListener(chatrecyclerview, this@HomeActivity)
        //表情！！！！！！！！！！
        val emojiadapter=emoji(EmoticonUtil.emoticonData.toList())
        emoticon.adapter=emojiadapter
        emojiadapter.onItemClickListener=object : BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
               val text=adapter!!.getViewByPosition(emoticon,position,android.R.id.text1) as TextView
                 val index = chatsendview.editText.getSelectionStart()
                 val editable = chatsendview.editText.text
                 editable.insert(index, text.text.toString())
            }
        }
        emoticon.layoutManager=GridLayoutManager(this@HomeActivity,7)


    }

    private fun UpdataReadMessage() {
        val conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, id)
        TIMConversationExt(conversation).setReadMessage(null, object : TIMCallBack {
            override fun onSuccess() {
                Log.i("iiiii", "消息上传成功")
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e("iiiii", "消息上传失败$p1")
            }
        })
    }

    fun initinject() {
        DaggerHomeCommponent.builder().activityCommpoent(activityCommpoent).homemodule(Homemodule()).build().inject(this)

    }


//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        //以下代码为处理Android6.0、7.0动态权限所需
//        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        PermissionManager.handlePermissionsResult(this, type, TakePhotoUt.invokeParam, TakePhotoUt)
//
//    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_photo -> {
                val intent = Intent(this, SydCameraActivity::class.java)
                intent.putExtra(CameraParaUtil.picQuality, 70) //图片质量0~100
                intent.putExtra(CameraParaUtil.picWidth, 1536)  //照片最小宽度配置，高度根据屏幕比例自动配置
                intent.putExtra(CameraParaUtil.previewWidth, 1280)  //相机预览界面最小宽度配置，高度根据屏幕比例自动配置
                startActivityForResult(intent, CameraParaUtil.REQUEST_CODE_FROM_CAMERA)
            }
            R.id.btn_image -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(1)
                        .capture(false)
                        .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(GlideEngine())
                        .forResult(1)
            }
            R.id.btn_file -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(intent, 99)
            }
        }
    }

    lateinit var mSelected: List<Uri>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //获取图片路径
            mSelected = Matisse.obtainResult(data!!)
            val obtainPathResult = Matisse.obtainPathResult(data)
            val bitmap = BitmapFactory.decodeFile(obtainPathResult.get(0))
            //构筑消息
            val timImageElem = TIMImageElem()
            timImageElem.path = obtainPathResult.get(0)
            mpersenter.sendmessge(id, timImageElem)
            //显示界面
            updataview(bitmap, SEND_MSG_TYPE, 2)
            Log.d("Matisse", "mSelected: $obtainPathResult")
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("iiiiii", "拍照取消!")
            return
        }
        if (resultCode != Activity.RESULT_OK) {
            Log.w("iiiiii", "拍照失败!")
            return
        }

        if (requestCode == CameraParaUtil.REQUEST_CODE_FROM_CAMERA) {
            val picturePath = data!!.getStringExtra(CameraParaUtil.picturePath)
            val bitmap = BitmapFactory.decodeFile(picturePath)
            Log.d("iiiii", "onActivityResult picturePath: " + picturePath)
            val timimgElem = TIMImageElem()
            timimgElem.path = picturePath
            mpersenter.sendmessge(id, timimgElem)
            updataview(bitmap, SEND_MSG_TYPE, 2)
        }

        if (requestCode == 99) {
            val uri = data!!.data//得到uri，后面就是将uri转化成file的过程。
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val actualimagecursor = managedQuery(uri, proj, null, null, null)
            val actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualimagecursor.moveToFirst()
            val img_path = actualimagecursor.getString(actual_image_column_index)
            val file = File(img_path)
            val length = file.length()
            if (length > 10240) return
            val TIMFileElem = TIMFileElem()
            TIMFileElem.path = img_path
            TIMFileElem.fileName = file.name
            mpersenter.sendmessge(id, TIMFileElem)
            updataview(TIMFileElem, SEND_MSG_TYPE, 4)
            Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun getlangtimemsg() {
        val list = LinkedHashSet<longtimedata>()
        val userconversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, id)
        TIMConversationExt(userconversation).getMessage(10, null, object :
                TIMValueCallBack<MutableList<TIMMessage>> {
            override fun onSuccess(p0: MutableList<TIMMessage>?) {
                p0!!.reverse()
                loop@ for (i in 0 until p0.size) {
                    val element = p0.get(i).getElement(0)
                    when (element.type) {
                        TIMElemType.Text, TIMElemType.Face -> {
                            list.add(longtimedata((element as TIMTextElem).text, p0.get(i).timestamp(), 1, p0.get(i).isSelf))
                        }
                        TIMElemType.Image -> {
                            for (image in (element as TIMImageElem).imageList) {
                                val uuid = image.uuid
                                image.getImage(FileUtil.getCacheFilePath(uuid), object : TIMCallBack {
                                    override fun onError(code: Int, desc: String) {//获取图片失败
                                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                                        //错误码 code 含义请参见错误码表
                                        Log.e("imgs--d", "getImage failed. code: $code errmsg: $desc")
                                    }

                                    override fun onSuccess() {//成功，参数为图片数据
                                        //doSomething
                                        val bitmap = BitmapFactory.decodeFile(FileUtil.getCacheFilePath(uuid))
                                        list.add(longtimedata(bitmap, p0.get(i).timestamp(), 2, p0.get(i).isSelf))
                                        Log.d("imgs--d", "getImage success.")
                                    }
                                })
                            }

                        }
                        TIMElemType.Sound -> {
                            val string = getSharedPreferences("LongTimeData", Context.MODE_PRIVATE).getString((element as TIMSoundElem).uuid, "")
                            if (string != "") {
                                val getsoundtime = MediaPlayer().getsoundtime(string!!)
                                list.add(longtimedata(Sounddata(string, getsoundtime), p0.get(i).timestamp(), 3, p0.get(i).isSelf))
                                continue@loop
                            }

                            val tempAudio = FileUtil.getTempFile(FileUtil.FileType.AUDIO)
                            (element as TIMSoundElem).getSoundToFile(tempAudio.absolutePath, object : TIMCallBack {
                                override fun onSuccess() {
                                    val getsoundtime = MediaPlayer().getsoundtime(tempAudio.canonicalPath)
                                    list.add(longtimedata(Sounddata(tempAudio.absolutePath, getsoundtime), p0.get(i).timestamp(), 3, p0.get(i).isSelf))
                                    val edit = getSharedPreferences("LongTimeData", Context.MODE_PRIVATE).edit()
                                    edit.putString(element.uuid, tempAudio.absolutePath)
                                    edit.apply()
                                }

                                override fun onError(p0: Int, p1: String?) {

                                }
                            })

                        }
                        TIMElemType.Video -> {
                        }
                        TIMElemType.GroupTips -> return
                        //  return new GroupTipMessage(message);
                        TIMElemType.File -> {
                            list.add(longtimedata((element as TIMFileElem), p0.get(i).timestamp(), 4, p0.get(i).isSelf))
                        }
                        TIMElemType.UGC -> return
                        else -> return
                    }

                }


                Thread(object : Runnable {
                    override fun run() {
                        while (true) {
                            if (list.size == p0.size) {
                                break
                            }
                        }
                        Looper.prepare()
                        val ms = Message()
                        val bu = Bundle()
                        bu.putSerializable("list", list)
                        ms.data = bu
                        handler.sendMessage(ms)
                        Looper.loop()
                        Log.i("iiiiiiiiiiiii", list.toString())
                    }

                }).start()


            }

            override fun onError(p0: Int, p1: String?) {
                Log.i("iiiiiiiiiiiiii", p1)
            }
        })

    }

    @Suppress("UNCHECKED_CAST")
    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val data = msg!!.data
            val datalists = data.getSerializable("list") as LinkedHashSet<longtimedata>
            val datalist = datalists.toMutableList()
            val datacount = HashSet<Int>()
            for (i in 0 until datalist.size) {
                for (j in datalist.size - 1 downTo i) {
                    if (i == j) continue
                    if (datalist[i].time == datalist.get(j).time) {
                        datacount.add(j)
                    }
                }
            }
            val toMutableList = datacount.toMutableList()
            toMutableList.sort()
            for (i in toMutableList.size - 1 downTo 0) {
                datalist.removeAt(toMutableList.get(i))
            }
            datalist.sortWith(Comparator<longtimedata> { o1, o2 ->
                o1.time.compareTo(o2.time)
            })
            for (datas in datalist) {
                when (datas.type) {
                    1 -> {
                        if (datas.isseft) {
                            updataview(datas.data as String, SEND_MSG_TYPE, 1)
                        } else updataview(datas.data as String, SHOW_MSG_TYPE, 1)

                    }
                    2 -> {
                        if (datas.isseft) {
                            updataview(datas.data as Bitmap, SEND_MSG_TYPE, 2)
                        } else updataview(datas.data as Bitmap, SHOW_MSG_TYPE, 2)
                    }
                    3 -> {
                        if (datas.isseft) {
                            updataview(datas.data as Sounddata, SEND_MSG_TYPE, 3)
                        } else updataview(datas.data as Sounddata, SHOW_MSG_TYPE, 3)
                    }
                    4 -> {
                        if (datas.isseft) {
                            updataview(datas.data as TIMFileElem, SEND_MSG_TYPE, 4)
                        } else updataview(datas.data as TIMFileElem, SHOW_MSG_TYPE, 4)
                    }

                }

            }
            //   data.getSerializable()
        }
    }


    fun updataview(data: Any, Type: Int, datatype: Int) {
        val msglists = ArrayList<Msg>()
        msglists.add(Msg(data, Type, datatype))
        chatadapter.addData(msglists)
        chatadapter.notifyDataSetChanged()
        chatrecyclerview.scrollToPosition(chatadapter.itemCount - 1)

    }

    fun getkeyboy() {
        SoftKeyBoardListener.setListener(this@HomeActivity, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                val layoutParams = chatsendview.layoutParams
                layoutParams.height = 168
            }

            override fun keyBoardHide(height: Int) {
                val layoutParams = chatsendview.layoutParams
                layoutParams.height = (height + layoutParams.height)
            }
        })
    }

    override fun onBackPressed() {
        MediaUtil.getInstance().stop()
        finish()
        super.onBackPressed()
    }
}