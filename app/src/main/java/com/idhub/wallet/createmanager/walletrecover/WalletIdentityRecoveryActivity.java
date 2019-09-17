package com.idhub.wallet.createmanager.walletrecover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.createmanager.walletcreate.InputPasswordActivity;
import com.idhub.wallet.createmanager.walletimport.ImportWalletTopView;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.utils.ToastUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import wallet.idhub.com.clientlib.ApiFactory;

public class WalletIdentityRecoveryActivity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

    private LoadingAndErrorView mLoadingAndErrorView;
    private ImportWalletTopView mTopView;
    private static final String sMneMonicPath = BIP44Util.ETHEREUM_PATH;
    private String mPrivateKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_wallet_identity_recovery);
        initView();
    }

    private void initView() {
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
        findViewById(R.id.tv_import).setOnClickListener(this);
        mTopView = findViewById(R.id.top_view);
        mTopView.setData(getString(R.string.wallet_import_wallet_from_mnemonic_tip), getString(R.string.wallet_edit_mnemonic_tip));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_import:
                submit();
                break;
        }
    }

    private void submit() {
        String key = mTopView.getKey();
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showShortToast(getString(R.string.wallet_no_empty_mnemonic));
            return;
        }
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String privateKey = WalletManager.findPrivateKeyByMnemonic(key, sMneMonicPath);
                emitter.onNext(privateKey);
                emitter.onComplete();
            }
        })  .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        mLoadingAndErrorView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(String s) {
                        mPrivateKey = s;
                        //create wallet
                        InputPasswordActivity.startActionForResult(WalletIdentityRecoveryActivity.this,100);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(e.getMessage());
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        mLoadingAndErrorView.setVisibility(View.GONE);

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            InputDialogFragment instance = InputDialogFragment.getInstance("ein", getString(R.string.wallet_input_ein), InputType.TYPE_CLASS_TEXT);
            instance.show(getSupportFragmentManager(), "input_dialog_fragment");
            instance.setInputDialogFragmentListener(this);
        }
    }

    @Override
    public void inputConfirm(String ein, String source) {
//        ApiFactory.getIdentityChainLocal()
    }
}
