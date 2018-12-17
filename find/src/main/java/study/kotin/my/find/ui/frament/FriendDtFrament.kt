package study.kotin.my.find.ui.frament

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import study.kotin.my.find.R
import study.kotin.my.find.ui.Activity.AddFriendDtActivity

class FriendDtFrament : DialogFragment(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.s1->{
                startActivity<AddFriendDtActivity>()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frienddtdialog, container, false)
        val s1=view.find<ImageView>(R.id.s1)
        val s2=view.find<ImageView>(R.id.s2)
        val s3=view.find<ImageView>(R.id.s3)
        val translationX = ObjectAnimator.ofFloat(s3, "translationX", 0f, -800f)
        val alpha = ObjectAnimator.ofFloat(s1, "alpha", 0f, 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationX,alpha)
        animatorSet.duration=500
        animatorSet.start()
        val translationX2 = ObjectAnimator.ofFloat(s1, "translationX", 0f, 800f)
        val alpha2 = ObjectAnimator.ofFloat(s3, "alpha", 0f, 1f)
        val animatorSet2 = AnimatorSet()
        animatorSet2.playTogether(translationX2,alpha2)
        animatorSet2.duration=500
        animatorSet2.start()
        s1.setOnClickListener(this)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity as Activity, R.style.ActionSheetDialogStyle)
        dialog.setContentView(R.layout.frienddtdialog)
        val window = dialog.window
        window!!.setGravity(Gravity.TOP)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        val attributes = window.attributes
//        attributes.y=20
        //       window.attributes=attributes
        dialog.show()
        return dialog
    }

}