package study.kotin.my.baselibrary.ext


import com.example.home.Messges.FileMessge
import com.example.home.Messges.ImgMessge
import com.example.home.Messges.SoundMessge
import com.example.home.Messges.TextMessge
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
                TIMElemType.Text, TIMElemType.Face -> return TextMessge.showTextMessge(message, mview)
                TIMElemType.Image -> return ImgMessge.showimgmessge(message, mview)
                TIMElemType.Sound -> return SoundMessge.showSoundMsg(message, mview)
                TIMElemType.Video -> return
                TIMElemType.GroupTips,
                    //  return new GroupTipMessage(message);
                TIMElemType.File -> return FileMessge.showfileMessge(message, mview)
                TIMElemType.UGC -> return
                else -> return
            }

    }


}
