package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.idhub.magic.common.kvc.entity.ClaimOrder;
import com.idhub.magic.common.kvc.entity.ClaimType;
import com.idhub.magic.common.parameter.MagicResponse;
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
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.ToastUtils;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.idhub.magic.clientlib.ApiFactory;

public class Level5Activity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {
    private TextView applyBtn;
    private LoadingAndErrorView mLoadingAndErrorView;
    private WalletKeystore mDefaultKeystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);
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
        Intent intent = new Intent(context, Level5Activity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_idhub_vip));
        TextView idhubVip = findViewById(R.id.tv_idhub_vip);
        applyBtn = findViewById(R.id.tv_apply);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
        initData();
    }

    private void initData() {
        String state = WalletVipSharedPreferences.getInstance().getComplianceInvestorVipState();
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for));
            applyBtn.setOnClickListener(this);
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for_ing));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_have_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                //请求，加载进行申请
                WalletVipSharedPreferences instance = WalletVipSharedPreferences.getInstance();
                String qualifiedInvestorVipState = instance.getQualifiedInvestorVipState();
                String qualifiedPurchaserVipState = instance.getQualifiedPurchaserVipState();
                if (!VipStateType.HAVE_APPLY_FOR.equals(qualifiedInvestorVipState)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_have_apply_for_qualified_investor_vip));
                    return;
                }
                if (!VipStateType.HAVE_APPLY_FOR.equals(qualifiedPurchaserVipState)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_have_apply_for_qualified_purchaser_vip));
                    return;
                }
                InputDialogFragment fragment = InputDialogFragment.getInstance("idhub_vip", getString(R.string.wallet_default_address_password),InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                fragment.show(getSupportFragmentManager(), "input_dialog_fragment");
                fragment.setInputDialogFragmentListener(this);
                break;
        }
    }

    private void applyClaim(String privateKey) {
        ClaimOrder claimOrder = new ClaimOrder();
        claimOrder.identity = NumericUtil.prependHexPrefix(mDefaultKeystore.getAddress());
        claimOrder.requestedClaimType = ClaimType.qualified_buyer.name();
        IDHubCredentialProvider.setDefaultCredentials(privateKey);
        ApiFactory.getKycService().order(claimOrder,claimOrder.identity).enqueue(new Callback<MagicResponse>() {
            @Override
            public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
                MagicResponse body = response.body();
                if (body != null && body.isSuccess())  {
                    WalletVipSharedPreferences.getInstance().setComplianceInvestorVipState(VipStateType.APPLY_FOR_ING);
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

    @Override
    public void inputConfirm(String data, String source) {
        mLoadingAndErrorView.onLoading();
        WalletInfo walletInfo = new WalletInfo(mDefaultKeystore);
        walletInfo.verifyPassword(data, new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                mLoadingAndErrorView.onGone();
                if (aBoolean) {
                    applyClaim(walletInfo.exportPrivateKey(data));
                } else {
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
}
