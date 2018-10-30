package study.kotin.my.baselibrary.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import study.kotin.my.baselibrary.R

/*
    底部导航
 */
class BottomNavBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    //消息Tab 标签
    private val mMsgBadge:TextBadgeItem

    init {
        //聊天
        val homeItem = BottomNavigationItem(R.drawable.a1,resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.a1_2)
                .setActiveColorResource(R.color.mainred)
                .setInActiveColorResource(R.color.text_normal)

        mMsgBadge = TextBadgeItem()
        homeItem.setBadgeItem(mMsgBadge)
        //通讯
        val cartItem = BottomNavigationItem(R.drawable.a2_2,resources.getString(R.string.nav_bar_cart))
                .setInactiveIconResource(R.drawable.a2)
                .setActiveColorResource(R.color.mainred)
                .setInActiveColorResource(R.color.text_normal)


        //消息
        val msgItem = BottomNavigationItem(R.drawable.a3,resources.getString(R.string.nav_bar_msg))
                .setInactiveIconResource(R.drawable.a3_2)
                .setActiveColorResource(R.color.mainred)
                .setInActiveColorResource(R.color.text_normal)



        //我的
        val userItem = BottomNavigationItem(R.drawable.a4_2,resources.getString(R.string.nav_bar_user))
                .setInactiveIconResource(R.drawable.a4)
                .setActiveColorResource(R.color.mainred)
                .setInActiveColorResource(R.color.text_normal)

        //设置底部导航模式及样式
        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)
        //添加Tab
        addItem(homeItem)
                .addItem(cartItem)
                .addItem(msgItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0)
                .initialise()
    }



    /*
        检查消息Tab是否显示标签
     */
    fun checkMsgBadge(count:Int){
        if (count!=0){
            mMsgBadge.show()
            mMsgBadge.setText("$count")
        }else {
            mMsgBadge.hide()

        }
    }
}
