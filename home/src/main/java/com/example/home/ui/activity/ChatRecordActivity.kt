package com.example.home.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.home.HomeAdapter.chatadapter
import com.example.home.R
import com.example.home.common.Msg
import com.example.home.data.Sounddata
import com.example.home.data.UserInfoData
import com.example.home.data.longtimedata
import com.example.home.persenter.HomePersenter
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.message.TIMConversationExt
import kotlinx.android.synthetic.main.chatlayout.*
import kotlinx.android.synthetic.main.chatrecordlayout.*
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ext.getsoundtime
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.baselibrary.utils.FileUtil
import java.util.*

class ChatRecordActivity : BaseMVPActivity<HomePersenter>() {
    var id: String? = ""
    var trun: Boolean = true
    var trun2: Boolean = true
    var lastTIMMessage: TIMMessage? = null
    val SEND_MSG_TYPE = 1
    val SHOW_MSG_TYPE = 0
    var msglist = ArrayList<Msg>()
    lateinit var chatadapter: chatadapter
    var MONTH: Int = 0
    var DATE: Int = 0
    var year: Int = 0
    lateinit var datalist: MutableList<longtimedata>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatrecordlayout)
        ctf.setOnClickListener {
            finish()
        }
        id = intent.extras?.getString("id")
        chatadapter = chatadapter(msglist, this)
        chatrecord.adapter = chatadapter
        chatrecord.layoutManager = LinearLayoutManager(this)
        val calendar = Calendar.getInstance()
        val time = calendar.timeInMillis
        MONTH = calendar.get(Calendar.MONTH) + 1
        DATE = calendar.get(Calendar.DATE)
        year = calendar.get(Calendar.YEAR)
        getlangtimemsg(99, null)
        timezuo.setOnClickListener {
            DATE--
            if (DATE == 0) {
                MONTH--
                if (MONTH % 2 == 1) {
                    DATE = 31
                } else {
                    DATE = 30
                }
                if (year % 4 == 0 && MONTH == 2) {
                    DATE = 29
                }
                if (MONTH == 2) {
                    DATE = 28
                }

            }
            if (MONTH == 0) {
                year--
                MONTH = 12
            }
            chatadapter.data.clear()
            chatrecord.removeAllViewsInLayout()
            timeset(datalist)
        }
        timeyou.setOnClickListener {
            DATE++
            if (MONTH % 2 == 1) {
                if (DATE > 31) {
                    DATE = 1
                    MONTH++
                    if (MONTH == 13) {
                        year++
                        MONTH = 1
                    }
                }
            } else {
                if (DATE > 30) {
                    DATE = 1
                    MONTH++
                    if (MONTH == 13) {
                        year++
                        MONTH = 1
                    }
                }

            }
            if (year % 4 == 0 && MONTH == 2) {
                if (DATE > 29) {
                    DATE = 1
                    MONTH++
                    if (MONTH == 13) {
                        year++
                        MONTH = 1
                    }
                }
            }
            if (MONTH == 2) {
                if (DATE > 28) {
                    DATE = 1
                    MONTH++
                    if (MONTH == 13) {
                        year++
                        MONTH = 1
                    }
                }
            }
            chatadapter.data.clear()
            chatrecord.removeAllViewsInLayout()
            timeset(datalist)
        }
    }

    fun getlangtimemsg(count: Int, lastMsg: TIMMessage?) {
        val list = ArrayList<longtimedata>()
        val conversation: TIMConversation
        if (id!!.substring(0, 5) == "@TGS#") {
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
                    return
                }
                if (count == 99) {
                    trun2 = false
                }
                val p0 = LinkedHashSet(p0).toMutableList()
                p0.reverse()
                lastTIMMessage = p0[0]
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
                        TIMElemType.GroupTips -> list.add(longtimedata((element as TIMGroupTipsElem), p0.get(i).timestamp(), 5, p0.get(i).isSelf))

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
            val datalists = data.getSerializable("list") as ArrayList<longtimedata>
            datalist = datalists.toMutableList()
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
            //       datalist.reverse()
            trun = false

            //循环更新视图
            timeset(datalist)
            trun = true
            trun2 = true
            //   data.getSerializable()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun timeset(datalist: MutableList<longtimedata>) {
        timeshow.text = "$year/$MONTH/$DATE"
        for (datas in datalist) {
            val mycalendar = Calendar.getInstance()
            mycalendar.timeInMillis = datas.time * 1000
            val myMONTH = mycalendar.get(Calendar.MONTH) + 1
            val myDATE = mycalendar.get(Calendar.DATE)
            val myyear = mycalendar.get(Calendar.YEAR)
            if (myMONTH == MONTH && DATE == myDATE && year == myyear) {
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
                    5->{
                        if (datas.isseft) {
                            updataview(datas.data as TIMGroupTipsElem, SEND_MSG_TYPE, 5)
                        } else updataview(datas.data as TIMGroupTipsElem, SHOW_MSG_TYPE, 5)
                    }
                }
            }

        }
    }

    fun updataview(data: Any, Type: Int, datatype: Int) {
        val name: String?
        val fdheadurl: String?
        val fdid: String?
        val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        if (Type == SHOW_MSG_TYPE) {
            name = sharedPreferences.getString(id + "fdname", "")
            fdheadurl = sharedPreferences.getString(id + "fdheadurl", "")
            fdid = sharedPreferences.getString(id + "fdid", "")
        } else {
            name = sharedPreferences.getString("myname", "")
            fdheadurl = sharedPreferences.getString("myheadurl", "")
            fdid = TIMManager.getInstance().loginUser
        }

        val msglists = ArrayList<Msg>()
        msglists.add(Msg(data, Type, datatype, UserInfoData(fdheadurl!!, name!!, fdid!!)))
        if (!trun) {
            chatadapter.addData(0, msglists)
            chatadapter.notifyDataSetChanged()

        } else {
            chatadapter.addData(chatadapter.itemCount, msglists)
            chatadapter.notifyDataSetChanged()
        }
        chatrecord.scrollToPosition(chatadapter.itemCount - 1)
    }
}