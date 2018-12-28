package study.kotin.my.baselibrary.common;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import study.kotin.my.baselibrary.R;
import study.kotin.my.baselibrary.data.ProvinceList;


public class ProvinceAdapter extends BaseQuickAdapter<ProvinceList.DataBean,BaseViewHolder> {


    public ProvinceAdapter(int layoutResId, @Nullable List<ProvinceList.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvinceList.DataBean item) {
        helper.setText(R.id.province_name,item.getProvince_name());
    }
}
