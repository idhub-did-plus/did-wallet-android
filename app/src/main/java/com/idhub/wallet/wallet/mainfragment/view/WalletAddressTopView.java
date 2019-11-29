package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.wallet.info.WalletInfoActivity;
import com.idhub.wallet.wallet.transaction.ReceiveActivity;

import org.web3j.crypto.Keys;

public class WalletAddressTopView extends ConstraintLayout implements View.OnClickListener {

    private WalletKeystore walletKeystore;
    private TextView mWalletName;
    private TextView mAddressView;
    private TextView mDefaultAddressName;

    private TextView mUpgradeName;


    public WalletAddressTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mUpgradeName = findViewById(R.id.upgrade_name);

        mUpgradeName.setOnClickListener(this);
        mWalletName = findViewById(R.id.wallet_name);
        mAddressView = findViewById(R.id.wallet_address);
        mAddressView.setOnClickListener(this);
        mDefaultAddressName = findViewById(R.id.tv_default);
    }

    public void setData(WalletKeystore keyStore) {
        walletKeystore = keyStore;
        mWalletName.setText(keyStore.getWallet().getName());
        mAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(keyStore.getAddress())));
        boolean isgl = keyStore.getWallet().isAssociate();
        if (isgl) {
            mUpgradeName.setVisibility(INVISIBLE);
        } else {
            mUpgradeName.setVisibility(VISIBLE);
        }
        if (keyStore.getWallet().isDefaultAddress()) {
            mDefaultAddressName.setVisibility(VISIBLE);
        } else {
            mDefaultAddressName.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.upgrade_name:
                WalletInfoActivity.startAction(getContext(), walletKeystore.getId());
                break;
            case R.id.wallet_address:
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.setType(TransactionTokenType.ETH_NAME);
                assetsModel.setName(TransactionTokenType.ETH_NAME);
                assetsModel.setAddress(walletKeystore.getAddress());
                assetsModel.setDecimals("18");
                assetsModel.setSymbol(TransactionTokenType.ETH_NAME);
                ReceiveActivity.startAction(getContext(), assetsModel);
                break;
        }
    }

}
