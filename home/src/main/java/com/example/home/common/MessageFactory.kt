package study.kotin.my.baselibrary.ext


import com.example.home.common.TextMessge
import com.example.home.persenter.view.HomeView
import com.tencent.imsdk.TIMElemType
import com.tencent.imsdk.TIMMessage

/**
 * 消息工厂
 */
object MessageFactory {
    /**
     * 消息工厂方法
     */
    fun getMessage(message: TIMMessage?, mview: HomeView) {
        when (message!!.getElement(0).type) {
            TIMElemType.Text, TIMElemType.Face -> return TextMessge.showTextMessge(message,mview)
            TIMElemType.Image -> return
            TIMElemType.Sound -> return
            TIMElemType.Video -> return
            TIMElemType.GroupTips,
                //  return new GroupTipMessage(message);
            TIMElemType.File -> return
            TIMElemType.UGC -> return
            else -> return
        }
    }


}
