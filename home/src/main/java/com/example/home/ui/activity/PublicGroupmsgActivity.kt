package com.example.home.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.home.R
import com.example.home.persenter.HomePersenter
import com.example.home.ui.Frament.PublicGroupFarment_1
import com.example.home.ui.Frament.PublicGroupFarment_2
import com.example.home.ui.Frament.PublicGroupFarment_3
import com.example.home.ui.Frament.PublicGroupFarment_4
import kotlinx.android.synthetic.main.publicgrouplayout.*
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class PublicGroupmsgActivity : BaseMVPActivity<HomePersenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.l1 -> {
                initview()
                l1.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l1.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_1)
                beginTransaction.commit()
            }
            R.id.l2 -> {
                initview()
                l2.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l2.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_2)
                beginTransaction.commit()
            }
            R.id.l3 -> {
                initview()
                l3.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l3.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_3)
                beginTransaction.commit()
            }
            R.id.l4 -> {
                initview()
                l4.setTextColor(ContextCompat.getColor(this, R.color.mainred))
                l4.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val beginTransaction = supportFragmentManager.beginTransaction()
                hideAll(beginTransaction)
                beginTransaction.show(publicGroupFarment_4)
                beginTransaction.commit()
            }
        }

    }

    val publicGroupFarment_1 by lazy { PublicGroupFarment_1() }
    val publicGroupFarment_2 by lazy { PublicGroupFarment_2() }
    val publicGroupFarment_3 by lazy { PublicGroupFarment_3() }
    val publicGroupFarment_4 by lazy { PublicGroupFarment_4() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publicgrouplayout)
        initFraments()
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.show(publicGroupFarment_1)
        beginTransaction.commit()
        l1.setOnClickListener(this)
        l2.setOnClickListener(this)
        l3.setOnClickListener(this)
        l4.setOnClickListener(this)
    }

    private fun initFraments() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frament, publicGroupFarment_1)
        beginTransaction.add(R.id.frament, publicGroupFarment_2)
        beginTransaction.add(R.id.frament, publicGroupFarment_3)
        beginTransaction.add(R.id.frament, publicGroupFarment_4)
        hideAll(beginTransaction)
        beginTransaction.commit()
    }

    private fun hideAll(beginTransaction: FragmentTransaction) {
        beginTransaction.hide(publicGroupFarment_1)
        beginTransaction.hide(publicGroupFarment_2)
        beginTransaction.hide(publicGroupFarment_3)
        beginTransaction.hide(publicGroupFarment_4)
    }

  private fun  initview(){
      l1.setTextColor(ContextCompat.getColor(this, R.color.black))
      l1.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
      l2.setTextColor(ContextCompat.getColor(this, R.color.black))
      l2.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
      l3.setTextColor(ContextCompat.getColor(this, R.color.black))
      l3.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
      l4.setTextColor(ContextCompat.getColor(this, R.color.black))
      l4.setBackgroundColor(ContextCompat.getColor(this, R.color.touming))
  }

}