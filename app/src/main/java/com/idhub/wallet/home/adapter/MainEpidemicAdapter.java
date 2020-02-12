package com.idhub.wallet.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;

public class MainEpidemicAdapter extends BaseRecyclerAdapter<MainEpidemicBean> {
    public MainEpidemicAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, MainEpidemicBean item) {

        View container = holder.getView(R.id.main_epidemic_item_container);
        TextView nameTextView = (TextView) holder.getView(R.id.name);
        TextView value0TextView = (TextView) holder.getView(R.id.value0);
        TextView value1TextView = (TextView) holder.getView(R.id.value1);
        TextView value2TextView = (TextView) holder.getView(R.id.value2);
        ViewCalculateUtil.setViewLinearLayoutParam(container, 147, 97, 0, 0, 8, 8);

        ViewCalculateUtil.setViewConstraintLayoutParam(nameTextView, ViewGroup.LayoutParams.WRAP_CONTENT, 20, 12, 0, 10, 0);
        ViewCalculateUtil.setTextSize(nameTextView, 14);
        ViewCalculateUtil.setViewConstraintLayoutParam(value0TextView, ViewGroup.LayoutParams.WRAP_CONTENT, 20, 8, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(value1TextView, ViewGroup.LayoutParams.WRAP_CONTENT, 17, 8, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(value2TextView, ViewGroup.LayoutParams.WRAP_CONTENT, 17, 0, 0, 0, 0);
        ViewCalculateUtil.setTextSize(value0TextView, 14);
        ViewCalculateUtil.setTextSize(value1TextView, 12);
        ViewCalculateUtil.setTextSize(value2TextView, 12);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_home_main_epidemic_item;
    }
}
