package study.kotin.my.baselibrary.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import study.kotin.my.baselibrary.R
import study.kotin.my.baselibrary.common.ProvinceAdapter
import study.kotin.my.baselibrary.common.SchoolAdapter
import study.kotin.my.baselibrary.data.ProvinceList
import study.kotin.my.baselibrary.data.SchoolList
import study.kotin.my.baselibrary.presenter.Basepersenter
import study.kotin.my.baselibrary.protocol.BaseResp
import java.util.ArrayList

open class SelectSchool(val school: EditText, val context: Context, val loadProvince: () -> Unit, val loadSchool: (urlcount: String) -> Unit) {

    lateinit var mProvinceListView: RecyclerView
    lateinit var mSchoolListView: RecyclerView
    lateinit var mTitle: TextView
    lateinit var mPopWindow: PopupWindow
    /**
     * Adapter相关
     */
    lateinit var mProvinceAdapter: ProvinceAdapter
    lateinit var mSchoolAdapter: SchoolAdapter
    lateinit var provinceId: String
    var itemListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        if (adapter === mProvinceAdapter) {
            val provinceName = mProvinceAdapter.getItem(position) as ProvinceList.DataBean
            provinceId = provinceName.getProvince_id()
            mTitle.setText("选择学校")
            mProvinceListView.setVisibility(View.GONE)
            mSchoolListView.setVisibility(View.VISIBLE)
            loadSchool(provinceId)
        } else if (adapter === mSchoolAdapter) {
            val schoolName = mSchoolAdapter.getItem(position) as SchoolList.DataBean
            school.setText(schoolName.getSchool_name())
            mPopWindow.dismiss()
        }
    }

    fun initPopView() {
        val popView = View.inflate(context, R.layout.view_select_province_list, null)
        mTitle = popView.findViewById(R.id.list_title) as TextView
        mProvinceListView = popView.findViewById(R.id.province)
        mSchoolListView = popView.findViewById(R.id.school)
        //--
        mProvinceAdapter = ProvinceAdapter(R.layout.item_province_list, ArrayList())
        mProvinceAdapter.setOnItemClickListener(itemListener)
        mProvinceListView.setAdapter(mProvinceAdapter)
        mProvinceListView.layoutManager = LinearLayoutManager(context)
        //--
        mSchoolAdapter = SchoolAdapter(R.layout.item_school_list, ArrayList())
        mSchoolAdapter.setOnItemClickListener(itemListener)
        mSchoolListView.setAdapter(mSchoolAdapter)
        mSchoolListView.layoutManager = LinearLayoutManager(context)
        //--
        val width = context.resources.displayMetrics.widthPixels * 3 / 4
        val height = context.resources.displayMetrics.heightPixels * 3 / 5
        mPopWindow = PopupWindow(popView, width, height)
        val dw = ColorDrawable(0x30000000)
        mPopWindow.setBackgroundDrawable(dw)
        mPopWindow.setFocusable(true)
        mPopWindow.setTouchable(true)
        mPopWindow.setOutsideTouchable(true)//允许在外侧点击取消
        loadProvince()
        mPopWindow.setOnDismissListener(listener)

    }

    var listener: PopupWindow.OnDismissListener = PopupWindow.OnDismissListener {
        mTitle.text = "选择地区"
        mProvinceListView.visibility = View.VISIBLE
        mSchoolListView.removeAllViewsInLayout()
        mSchoolAdapter.data.clear()
        mSchoolAdapter.notifyDataSetChanged()
        mSchoolListView.visibility = View.GONE
    }
}