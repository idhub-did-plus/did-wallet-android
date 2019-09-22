package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.idhub.magic.common.parameter.MagicResponse;
import com.idhub.magic.common.ustorage.entity.FinancialProfile;
import com.idhub.magic.common.ustorage.entity.IdentityArchive;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wallet.idhub.com.clientlib.ApiFactory;

public class Level3Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView applyBtn;
    private RadioGroup mRadioGroup;
    private TextView mIdhubVip;
    private String HIGH_INCOME = "high_income";
    private String HIGH_ASSETS = "high_assets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);
        initView();
        IdentityArchive archive = new IdentityArchive();
        FinancialProfile financialProfile = new FinancialProfile();
        financialProfile.highIncome = true;
        archive.setFinancialProfile(financialProfile);
        IDHubCredentialProvider.setDefaultCredentials(new WalletInfo(WalletManager.getDefaultKeystore()).exportPrivateKey("123"));
        String defaultAddress = WalletManager.getDefaultAddress();
        ApiFactory.getArchiveStorage().storeArchive(archive, defaultAddress).enqueue(new Callback<MagicResponse>() {
            @Override
            public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
                Log.e("LYW", "onResponse: " + response.body().isSuccess() );
            }

            @Override
            public void onFailure(Call<MagicResponse> call, Throwable t) {

            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, Level3Activity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_qualified_investor));
        mIdhubVip = findViewById(R.id.tv_vip);
        applyBtn = findViewById(R.id.tv_apply);
        mRadioGroup = findViewById(R.id.qualified_investor_condition);
        initData();
    }

    private void initData() {
        String state = WalletVipSharedPreferences.getInstance().getQualifiedInvestorVipState();
        String content = "";

        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            if (TextUtils.isEmpty(content)){
                applyBtn.setText(getString(R.string.wallet_submit));
                applyBtn.setOnClickListener(this);
                applyBtn.setBackgroundResource(R.drawable.wallet_shape_button);
            }else {
                applyBtn.setText(getString(R.string.wallet_apply_for));
                applyBtn.setBackgroundResource(R.drawable.wallet_shape_button);
                applyBtn.setOnClickListener(this);
                setApplyContent(content);
            }
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for_ing));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
            setApplyContent(content);
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_have_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
            setApplyContent(content);
        }

    }

    private void setApplyContent(String content) {
        mIdhubVip.setVisibility(View.VISIBLE);
        if (HIGH_INCOME.equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_investor_condition_first));
        } else if (HIGH_ASSETS.equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_investor_condition_second));
        }
        mRadioGroup.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:


                //请求，加载进行申请
                int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();
                Log.e("LYW", "onClick: " +checkedRadioButtonId );
                String type = "";
                if (checkedRadioButtonId == R.id.qualified_investor_condition_first) {
                    type = HIGH_INCOME;
                } else if (checkedRadioButtonId == R.id.qualified_investor_condition_second) {
                    type = HIGH_ASSETS;
                }else {
                    ToastUtils.showShortToast(getString(R.string.wallet_select_item_apply));
                    return;
                }
                WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipContent(type);
                WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.APPLY_FOR_ING);
                initData();
                WalletVipStateObservable.getInstance().update();
                break;
        }
    }
}
