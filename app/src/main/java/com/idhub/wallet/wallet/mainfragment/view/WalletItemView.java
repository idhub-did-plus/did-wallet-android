package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;

public class WalletItemView extends ConstraintLayout implements View.OnClickListener {

    private TextView mNameTv;
    private TextView mAddressTv;
    private ImageView mMenuIv;
    private DidHubKeyStore mKeyStore;
    private TextView mAssociatedAddress;
    private boolean clicked = true;
    private TextView mEINView;

    public WalletItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTv = findViewById(R.id.tv_wallet_name);
        mAddressTv = findViewById(R.id.tv_wallet_address);
        mMenuIv = findViewById(R.id.iv_wallet_menu);
        mAssociatedAddress = findViewById(R.id.tv_associated_address);
        mEINView = findViewById(R.id.tv_ein_number);
        mMenuIv.setOnClickListener(this);
    }

    public void setData(DidHubKeyStore keyStore) {
        this.mKeyStore = keyStore;
        mNameTv.setText(keyStore.getWallet().getName());
        mAddressTv.setText(NumericUtil.prependHexPrefix(keyStore.getAddress()));
        boolean isgl = keyStore.getWallet().isIsgl();
        Log.e("LYW", "setData: " + isgl);
        if (isgl) {
            mAssociatedAddress.setText(getContext().getString(R.string.wallet_associated_address));
            mAssociatedAddress.setTextColor(getContext().getResources().getColor(R.color.wallet_orange));
            mAssociatedAddress.setBackgroundResource(R.drawable.wallet_background_stroke_orange);
        } else {
            mAssociatedAddress.setText(getContext().getString(R.string.wallet_upgrade_associated_address));
            mAssociatedAddress.setTextColor(getContext().getResources().getColor(R.color.wallet_black));
            mAssociatedAddress.setBackgroundResource(R.drawable.wallet_background_stroke_black);
            mAssociatedAddress.setOnClickListener(this);
        }
        if (keyStore.getWallet().isDefaultAddress()) {
            mEINView.setText("defaultAddress");
        }else{
            mEINView.setText("");
        }
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setMenuIv(int menuIv) {
        mMenuIv.setImageResource(menuIv);
    }

    public void setMenuIvVisible(int visible) {
        mMenuIv.setVisibility(visible);
    }

    @Override
    public void onClick(View v) {
        if (clicked) {
            int id = v.getId();
            switch (id) {
                case R.id.iv_wallet_menu:
                case R.id.tv_associated_address:
                    //关联地址
                    WalletManagerActivity.startAction(getContext(), mKeyStore.getId());
                    break;
            }
        }
    }
}
