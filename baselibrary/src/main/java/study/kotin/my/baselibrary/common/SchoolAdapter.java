package study.kotin.my.baselibrary.common;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


import study.kotin.my.baselibrary.R;
import study.kotin.my.baselibrary.data.SchoolList;

/**
 * Created by tonycheng on 2015/9/19.
 */
public class SchoolAdapter extends BaseQuickAdapter<SchoolList.DataBean,BaseViewHolder> {

    public SchoolAdapter(int layoutResId, @Nullable List<SchoolList.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolList.DataBean item) {
        helper.setText(R.id.school_name,item.getSchool_name());
    }
}
