package com.idhub.wallet.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;

public class MainEpidemicAdapter extends BaseRecyclerAdapter<MainEpidemicBean> {
    public MainEpidemicAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, MainEpidemicBean item) {
        TextView nameTextView = (TextView) holder.getView(R.id.name);
        TextView value0TextView = (TextView) holder.getView(R.id.value0);
        TextView value1TextView = (TextView) holder.getView(R.id.value1);
        TextView value2TextView = (TextView) holder.getView(R.id.value2);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_home_main_epidemic_item;
    }
}
