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
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.ToastUtils;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.idhub.magic.clientlib.ApiFactory;

public class Level2Activity extends BaseActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {


    private TextView applyBtn;
    private WalletKeystore mDefaultKeystore;
    private LoadingAndErrorView mLoadingAndErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
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
        Intent intent = new Intent(context, Level2Activity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_idhub_super_vip));
        applyBtn = findViewById(R.id.tv_apply);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
        initData();
    }

    private void initData() {
        String state = WalletVipSharedPreferences.getInstance().getIdhubSuperVipState();
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button);
            applyBtn.setOnClickListener(this);
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for_ing));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_have_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        }else if (VipStateType.REFUSED_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_again_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button);
            applyBtn.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                //请求，加载进行申请
                WalletVipSharedPreferences instance = WalletVipSharedPreferences.getInstance();
                String idhubVipState = instance.getIdhubVipState();
                if (!VipStateType.HAVE_APPLY_FOR.equals(idhubVipState)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_have_apply_for_idhub_vip));
                    return;
                }
                InputDialogFragment fragment = InputDialogFragment.getInstance("idhub_vip", getString(R.string.wallet_default_address_password), InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                fragment.show(getSupportFragmentManager(), "input_dialog_fragment");
                fragment.setInputDialogFragmentListener(this);

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
                if (aBoolean) {
                    //请求，加载进行申请
                    ClaimOrder claimOrder = new ClaimOrder();
                    claimOrder.identity =WalletManager.getDefaultAddress();
                    claimOrder.requestedClaimType = ClaimType.IDHub_SVIP.name();
                    IDHubCredentialProvider.setDefaultCredentials(walletInfo.exportPrivateKey(data));
                    ApiFactory.getKycService().order(claimOrder,WalletManager.getDefaultAddress()).enqueue(new Callback<MagicResponse>() {
                        @Override
                        public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
                            MagicResponse body = response.body();
                            if (body != null && body.isSuccess())  {
                                WalletVipSharedPreferences.getInstance().setIdhubSuperVipState(VipStateType.APPLY_FOR_ING);
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
                }else {
                    mLoadingAndErrorView.onGone();
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
