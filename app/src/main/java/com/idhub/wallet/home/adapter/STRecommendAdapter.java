package com.idhub.wallet.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;

public class STRecommendAdapter extends BaseRecyclerAdapter<STRecommendBean> {
    public STRecommendAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, STRecommendBean item) {
        TextView nameTextView = (TextView) holder.getView(R.id.name);
        TextView valueTextView = (TextView) holder.getView(R.id.value);
        ImageView hotIcon = (ImageView) holder.getView(R.id.hot_icon);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_home_st_recommend_item;
    }
}
