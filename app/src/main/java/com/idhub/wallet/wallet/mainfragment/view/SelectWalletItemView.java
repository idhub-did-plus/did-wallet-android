package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.wallet.info.WalletInfoActivity;

import org.web3j.crypto.Keys;

public class SelectWalletItemView extends ConstraintLayout implements View.OnClickListener {

    private WalletKeystore walletKeystore;
    private TextView walletName;
    private TextView walletAddress;
    private TextView defaultWalletView;
    private ImageView upgradeImageView;
    private View stateView;

    public SelectWalletItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        stateView = findViewById(R.id.state_view);
        walletName = findViewById(R.id.tv_wallet_name);
        walletAddress = findViewById(R.id.tv_wallet_address);
        defaultWalletView = findViewById(R.id.tv_default_address);
        upgradeImageView = findViewById(R.id.upgrade_image_view);
        upgradeImageView.setOnClickListener(this);
    }

    public void setData(WalletKeystore keyStore) {
        walletKeystore = keyStore;
        walletName.setText(keyStore.getWallet().getName());
        walletAddress.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(keyStore.getAddress())));
        boolean isgl = keyStore.getWallet().isAssociate();
        if (isgl) {
            upgradeImageView.setVisibility(GONE);
        } else {
            upgradeImageView.setVisibility(VISIBLE);
            upgradeImageView.setOnClickListener(this);
        }
        boolean defaultAddress = keyStore.getWallet().isDefaultAddress();
        Log.e("LYW", "setData: " + defaultAddress );
        if (defaultAddress) {
            defaultWalletView.setVisibility(VISIBLE);
        } else {
            defaultWalletView.setVisibility(GONE);
        }
    }

    public void setItemIsSelect(boolean selected) {
        if (selected) {
            stateView.setVisibility(VISIBLE);
            walletName.setTextColor(getResources().getColor(R.color.wallet_main_blue_color));
            walletAddress.setTextColor(getResources().getColor(R.color.wallet_main_blue_color));
            defaultWalletView.setTextColor(getResources().getColor(R.color.wallet_light_blue_color));
            defaultWalletView.setBackground(getResources().getDrawable(R.drawable.wallet_rectangle_line_blue_4));
            upgradeImageView.setImageResource(R.mipmap.wallet_upgrade_blue_icon);
        } else {
            stateView.setVisibility(GONE);
            walletName.setTextColor(getResources().getColor(R.color.wallet_text_gray_color));
            walletAddress.setTextColor(getResources().getColor(R.color.wallet_text_gray_color));
            defaultWalletView.setTextColor(getResources().getColor(R.color.wallet_text_gray_color));
            defaultWalletView.setBackground(getResources().getDrawable(R.drawable.wallet_rectangle_line_4));
            upgradeImageView.setImageResource(R.mipmap.wallet_upgrade_black_icon);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.upgrade_image_view:
                if (walletKeystore != null)
                    WalletInfoActivity.startAction(getContext(), walletKeystore.getId());
                break;
        }
    }
}
