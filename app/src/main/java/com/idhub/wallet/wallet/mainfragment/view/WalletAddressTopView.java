package com.idhub.wallet.wallet.mainfragment.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.common.zxinglib.QrCodeActivity;
import com.idhub.wallet.common.zxinglib.widget.zing.MipcaActivityCapture;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.utils.UnitUtils;
import com.idhub.wallet.wallet.mainfragment.WalletListDialog;
import com.idhub.wallet.wallet.mainfragment.WalletListDialogFragment;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;
import com.idhub.wallet.wallet.transaction.ReceiveActivity;

import org.web3j.crypto.Keys;

public class WalletAddressTopView extends ConstraintLayout implements View.OnClickListener, WalletListDialog.WalletListSelectItemListener {

    private WalletKeystore walletKeystore;
    private TextView mWalletName;
    private TextView mAddressView;
    private TextView mDefaultAddressName;
    private ImageView mUpgradeIcon;
    private TextView mUpgradeName;
    private View mAddressBackGroundView;

    public WalletAddressTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.qr_code).setOnClickListener(this);
        findViewById(R.id.wallet_manage).setOnClickListener(this);
        findViewById(R.id.wallet_select).setOnClickListener(this);
        mUpgradeIcon = findViewById(R.id.upgrade_icon);
        mUpgradeName = findViewById(R.id.upgrade_name);
        mUpgradeIcon.setOnClickListener(this);
        mUpgradeName.setOnClickListener(this);
        mWalletName = findViewById(R.id.wallet_name);
        mAddressView = findViewById(R.id.wallet_address);
        mAddressView.setOnClickListener(this);
        mDefaultAddressName = findViewById(R.id.tv_default);
        mAddressBackGroundView = findViewById(R.id.ct_address_view);

    }

    public void setData(WalletKeystore keyStore) {
        walletKeystore = keyStore;
        mWalletName.setText(keyStore.getWallet().getName());
        mAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(keyStore.getAddress())));
        boolean isgl = keyStore.getWallet().isAssociate();
        if (isgl) {
            mUpgradeIcon.setVisibility(INVISIBLE);
            mUpgradeName.setVisibility(INVISIBLE);
            mAddressBackGroundView.setBackgroundResource(R.mipmap.wallet_association_wallet);
        } else {
            mAddressBackGroundView.setBackgroundResource(R.mipmap.wallet_normal_wallet);
            mUpgradeIcon.setVisibility(VISIBLE);
            mUpgradeName.setVisibility(VISIBLE);
        }
        if (keyStore.getWallet().isDefaultAddress()) {
            ConstraintLayout.LayoutParams layoutParams = (LayoutParams) mAddressView.getLayoutParams();
            layoutParams.topMargin = UnitUtils.dp2px(0);
            mAddressView.setLayoutParams(layoutParams);
            mDefaultAddressName.setVisibility(VISIBLE);
        } else {
            ConstraintLayout.LayoutParams layoutParams = (LayoutParams) mAddressView.getLayoutParams();
            layoutParams.topMargin = UnitUtils.dp2px(11);
            mAddressView.setLayoutParams(layoutParams);
            mDefaultAddressName.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.qr_code:
                QrCodeActivity.startAction(((Activity) getContext()), 100);
                break;
            case R.id.wallet_manage:
            case R.id.upgrade_icon:
            case R.id.upgrade_name:
                WalletManagerActivity.startAction(getContext(), walletKeystore.getId());
                break;
            case R.id.wallet_select:
                WalletListDialog walletListDialog = new WalletListDialog(getContext(), walletKeystore.getAddress());
                walletListDialog.setWalletListSelectItemListener(this);
                walletListDialog.show();
                break;
            case R.id.wallet_address:
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.setType(AssetsDefaultType.ETH_NAME);
                assetsModel.setName(AssetsDefaultType.ETH_NAME);
                assetsModel.setAddress(walletKeystore.getAddress());
                assetsModel.setDecimals("18");
                assetsModel.setSymbol(AssetsDefaultType.ETH_NAME);
                ReceiveActivity.startAction(getContext(), assetsModel);
                break;
        }
    }

    @Override
    public void selectItem(String id) {
        boolean b = WalletOtherInfoSharpreference.getInstance().setSelectedId(id);
        if (b)
            WalletSelectedObservable.getInstance().update();
    }
}
