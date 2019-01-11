package study.kotin.my.baselibrary.ext


import com.eightbitlab.rxbus.Bus
import com.example.home.Messges.*
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
            TIMElemType.Text, TIMElemType.Face -> TextMessge.showTextMessge(message, mview)
            TIMElemType.Image -> ImgMessge.showimgmessge(message, mview)
            TIMElemType.Sound -> SoundMessge.showSoundMsg(message, mview)
            TIMElemType.Video -> {
            }
            TIMElemType.GroupTips -> {
                GroupTipsMessge.showGroupTipsMessge(message, mview)
            }
            //  return new GroupTipMessage(message);
            TIMElemType.File -> FileMessge.showfileMessge(message, mview)
            TIMElemType.UGC -> {
            }
            else -> {
            }
        }

    }


}
