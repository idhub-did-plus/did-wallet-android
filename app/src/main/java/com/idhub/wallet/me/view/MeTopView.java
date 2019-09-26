package com.idhub.wallet.me.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.me.information.UploadInformationTypeActivity;

import java.math.BigInteger;

public class MeTopView extends ConstraintLayout implements View.OnClickListener {

    private TextView mRecoverAddressView;
    private TextView mEINIdentityView;
    private TextView mEINView;

    public MeTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView headView = findViewById(R.id.iv_head);
        UserBasicInfoEntity userBasicInfo = UserBasicInfoSharpreference.getInstance().getUserBasicInfo();
        Glide.with(getContext()).load(userBasicInfo.headPath).apply(RequestOptions.bitmapTransform(new CircleCrop())).placeholder(R.mipmap.wallet_default_head).into(headView);
        TextView name = findViewById(R.id.tv_name);
        name.setText(userBasicInfo.name);
        TextView singature = findViewById(R.id.tv_signature);
        singature.setText(userBasicInfo.signature);

        mRecoverAddressView = findViewById(R.id.tv_recover_address);

        findViewById(R.id.upload_file).setOnClickListener(this);
        mEINIdentityView = findViewById(R.id.tv_ein_number);
        mEINView = findViewById(R.id.tv_ein);
    }

    public void setRecoverAddress(String recoverAddress) {
        setRecoverAddressViewVisible(VISIBLE);
        mRecoverAddressView.setText(getContext().getString(R.string.wallet_recovery_address)+":" + recoverAddress);
    }

    public void setEIN1484(String ein) {
        setEINVisible(View.VISIBLE);
        mEINView.setText(getContext().getString(R.string.wallet_ein)+":"+ein);
        mEINIdentityView.setText("did:erc1484:" + DeployedContractAddress.IdentityRegistryInterface + ":" + NumericUtil.bigIntegerToHexWithZeroPadded(new BigInteger(ein), 64));
    }

    public void setEIN1056(String address) {
        setEINVisible(View.VISIBLE);
        mEINView.setText(getContext().getString(R.string.wallet_ein)+":0");
        mEINIdentityView.setText("did:erc1056:" + DeployedContractAddress.EthereumDIDRegistryInterface + ":" + address);
    }

    public void setEINVisible(int visibility){
        mEINIdentityView.setVisibility(visibility);
    }

    public void setRecoverAddressViewVisible(int visibility){
        mRecoverAddressView.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.upload_file:

                UploadInformationTypeActivity.startAction(getContext());
                break;
        }
    }
}
