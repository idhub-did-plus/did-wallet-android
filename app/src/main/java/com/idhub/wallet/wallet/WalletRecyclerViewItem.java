package com.idhub.wallet.wallet;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;

public class WalletRecyclerViewItem extends ConstraintLayout implements View.OnClickListener {

    public WalletRecyclerViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.iv_wallet_menu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_wallet_menu:
                WalletManagerActivity.startAction(getContext());
                break;
        }
    }
}
