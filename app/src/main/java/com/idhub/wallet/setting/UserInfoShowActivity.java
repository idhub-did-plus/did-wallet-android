package com.idhub.wallet.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.didhub.util.NumericUtil;

import org.web3j.crypto.Keys;

import java.math.BigInteger;

public class UserInfoShowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_user_info_show);
        init();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UserInfoShowActivity.class);
        context.startActivity(intent);
    }
    private void init() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_user_info));
        ImageView headView = findViewById(R.id.iv_user_head);
        UserBasicInfoEntity userBasicInfo = UserBasicInfoSharpreference.getInstance().getUserBasicInfo();
        Glide.with(this).load(userBasicInfo.headPath).apply(RequestOptions.bitmapTransform(new CircleCrop())).placeholder(R.mipmap.wallet_default_head).into(headView);

        TextView nameView = findViewById(R.id.tv_user_name);
        nameView.setText(userBasicInfo.name);
        TextView signatureView = findViewById(R.id.tv_user_signature);
        signatureView.setText(userBasicInfo.signature);

        View einLayoutView = findViewById(R.id.ll_EIN);
        TextView einView = findViewById(R.id.tv_user_ein);
        View didLayoutView = findViewById(R.id.ll_did);
        TextView didView = findViewById(R.id.tv_user_did);
        String ein = WalletOtherInfoSharpreference.getInstance().getEIN();
        if (TextUtils.isEmpty(ein)) {
            einLayoutView.setVisibility(View.GONE);
            didLayoutView.setVisibility(View.GONE);
        }else {
            einLayoutView.setVisibility(View.VISIBLE);
            didLayoutView.setVisibility(View.VISIBLE);
            einView.setText(ein);
            didView.setText("did:erc1484:" + DeployedContractAddress.IdentityRegistryInterface + ":" + NumericUtil.bigIntegerToHexWithZeroPadded(new BigInteger(ein), 64));

        }

        View recoveryLayoutView = findViewById(R.id.ll_recovery_address);
        TextView recoveryView = findViewById(R.id.tv_user_recovery_address);
        String recoverAddress = WalletOtherInfoSharpreference.getInstance().getRecoverAddress();
        if (TextUtils.isEmpty(recoverAddress)) {
            recoveryLayoutView.setVisibility(View.GONE);
        }else {
            recoveryLayoutView.setVisibility(View.VISIBLE);
            recoveryView.setText(Keys.toChecksumAddress(recoverAddress));
        }



    }
}
