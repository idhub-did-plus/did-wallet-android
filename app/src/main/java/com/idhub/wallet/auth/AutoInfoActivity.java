package com.idhub.wallet.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.title.TitleLayout;

public class AutoInfoActivity extends BaseActivity {

    private BaseInformationLayout mBaseInformationLayout;
    private IdentityInformationLayout mIdentityInformationLayout;
    private TaxpayerInformationLayout mTaxpayerInformationLayout;
    private CreditInformationLayout mCreditInformationLayout;
    private AddressInformationLayout mAddressInformationLayout;
    private AssetsInformationLayout mAssetsInformationLayout;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AutoInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_auto_info);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_id_hub_information_title));

        InformationContentView baseInfoView = findViewById(R.id.base_info);
        mBaseInformationLayout = new BaseInformationLayout(this);
        baseInfoView.setContent(R.mipmap.wallet_base_info_icon, getString(R.string.basic_information), mBaseInformationLayout);

        InformationContentView identityInfoView = findViewById(R.id.identity_info);
        mIdentityInformationLayout = new IdentityInformationLayout(this);
        identityInfoView.setContent(R.mipmap.wallet_identity_info_icon, getString(R.string.identity_information), mIdentityInformationLayout);

        InformationContentView taxpayerInfoView = findViewById(R.id.taxpayer_info);
        mTaxpayerInformationLayout = new TaxpayerInformationLayout(this);
        taxpayerInfoView.setContent(R.mipmap.wallet_tax_info_icon, getString(R.string.taxpayer_information), mTaxpayerInformationLayout);

        InformationContentView creditInfoView = findViewById(R.id.credit_info);
        mCreditInformationLayout = new CreditInformationLayout(this);
        creditInfoView.setContent(R.mipmap.wallet_credit_info_icon, getString(R.string.credit_information), mCreditInformationLayout);

        InformationContentView addressInfoView = findViewById(R.id.address_info);
        mAddressInformationLayout = new AddressInformationLayout(this);
        addressInfoView.setContent(R.mipmap.wallet_address_info_icon, getString(R.string.address_information), mAddressInformationLayout);

        InformationContentView proofInfoView = findViewById(R.id.assets_info);
        mAssetsInformationLayout = new AssetsInformationLayout(this);
        proofInfoView.setContent(R.mipmap.wallet_assets_info_icon, getString(R.string.assets_information), mAssetsInformationLayout);


    }
}
