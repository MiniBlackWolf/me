package study.kotin.my.baselibrary.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log

import com.tencent.qcloud.presentation.event.MessageEvent
import java.util.Observable
import java.util.Observer

import study.kotin.my.baselibrary.R
import study.kotin.my.baselibrary.common.BaseApplication
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

import android.content.Context.NOTIFICATION_SERVICE
import com.tencent.imcore.TextElem
import com.tencent.imsdk.*

/**
 * 在线消息通知展示
 */
class PushUtil private constructor() : Observer {

    private val pushId = 1

    init {
        MessageEvent.getInstance().addObserver(this)
    }


    private fun PushNotify(msg: TIMMessage?) {
        //系统消息，自己发的消息，程序在前台的时候不通知
        if (msg == null || Foreground.get().isForeground || msg.conversation.type != TIMConversationType.Group && msg.conversation.type != TIMConversationType.C2C || msg.isSelf || msg.recvFlag == TIMGroupReceiveMessageOpt.ReceiveNotNotify) return
        var senderStr: String?
        val contentStr: String
        val element = msg.getElement(0)
        val type = msg.getElement(0).type
        val fdid = msg.sender
        val fdname = BaseApplication.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString(fdid + "fdname", "")
        senderStr = fdname
        if(fdid=="100007675650"){
            senderStr="系统"
        }
        when (type) {
            TIMElemType.Text -> {
                contentStr = (element as TIMTextElem).text
            }
            TIMElemType.Image -> {
                contentStr = "图片"
            }
            TIMElemType.Sound -> {
                contentStr = "语音"
            }
            TIMElemType.File -> {
                contentStr = "文件"
            }
            TIMElemType.GroupTips -> {
                contentStr = "群消息"
            }
            TIMElemType.SNSTips -> {
                contentStr = "好友消息"
            }
            TIMElemType.UGC -> {
                contentStr = "UGC消息"
            }
            else -> {
                contentStr = ""
            }
        }

        val mNotificationManager = BaseApplication.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(BaseApplication.context)
        val notificationIntent = Intent(BaseApplication.context, BaseMVPActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val intent = PendingIntent.getActivity(BaseApplication.context, 0,
                notificationIntent, 0)
        mBuilder.setContentTitle(senderStr)//设置通知栏标题
                .setContentText(contentStr)
                .setContentIntent(intent) //设置通知栏点击意图
                //                .setNumber(++pushNum) //设置通知集合的数量
                .setTicker("$senderStr:$contentStr") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.drawable.a4_2)//设置通知小ICON
                .setChannelId(BaseApplication.context.packageName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(BaseApplication.context.packageName, TAG, NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager.createNotificationChannel(channel)
        }
        val notify = mBuilder.build()
        notify.flags = notify.flags or Notification.FLAG_AUTO_CANCEL
        mNotificationManager.notify(pushId, notify)
    }

    fun reset() {
        val notificationManager = BaseApplication.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(pushId)
    }

    /**
     * This method is called if the specified `Observable` object's
     * `notifyObservers` method is called (because the `Observable`
     * object has been updated.
     *
     * @param observable the [Observable] object.
     * @param data       the data passed to [Observable.notifyObservers].
     */
    override fun update(observable: Observable, data: Any) {
        if (observable is MessageEvent) {
            if (data is TIMMessage) {
                if (data != null) {
                    PushNotify(data)
                }
            }
        }
    }

    companion object {

        private val TAG = PushUtil::class.java.simpleName

        private var pushNum = 0

        val instance = PushUtil()

        fun resetPushNum() {
            pushNum = 0
        }
    }


}
