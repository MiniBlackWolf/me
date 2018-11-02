package study.kotin.my.usercenter.common

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.toast
import tencent.tls.platform.TLSHelper

class TextWatchers(val tlsHelper: TLSHelper, val Listener: Any, val text: EditText, val i0: Int,val context:Context) : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
       val code=text.text.toString()
        if(!hasFocus) {
            if (code=="") {
                context.toast("请输入验证码")
                return
            }
            if (i0 == 0) {
                tlsHelper.TLSPwdResetVerifyCode(code, Listener as PwdResetListener)
            }
            if (i0 == 1) {
                tlsHelper.TLSPwdRegVerifyCode(code, Listener as PwdRegListener)
            }
        }
    }


}