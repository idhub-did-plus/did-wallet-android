package com.idhub.wallet.createmanager.walletcreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.idhub.wallet.R;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.createmanager.WalletCreateChannel;
import com.idhub.wallet.didhub.DidHubIdentify;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.model.MnemonicAndPath;
import com.idhub.wallet.utils.ToastUtils;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InputPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPassword;
    private EditText mPasswordRepeat;
    private EditText mWalletName;
    private EditText mPasswordHint;
    private LoadingAndErrorView mLoadingAndErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_input_password);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_create_wallet));
        findViewById(R.id.tv_create).setOnClickListener(this);
        mWalletName = findViewById(R.id.et_wallet_name);
        mPassword = findViewById(R.id.et_password);
        mPasswordRepeat = findViewById(R.id.et_password_again);
        mPasswordHint = findViewById(R.id.et_password_tip);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }

    public static void startActionForResult(Context context, int requestCode) {
        Intent intent = new Intent(context, InputPasswordActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_create:
                checkoutPasswordAndCreate();
                break;
        }
    }

    private void checkoutPasswordAndCreate() {
        String walletName = mWalletName.getText().toString();
        String password = mPassword.getText().toString();
        String passwordRepeat = mPasswordRepeat.getText().toString();
        if (TextUtils.isEmpty(walletName)) {
            ToastUtils.showShortToast("钱包名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(passwordRepeat)) {
            ToastUtils.showShortToast("重复密码不能为空");
            return;
        }
        if (!password.equals(passwordRepeat)) {
            ToastUtils.showShortToast("两次密码不一致");
            return;
        }
        createWallet(walletName, password);
    }

    private void createWallet(String walletName, String password) {
        DisposableObserver<DidHubIdentify> observer = new DisposableObserver<DidHubIdentify>() {

            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(DidHubIdentify didHubIdentify) {
                DidHubKeyStore keyStore = didHubIdentify.mDidHubKeyStore;
                if (keyStore != null) {
                    WalletInfo walletInfo = new WalletInfo(keyStore);
                    String id = walletInfo.getId();
                    MnemonicAndPath mnemonicAndPath = walletInfo.exportMnemonic(password);
                    MnemonicBackupHintActivity.startAction(InputPasswordActivity.this, mnemonicAndPath.getMnemonic(), id);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtils.showShortToast("Wallet creation failed");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        };
        Observable.create((ObservableOnSubscribe<DidHubIdentify>) emitter -> {
            DidHubIdentify identity = DidHubIdentify.createIdentity(walletName, password, mPasswordHint.getText().toString());
            emitter.onNext(identity);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
