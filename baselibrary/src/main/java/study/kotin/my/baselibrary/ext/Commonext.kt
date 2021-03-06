package study.kotin.my.baselibrary.ext


import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.support.v4.app.FragmentActivity
import android.view.View
import androidx.core.widget.toast
import com.tencent.imsdk.TIMSoundElem
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import study.kotin.my.baselibrary.rx.BaseObserver
import java.util.regex.Pattern


fun <T> Observable<T>.excute(Observer: BaseObserver<T>, lifecycleProvider: LifecycleProvider<*>) {

    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe(Observer)


}

fun View.passverify(s: String, context: Activity?): Boolean {
    val patternnmber = """^[1-9]\d*${'$'}"""
    val patterEN = """^[A-Za-z]+${'$'}"""
    val matcher = Pattern.compile(patternnmber).matcher(s.trim())
    val matcher2 = Pattern.compile(patterEN).matcher(s.trim())
    if (s.trim().length in 0..8) {
        context!!.toast("字符必须大于8个字符")
        return false
    }
    if (matcher.find()) {
        context!!.toast("不能全数字")
        return false
    }
    if (matcher2.find()) {
        context!!.toast("不能全字母")
        return false
    }
    return true

}

fun MediaPlayer.getsoundtime(path: String): Long {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setDataSource(path)
    mediaPlayer.prepare()
    val duration = mediaPlayer.duration
    return duration.toLong()
}

fun Activity.setuserstting(key:String,value:Boolean){
    val edit = getSharedPreferences("UserSetting", Context.MODE_PRIVATE).edit()
    edit.putBoolean(key, value)
    edit.apply()
}
fun Activity.getuserstting(key:String):Boolean{
    val edit = getSharedPreferences("UserSetting", Context.MODE_PRIVATE)
    return edit.getBoolean(key,false)
}

fun Context.getjwt():String?{
    var sharedPreferences = getSharedPreferences("UserAcc", Context.MODE_PRIVATE).getString("jwt", "")
    if(sharedPreferences!=""){
        sharedPreferences="Bearer $sharedPreferences"
    }
    return sharedPreferences
}