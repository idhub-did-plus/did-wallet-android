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

public class STRecommendAdapter extends BaseRecyclerAdapter<STRecommendBean> {
    public STRecommendAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, STRecommendBean item) {
        View container = holder.getView(R.id.st_recommend_item_container);
        TextView nameTextView = (TextView) holder.getView(R.id.name);
        TextView valueTextView = (TextView) holder.getView(R.id.value);
        ImageView hotIcon = (ImageView) holder.getView(R.id.hot_icon);
        ViewCalculateUtil.setViewLinearLayoutParam(container, 164, 70, 0, 8, 8, 8);
        ViewCalculateUtil.setViewConstraintLayoutParam(nameTextView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 11, 0, 10, 0);
        ViewCalculateUtil.setTextSize(nameTextView, 14);
        ViewCalculateUtil.setViewConstraintLayoutParam(valueTextView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 11, 10, 0);
        ViewCalculateUtil.setTextSize(valueTextView, 14);
        ViewCalculateUtil.setViewConstraintLayoutParam(hotIcon, 25, 27, 0, 4, 0, 3);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_home_st_recommend_item;
    }
}
