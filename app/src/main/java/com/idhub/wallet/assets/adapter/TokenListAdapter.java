package com.idhub.wallet.assets.adapter;

import android.content.Context;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;

public class TokenListAdapter extends BaseRecyclerAdapter<AssetsModel> {
    public TokenListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, AssetsModel item) {

    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return 0;
    }
}
