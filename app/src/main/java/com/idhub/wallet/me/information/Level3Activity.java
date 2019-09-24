package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.idhub.magic.common.kvc.entity.ClaimOrder;
import com.idhub.magic.common.kvc.entity.ClaimType;
import com.idhub.magic.common.parameter.MagicResponse;
import com.idhub.magic.common.ustorage.entity.BuyerType;
import com.idhub.magic.common.ustorage.entity.FinancialProfile;
import com.idhub.magic.common.ustorage.entity.IdentityArchive;
import com.idhub.magic.common.ustorage.entity.InvestorType;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.UploadIDHubInfoDbManager;
import com.idhub.wallet.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.ToastUtils;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wallet.idhub.com.clientlib.ApiFactory;

public class Level3Activity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

    private TextView applyBtn;
    private RadioGroup mRadioGroup;
    private TextView mIdhubVip;
    private WalletKeystore mDefaultKeystore;
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mPrivateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);
        mDefaultKeystore = WalletManager.getDefaultKeystore();
        if (mDefaultKeystore == null) {
            mDefaultKeystore = WalletManager.getCurrentKeyStore();
            if (mDefaultKeystore == null) {
                finish();
                return;
            }
        }
        initView();
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
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
        initData();
    }

    private void initData() {
        String state = WalletVipSharedPreferences.getInstance().getQualifiedInvestorVipState();
        String content = WalletVipSharedPreferences.getInstance().getQualifiedInvestorVipContent();
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
        if (InvestorType.highIncome.name().equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_investor_condition_first));
        } else if (InvestorType.highAssets.name().equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_investor_condition_second));
        }
        mRadioGroup.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                WalletVipSharedPreferences instance = WalletVipSharedPreferences.getInstance();
                String idhubVipState = instance.getIdhubVipState();
                if (!VipStateType.HAVE_APPLY_FOR.equals(idhubVipState)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_have_apply_for_idhub_vip));
                    return;
                }
                if (TextUtils.isEmpty(mPrivateKey)) {
                    //输入密码
                    InputDialogFragment fragment = InputDialogFragment.getInstance("idhub_vip", getString(R.string.wallet_default_address_password), InputType.TYPE_CLASS_TEXT);
                    fragment.show(getSupportFragmentManager(), "input_dialog_fragment");
                    fragment.setInputDialogFragmentListener(this);
                } else {
                    applyClaim();
                }
                break;
        }
    }

    @Override
    public void inputConfirm(String data, String source) {
        mLoadingAndErrorView.onLoading();
        WalletInfo walletInfo = new WalletInfo(mDefaultKeystore);
        walletInfo.verifyPassword(data, new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                mLoadingAndErrorView.onGone();
                if (aBoolean) {
                    mPrivateKey = walletInfo.exportPrivateKey(data);
                    applyClaim();
                }else {
                    ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                }
            }

            @Override
            public void onError(Throwable e) {
                mLoadingAndErrorView.onGone();
                ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void applyClaim() {
        mLoadingAndErrorView.onLoading();
        if (applyBtn.getText().toString().equals(getString(R.string.wallet_submit))) {
            //提交数据
            int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();
            Log.e("LYW", "onClick: " +checkedRadioButtonId );
            FinancialProfile profile = new FinancialProfile();
            if (checkedRadioButtonId == R.id.qualified_investor_condition_first) {
                profile.investorType = InvestorType.highIncome.name();
            } else if (checkedRadioButtonId == R.id.qualified_investor_condition_second) {
                profile.investorType = InvestorType.highAssets.name();
            }else {
                mLoadingAndErrorView.onGone();
                ToastUtils.showShortToast(getString(R.string.wallet_select_item_apply));
                return;
            }
            IDHubCredentialProvider.setDefaultCredentials(mPrivateKey);
            ApiFactory.getArchiveStorage().storeFinancialProfile(profile, NumericUtil.prependHexPrefix(mDefaultKeystore.getAddress())).enqueue(new Callback<MagicResponse>() {
                @Override
                public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
                    mLoadingAndErrorView.onGone();
                    MagicResponse body = response.body();
                    if (body != null && body.isSuccess()) {
                        WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipContent(profile.investorType);
                        initData();
                        Log.e("LYW", "onResponse: " + body.getMessage());
                    } else {
                        ToastUtils.showShortToast(getString(R.string.wallet_claim_vip_fail));
                    }
                }

                @Override
                public void onFailure(Call<MagicResponse> call, Throwable t) {
                    mLoadingAndErrorView.onGone();
                    ToastUtils.showShortToast(getString(R.string.wallet_claim_vip_fail));
                }
            });
        }else {
            //申请
            ClaimOrder claimOrder = new ClaimOrder();
            claimOrder.identity = NumericUtil.prependHexPrefix(mDefaultKeystore.getAddress());
            claimOrder.requestedClaimType = ClaimType.qualified_investor.name();
            IDHubCredentialProvider.setDefaultCredentials(mPrivateKey);
            ApiFactory.getKycService().order(claimOrder,claimOrder.identity).enqueue(new Callback<MagicResponse>() {
                @Override
                public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
                    MagicResponse body = response.body();
                    if (body != null && body.isSuccess())  {
                        WalletVipSharedPreferences.getInstance().setIdhubVipState(VipStateType.APPLY_FOR_ING);
                        initData();
                        WalletVipStateObservable.getInstance().update();
                    }else {
                        ToastUtils.showShortToast(getString(R.string.wallet_claim_vip_fail));
                    }
                    mLoadingAndErrorView.onGone();
                }

                @Override
                public void onFailure(Call<MagicResponse> call, Throwable t) {
                    mLoadingAndErrorView.onGone();
                    ToastUtils.showShortToast(getString(R.string.wallet_claim_vip_fail));
                }
            });
        }
    }
}
