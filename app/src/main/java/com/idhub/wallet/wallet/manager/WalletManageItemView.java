package com.idhub.wallet.wallet.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.wallet.info.WalletInfoActivity;

import org.web3j.crypto.Keys;

public class WalletManageItemView extends LinearLayout implements View.OnClickListener {

    private TextView nameView;
    private View upgradeView;
    private TextView addressView;
    private View defaultAddressView;
    private WalletKeystore store;

    public WalletManageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        nameView = findViewById(R.id.wallet_name);
        upgradeView = findViewById(R.id.upgrade_icon);
        addressView = findViewById(R.id.wallet_address);
        defaultAddressView = findViewById(R.id.tv_default);
        findViewById(R.id.ct_address_view).setOnClickListener(this);
    }

    public void setData(WalletKeystore store) {
        this.store = store;
        Wallet wallet = store.getWallet();
        nameView.setText(wallet.getName());
        addressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(store.getAddress())));
        boolean associate = wallet.isAssociate();
        if (associate) {
            upgradeView.setVisibility(GONE);
        } else {
            upgradeView.setVisibility(VISIBLE);
        }
        if (store.getWallet().isDefaultAddress()) {
            defaultAddressView.setVisibility(VISIBLE);
        } else {
            defaultAddressView.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ct_address_view:
                if (store != null)
                    WalletInfoActivity.startAction(getContext(), store.getId());
                break;
        }
    }
}
