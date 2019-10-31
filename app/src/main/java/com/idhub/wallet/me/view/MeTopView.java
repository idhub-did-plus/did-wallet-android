package com.idhub.wallet.me.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.me.information.UploadInformationTypeActivity;
import com.idhub.wallet.setting.SettingsActivity;
import com.idhub.wallet.setting.UserInfoShowActivity;

import org.web3j.crypto.Keys;

import java.math.BigInteger;

public class MeTopView extends ConstraintLayout implements View.OnClickListener {

    private TextView mRecoverAddressView;
    private TextView mEINIdentityView;
    private TextView mEINView;
    private ImageView headView;
    private TextView name;
    private TextView singature;

    public MeTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headView = findViewById(R.id.iv_head);
        name = findViewById(R.id.tv_name);
        singature = findViewById(R.id.tv_signature);
        setUserInfo();
        findViewById(R.id.iv_setting).setOnClickListener(this);
        mRecoverAddressView = findViewById(R.id.tv_recover_address);
        mEINIdentityView = findViewById(R.id.tv_ein_number);
        mEINView = findViewById(R.id.tv_ein);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoShowActivity.startAction(getContext());
            }
        });
    }

    public void setUserInfo() {
        UserBasicInfoEntity userBasicInfo = UserBasicInfoSharpreference.getInstance().getUserBasicInfo();
        Glide.with(this).load(userBasicInfo.headPath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(headView);
        name.setText(userBasicInfo.name);
        singature.setText(userBasicInfo.signature);
    }

    public void setRecoverAddress(String recoverAddress) {
        setRecoverAddressViewVisible(VISIBLE);
        mRecoverAddressView.setText(getContext().getString(R.string.wallet_recovery_address)+":" + Keys.toChecksumAddress(recoverAddress));
    }

    public void setEIN1484(String ein) {
        setEINVisible(View.VISIBLE);
        mEINView.setText(getContext().getString(R.string.wallet_ein)+":"+ein);
        mEINIdentityView.setText("did:erc1484:" + DeployedContractAddress.IdentityRegistryInterface + ":" + NumericUtil.bigIntegerToHexWithZeroPadded(new BigInteger(ein), 64));
    }

    public void setEIN1056(String address) {
        setEINVisible(View.VISIBLE);
        mEINView.setVisibility(GONE);
        mEINIdentityView.setText("did:erc1056:" + DeployedContractAddress.EthereumDIDRegistryInterface + ":" + address);
    }

    public void setEINVisible(int visibility){
        mEINView.setVisibility(visibility);
        mEINIdentityView.setVisibility(visibility);
    }

    public void setRecoverAddressViewVisible(int visibility){
        mRecoverAddressView.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting:
                SettingsActivity.startAction(getContext());
                break;
        }
    }
}
