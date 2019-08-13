package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;

public class WalletItemView extends ConstraintLayout implements View.OnClickListener {

    private TextView mNameTv;
    private TextView mAddressTv;
    private ImageView mMenuIv;

    public WalletItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTv = findViewById(R.id.tv_wallet_name);
        mAddressTv = findViewById(R.id.tv_wallet_address);
        mMenuIv = findViewById(R.id.iv_wallet_menu);
        mMenuIv.setOnClickListener(this);
    }

    public void setData(DidHubKeyStore keyStore) {
        mNameTv.setText(keyStore.getWallet().getName());
        mAddressTv.setText(keyStore.getAddress());
    }

    public void setMenuIv(int menuIv) {
        mMenuIv.setImageResource(menuIv);
    }

    public void setMenuIvVisible(int visible) {
        mMenuIv.setVisibility(visible);
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
