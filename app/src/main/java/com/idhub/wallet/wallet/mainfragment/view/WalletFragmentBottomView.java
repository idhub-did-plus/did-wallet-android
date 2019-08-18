package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.mainfragment.adapter.WalletAssetsAdapter;
import com.idhub.wallet.greendao.entity.AssetsModel;

import java.util.List;

public class WalletFragmentBottomView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {

    private WalletAssetsAdapter mAdapter;

    public WalletFragmentBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = findViewById(R.id.rv_assets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WalletAssetsAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
    }

    public void setData(List<AssetsModel> assetsModels) {
        mAdapter.addDatas(assetsModels);
    }

    @Override
    public void onRefresh() {
        mAdapter.notifyDataSetChanged();
    }
}
