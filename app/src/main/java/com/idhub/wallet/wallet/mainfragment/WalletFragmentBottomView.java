package com.idhub.wallet.wallet.mainfragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.idhub.wallet.R;

public class WalletFragmentBottomView extends SwipeRefreshLayout {

    public WalletFragmentBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        RecyclerView recyclerView = findViewById(R.id.rv_assets);

    }
}
