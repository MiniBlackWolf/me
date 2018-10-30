package study.kotin.my.baselibrary.ext


import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.View
import androidx.core.widget.toast
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import study.kotin.my.baselibrary.rx.BaseObserver
import java.util.regex.Pattern


fun <T> Observable<T>.excute(Observer: BaseObserver<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .subscribe(Observer)


}

fun View.passverify(s:String,context: Activity?):Boolean{

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
    if(matcher2.find()){
        context!!.toast("不能全字母")
        return false
    }
    return true

}