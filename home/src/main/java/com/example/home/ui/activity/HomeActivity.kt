package com.example.home.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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
import study.kotin.my.baselibrary.common.BaseApplication
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.ajguan.library.EasyRefreshLayout
import com.ajguan.library.LoadModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.home.HomeAdapter.emoji
import study.kotin.my.baselibrary.utils.GifSizeFilter
import com.example.home.Utils.RefreshView
import com.example.home.common.itemclickListener
import com.example.home.data.Sounddata
import com.example.home.data.UserInfoData
import com.example.home.data.longtimedata
import com.oden.syd_camera.SydCameraActivity
import com.oden.syd_camera.camera.CameraParaUtil
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo
import com.tencent.imsdk.ext.group.TIMGroupManagerExt
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMManagerExt
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt
import com.zhihu.matisse.filter.Filter
import org.jetbrains.anko.*
import study.kotin.my.baselibrary.ext.getsoundtime
import study.kotin.my.baselibrary.protocol.BaseResp
import study.kotin.my.baselibrary.utils.EmoticonUtil
import study.kotin.my.baselibrary.utils.FileUtil
import study.kotin.my.baselibrary.utils.MediaUtil
import java.io.File
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


@Suppress("DEPRECATION")
@Route(path = "/home/HomeActivity")
class HomeActivity : BaseMVPActivity<HomePersenter>(), HomeView, View.OnClickListener {

    val SEND_MSG_TYPE = 1
    val SHOW_MSG_TYPE = 0
    lateinit var id: String
    lateinit var chatadapter: chatadapter
    var lastTIMMessage: TIMMessage? = null
    var trun: Boolean = true
    var trun2: Boolean = true
    //发送语音消息
    override fun sendSoundmsg() {
        val audiosh = getSharedPreferences("sp_name_audio", MODE_PRIVATE)
        val adpath = audiosh.getString("audio_path", "")
        val adtime = audiosh.getLong("elpased", -1)
        if (adpath != "" && adtime != -1L) {
            val TIMSoundElem = TIMSoundElem()
            TIMSoundElem.path = adpath
            trun = true
            trun2 = false
            mpersenter.sendmessge(id, TIMSoundElem)
            //--updataview
            updataview(Sounddata(adpath!!, adtime), TIMManager.getInstance().loginUser,SEND_MSG_TYPE, 3)

        }
    }


    //发送消息
    override fun sendmsg() {
        val timTextElem = TIMTextElem()
        timTextElem.text = chatsendview.editText.text.toString()
        mpersenter.sendmessge(id, timTextElem)
        //----updataview
        trun = true
        trun2 = false
        updataview(chatsendview.editText.text.toString(), TIMManager.getInstance().loginUser,SEND_MSG_TYPE, 1)
        chatsendview.editText.setText("")

    }

    var msglist = ArrayList<Msg>()
    //群消息
    override fun showgrouptipmsg(TIMGroupTipsElem: TIMGroupTipsElem,id:String) {
        updataview(TIMGroupTipsElem, id,SHOW_MSG_TYPE, 5)
    }

    //文本信息
    override fun showtextmsg(TIMTextElem: TIMTextElem,id:String) {
        updataview(TIMTextElem.text.toString(),id, SHOW_MSG_TYPE, 1)
    }

    //图片信息
    override fun showimgmsg(bitmap: Bitmap,id:String) {
        updataview(bitmap,id, SHOW_MSG_TYPE, 2)
    }

    //语音消息
    override fun showSoundmsg(path: String, time: Long,id:String) {
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
        updataview(Sounddata(path, time),id, SHOW_MSG_TYPE, 3)
    }

    //文件消息
    override fun showFilemsg(TIMFileElem: TIMFileElem,id:String) {
        updataview(TIMFileElem,id, SHOW_MSG_TYPE, 4)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatlayout)
        initinject()
        KeyboardUtils.fixAndroidBug5497(this)
        val Bgpath = getSharedPreferences("boolen", Context.MODE_PRIVATE).getString("BGpath", "")
        if (Bgpath != "") {
            //压缩bitmap
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(Bgpath, options)
            val ratio = Math.max(options.outWidth * 1.0 / 1024f, options.outHeight * 1.0 / 1024f)
            options.inSampleSize = Math.ceil(ratio).toInt()
            options.inJustDecodeBounds = false
            val photoImg = BitmapFactory.decodeFile(Bgpath, options)
            chatrecyclerview.background = BitmapDrawable(photoImg)
        }

        //用户名获取
        val extras = intent.extras
        id = extras!!.get("id") as String
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
        getlangtimemsg(10, null)
        //已读消息上报
        UpdataReadMessage()
        //事件点击方法
        chatsendview.btnEmotion.setOnClickListener {
            emoticon.isVisible = !emoticon.isVisible

        }
        chatfh.setOnClickListener {
            finish()
        }
        chatadapter.onItemChildClickListener = itemclickListener(chatrecyclerview, this@HomeActivity)
        //表情！！！！！！！！！！
        Emoji()
        //加载更多
        adddownmore()
        //对话信息
        topmsg()
        val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        if (id.substring(0, 5) == "@TGS#") {
            val Gtype = sharedPreferences.getString("${id}Gtype", "")
            if (Gtype == "") {
                TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(id), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
                    override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                        if (p0?.size == null) return
                        if (p0[0].groupType == "Public") {
                            addmore.text = "社团信息"
                            addmore.setOnClickListener {
                                startActivity<PublicGroupmsgActivity>("id" to id)
                            }

                        } else {
                            addmore.text = "更多"
                            addmore.setOnClickListener { it ->
                                val popWindow = PopupWindow(this@HomeActivity)
                                val view = layoutInflater.inflate(R.layout.addmoreitem, null)
                                popWindow.contentView = view//显示的布局，还可以通过设置一个View // .size(600,400) //设置显示的大小，不设置就默认包裹内容
                                popWindow.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.touming)))
                                popWindow.setFocusable(true)//是否获取焦点，默认为ture
                                popWindow.setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                                popWindow.showAsDropDown(addmore, 0, 10)//显示PopupWindow
                                view.find<TextView>(R.id.iniv).setOnClickListener {
                                    ARouter.getInstance().build("/address/FriendActivity").withString("type", "iniv").withString("Gid", id).navigation()
                                    finish()
                                }
                                view.find<TextView>(R.id.diss).setOnClickListener {
                                    TIMGroupManager.getInstance().quitGroup(id, object : TIMCallBack {
                                        override fun onSuccess() {
                                            toast("退出成功")
                                            TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.Group, id)
                                            finish()
                                        }

                                        override fun onError(p0: Int, p1: String?) {
                                            toast("退出失败$p1")
                                        }
                                    })

                                }
                            }
                        }

                    }

                    override fun onError(p0: Int, p1: String?) {
                    }

                })
            } else {
                if (Gtype == "Public") {
                    addmore.text = "社团信息"
                    addmore.setOnClickListener {
                        startActivity<PublicGroupmsgActivity>("id" to id)
                    }

                } else {
                    addmore.text = "更多"
                    addmore.setOnClickListener { it ->
                        val popWindow = PopupWindow(this)
                        val view = layoutInflater.inflate(R.layout.addmoreitem, null)
                        popWindow.contentView = view//显示的布局，还可以通过设置一个View // .size(600,400) //设置显示的大小，不设置就默认包裹内容
                        popWindow.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.touming)))
                        popWindow.setFocusable(true)//是否获取焦点，默认为ture
                        popWindow.setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                        popWindow.showAsDropDown(addmore, 0, 10)//显示PopupWindow
                        view.find<TextView>(R.id.iniv).setOnClickListener {
                            ARouter.getInstance().build("/address/FriendActivity").withString("type", "iniv").withString("Gid", id).navigation()
                            finish()
                        }
                        view.find<TextView>(R.id.diss).setOnClickListener {
                            TIMGroupManager.getInstance().quitGroup(id, object : TIMCallBack {
                                override fun onSuccess() {
                                    toast("退出成功")
                                    TIMManagerExt.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.Group, id)
                                    finish()
                                }

                                override fun onError(p0: Int, p1: String?) {
                                    toast("退出失败$p1")
                                }
                            })

                        }
                    }
                }
            }
        } else {
            addmore.text = "设置"
            addmore.setOnClickListener {
                startActivity<PersonalChatSettingActivity>("id" to id)
            }
        }


    }

    private fun topmsg() {
        TIMFriendshipManager .getInstance().getUsersProfile (arrayListOf(id), object : TIMValueCallBack<MutableList<TIMUserProfile>> {
            override fun onSuccess(p0: MutableList<TIMUserProfile>?) {
                if (p0?.size == null) return
                if (p0[0].remark == "") {
                    chatname.text = p0[0].nickName
                } else {
                    chatname.text = p0[0].remark
                }

            }

            override fun onError(p0: Int, p1: String?) {
            }

        })
        TIMGroupManagerExt.getInstance().getGroupDetailInfo(arrayListOf(id), object : TIMValueCallBack<MutableList<TIMGroupDetailInfo>> {
            override fun onSuccess(p0: MutableList<TIMGroupDetailInfo>?) {
                if (p0?.size == null) return
                chatname.text = p0[0].groupName
            }

            override fun onError(p0: Int, p1: String?) {
            }

        })
    }

    private fun adddownmore() {
        morelayout.setRefreshHeadView(RefreshView(this@HomeActivity))
        morelayout.setLoadMoreModel(LoadModel.NONE)
        morelayout.addEasyEvent(object : EasyRefreshLayout.EasyEvent {
            override fun onLoadMore() {

            }

            override fun onRefreshing() {
                Thread(Runnable {
                    getlangtimemsg(5, lastTIMMessage)
                }).run()
            }
        })
    }

    private fun Emoji() {
        val emojiadapter = emoji(EmoticonUtil.emoticonData.toList())
        emoticon.adapter = emojiadapter
        emojiadapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val text = adapter!!.getViewByPosition(emoticon, position, android.R.id.text1) as TextView
                val index = chatsendview.editText.getSelectionStart()
                val editable = chatsendview.editText.text
                editable.insert(index, text.text.toString())
            }
        }
        emoticon.layoutManager = GridLayoutManager(this@HomeActivity, 7)
    }

    private fun UpdataReadMessage() {
        val conversation: TIMConversation
        if (id.substring(0, 5) == "@TGS#") {
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.Group, //会话类型：群聊
                    id)
        } else {
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.C2C, //会话类型：单聊
                    id)
        }
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
                        .imageEngine(study.kotin.my.baselibrary.common.GlideEngine())
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
            timImageElem.level = 0
            trun = true
            trun2 = false
            mpersenter.sendmessge(id, timImageElem)
            //显示界面
            updataview(bitmap,TIMManager.getInstance().loginUser, SEND_MSG_TYPE, 2)
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
            trun = true
            trun2 = false
            mpersenter.sendmessge(id, timimgElem)
            updataview(bitmap, TIMManager.getInstance().loginUser,SEND_MSG_TYPE, 2)
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
            if (length > 10485697) {
                toast("文件不能大于10mb!")
                return
            }
            val TIMFileElem = TIMFileElem()
            TIMFileElem.path = img_path
            TIMFileElem.fileName = file.name
            trun = true
            trun2 = false
            mpersenter.sendmessge(id, TIMFileElem)
            updataview(TIMFileElem, TIMManager.getInstance().loginUser,SEND_MSG_TYPE, 4)
            Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun getlangtimemsg(count: Int, lastMsg: TIMMessage?) {
        val list = ArrayList<longtimedata>()
        val conversation: TIMConversation
        if (id.substring(0, 5) == "@TGS#") {
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.Group, //会话类型：群聊
                    id)
        } else {
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.C2C, //会话类型：单聊
                    id)
        }
        TIMConversationExt(conversation).getMessage(count, lastMsg, object :
                TIMValueCallBack<MutableList<TIMMessage>> {
            override fun onSuccess(p0: MutableList<TIMMessage>?) {
                if (p0!!.isEmpty()) {
                    chatadapter.isUpFetchEnable = false
                    morelayout.refreshComplete()
                    return
                }
                if (count == 10) {
                    trun2 = false
                }
                val p0 = LinkedHashSet(p0).toMutableList()
                p0.reverse()
                lastTIMMessage = p0[0]
                loop@ for (i in 0 until p0.size) {
                    val element = p0.get(i).getElement(0)
                    when (element.type) {
                        TIMElemType.Text, TIMElemType.Face -> {
                            list.add(longtimedata((element as TIMTextElem).text,p0.get(i).sender, p0.get(i).timestamp(), 1, p0.get(i).isSelf))
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
                                        list.add(longtimedata(bitmap,p0.get(i).sender, p0.get(i).timestamp(), 2, p0.get(i).isSelf))
                                        Log.d("imgs--d", "getImage success.")
                                    }
                                })
                            }

                        }
                        TIMElemType.Sound -> {
                            var string = getSharedPreferences("LongTimeData", Context.MODE_PRIVATE).getString((element as TIMSoundElem).uuid, "")
                            if(!File(string).exists()){
                                string=""
                            }
                            if (string != "") {

                                val getsoundtime = MediaPlayer().getsoundtime(string!!)
                                list.add(longtimedata(Sounddata(string, getsoundtime),p0.get(i).sender, p0.get(i).timestamp(), 3, p0.get(i).isSelf))
                                continue@loop
                            }

                            val tempAudio = FileUtil.getTempFile(FileUtil.FileType.AUDIO)
                            (element as TIMSoundElem).getSoundToFile(tempAudio.absolutePath, object : TIMCallBack {
                                override fun onSuccess() {
                                    val getsoundtime = MediaPlayer().getsoundtime(tempAudio.canonicalPath)
                                    list.add(longtimedata(Sounddata(tempAudio.absolutePath, getsoundtime),p0.get(i).sender, p0.get(i).timestamp(), 3, p0.get(i).isSelf))
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
                        TIMElemType.GroupTips -> {
                            list.add(longtimedata((element as TIMGroupTipsElem), p0.get(i).sender,p0.get(i).timestamp(), 5, p0.get(i).isSelf))
                        }
                        //  return new GroupTipMessage(message);
                        TIMElemType.File -> {
                            list.add(longtimedata((element as TIMFileElem), p0.get(i).sender,p0.get(i).timestamp(), 4, p0.get(i).isSelf))
                        }
                        TIMElemType.UGC -> {
                        }
                        else -> {
                        }
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
            val datalists = data.getSerializable("list") as ArrayList<longtimedata>
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
            datalist.reverse()
            trun = false
            for (datas in datalist) {
                when (datas.type) {
                    1 -> {
                        if (datas.isseft) {
                            updataview(datas.data as String,datas.id, SEND_MSG_TYPE, 1)
                        } else updataview(datas.data as String,datas.id, SHOW_MSG_TYPE, 1)

                    }
                    2 -> {
                        if (datas.isseft) {
                            updataview(datas.data as Bitmap,datas.id, SEND_MSG_TYPE, 2)
                        } else updataview(datas.data as Bitmap,datas.id, SHOW_MSG_TYPE, 2)
                    }
                    3 -> {
                        if (datas.isseft) {
                            updataview(datas.data as Sounddata,datas.id, SEND_MSG_TYPE, 3)
                        } else updataview(datas.data as Sounddata,datas.id, SHOW_MSG_TYPE, 3)
                    }
                    4 -> {
                        if (datas.isseft) {
                            updataview(datas.data as TIMFileElem,datas.id, SEND_MSG_TYPE, 4)
                        } else updataview(datas.data as TIMFileElem,datas.id, SHOW_MSG_TYPE, 4)
                    }
                    5 -> {
                        if (datas.isseft) {
                            updataview(datas.data as TIMGroupTipsElem,datas.id, SEND_MSG_TYPE, 5)
                        } else updataview(datas.data as TIMGroupTipsElem,datas.id, SHOW_MSG_TYPE, 5)
                    }

                }

            }

            trun = true
            trun2 = true
            //   data.getSerializable()
        }
    }


    fun updataview(data: Any,id:String, Type: Int, datatype: Int) {
        val name: String?
        val fdheadurl: String?
        val fdid: String?
        val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        if (Type == SHOW_MSG_TYPE) {
            name = sharedPreferences.getString(id + "fdname", "")
            fdheadurl = sharedPreferences.getString(id + "fdheadurl", "")
       //     fdid = sharedPreferences.getString(id + "fdid", "")
        } else {
            name = sharedPreferences.getString("myname", "")
            fdheadurl = sharedPreferences.getString("myheadurl", "")
       //     fdid = TIMManager.getInstance().loginUser
        }
        val msglists = ArrayList<Msg>()
        msglists.add(Msg(data, Type, datatype, UserInfoData(fdheadurl!!, name!!, id)))
        if (!trun) {
            chatadapter.addData(0, msglists)
            chatadapter.notifyDataSetChanged()

        } else {
            chatadapter.addData(chatadapter.itemCount, msglists)
            chatadapter.notifyDataSetChanged()
        }
        if (!trun2) {
            chatrecyclerview.scrollToPosition(chatadapter.itemCount - 1)
        }
        morelayout.refreshComplete()
    }


    override fun onBackPressed() {
        MediaUtil.getInstance().stop()
        finish()
        super.onBackPressed()
    }
}