package com.idhub.wallet.setting.message.idhub;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.IdHubMessageType;
import com.idhub.wallet.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;
import com.idhub.wallet.setting.TransactionDetailActivity;

import org.web3j.crypto.Keys;

import java.math.BigInteger;

public class IdHubMessageItemView extends ConstraintLayout {


    private TextView mTypeView;
    private TextView mAddressView;
    private TextView mDefaultAddressNameView;
    private TextView mDefaultAddressView;
    private TextView mRecoverAddressNameView;
    private TextView mRecoverAddressView;
    private TextView mEINNameView;
    private TextView mEINView;
    private TextView mTimeView;

    public IdHubMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTypeView = findViewById(R.id.message_type);
        mAddressView = findViewById(R.id.address);
        mDefaultAddressNameView = findViewById(R.id.default_address_name);
        mDefaultAddressView = findViewById(R.id.default_address);
        mRecoverAddressNameView = findViewById(R.id.recover_address_name);
        mRecoverAddressView = findViewById(R.id.recover_address);
        mEINNameView = findViewById(R.id.ein_name);
        mEINView = findViewById(R.id.ein);
        mTimeView = findViewById(R.id.time);
    }

    public void setData(IdHubMessageEntity idHubMessageEntity) {
        String address = idHubMessageEntity.getAddress();
        mAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(address)));
        String type = idHubMessageEntity.getType();
        switch (type) {
            case IdHubMessageType.CREATE_1056_Identity:
                mTypeView.setText(getContext().getString(R.string.wallet_create_1056_identity));
                break;
            case IdHubMessageType.UPGRADE_1484_IDENTITY:
                mTypeView.setText(getContext().getString(R.string.wallet_upgrade_1484_identity));
                break;
            case IdHubMessageType.IMPORT_1056_IDENTITY:
                mTypeView.setText(getContext().getString(R.string.wallet_import_1056_identity));
                break;
            case IdHubMessageType.IMPORT_1484_IDENTITY:
                idHubMessageEntity.setRecoverAddress(WalletOtherInfoSharpreference.getInstance().getRecoverAddress());
                idHubMessageEntity.setEin(WalletOtherInfoSharpreference.getInstance().getEIN());
                mTypeView.setText(getContext().getString(R.string.wallet_import_1484_identity));
                break;
            case IdHubMessageType.UPGRADE_ASSOCIATION_IDENTITY:
                mTypeView.setText(getContext().getString(R.string.wallet_upgrade_association_identity));
                break;
            case IdHubMessageType.RECOVERY_IDENTITY:
                mTypeView.setText(getContext().getString(R.string.wallet_recovery_identity));
                break;
        }
        String defaultAddress = idHubMessageEntity.getDefaultAddress();
        if (!TextUtils.isEmpty(defaultAddress)) {
            mDefaultAddressView.setVisibility(VISIBLE);
            mDefaultAddressNameView.setVisibility(VISIBLE);
            mDefaultAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(defaultAddress)));
        } else {
            mDefaultAddressView.setVisibility(GONE);
            mDefaultAddressNameView.setVisibility(GONE);
        }
        String recoverAddress = idHubMessageEntity.getRecoverAddress();
        if (!TextUtils.isEmpty(recoverAddress)) {
            mRecoverAddressView.setVisibility(VISIBLE);
            mRecoverAddressNameView.setVisibility(VISIBLE);
            mRecoverAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(recoverAddress)));
        } else {
            mRecoverAddressView.setVisibility(GONE);
            mRecoverAddressNameView.setVisibility(GONE);
        }
        String ein = idHubMessageEntity.getEin();
        if (!TextUtils.isEmpty(ein)) {
            mEINNameView.setVisibility(VISIBLE);
            mEINView.setVisibility(VISIBLE);
            mEINView.setText(ein);
        } else {
            mEINNameView.setVisibility(GONE);
            mEINView.setVisibility(GONE);
        }
        String time = idHubMessageEntity.getTime();
        mTimeView.setText(time);
    }


}
