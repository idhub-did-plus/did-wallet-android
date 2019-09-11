package com.idhub.wallet.me.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idhub.magic.center.contracts.IdentityRegistryInterface;
import com.idhub.magic.center.service.DeployedContractAddress;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.me.information.UploadFileActivity;
import com.idhub.wallet.me.information.UploadInformationTypeActivity;

public class MeTopView extends ConstraintLayout implements View.OnClickListener {

    private TextView mRecoverAddressView;
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
        mEINView = findViewById(R.id.tv_ein_number);
    }

    public void setRecoverAddress(String recoverAddress) {
        setRecoverAddressViewVisible(VISIBLE);
        mRecoverAddressView.setText("recoverAddress:" + recoverAddress);
    }

    public void setEIN1484(String ein) {
        setEINVisible(View.VISIBLE);
        mEINView.setText("did:erc1484:" + DeployedContractAddress.IdentityRegistryInterface + ":" + ein);
    }

    public void setEIN1056(String address) {
        setEINVisible(View.VISIBLE);
        mEINView.setText("did:erc1056:" + DeployedContractAddress.EthereumDIDRegistryInterface + ":" + address);
    }

    public void setEINVisible(int visibility){
        mEINView.setVisibility(visibility);
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
