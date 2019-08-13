package com.idhub.wallet.identitymanager.identitycreate;

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
import com.idhub.wallet.didhub.DidHubIdentify;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.keystore.EncMnemonicKeystore;
import com.idhub.wallet.didhub.model.MnemonicAndPath;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.utils.ToastUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    public static void startAction(Context context) {
        Intent intent = new Intent(context, InputPasswordActivity.class);
        context.startActivity(intent);
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


        Observable.create((Observable.OnSubscribe<DidHubIdentify>) subscriber -> {
            subscriber.onStart();
            DidHubIdentify identity = DidHubIdentify.createIdentity(walletName, password, mPasswordHint.getText().toString());
            subscriber.onNext(identity);
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DidHubIdentify>() {
            @Override
            public void onStart() {
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCompleted() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DidHubIdentify didHubIdentify) {
                DidHubKeyStore keyStore = didHubIdentify.getKeyStore();
                if (keyStore != null) {
                    WalletInfo walletInfo = new WalletInfo(keyStore);
                    MnemonicAndPath mnemonicAndPath = walletInfo.exportMnemonic(password);
                    MnemonicBackupHintActivity.startAction(InputPasswordActivity.this, mnemonicAndPath.getMnemonic());
                } else {
                    ToastUtils.showShortToast("key is null");
                }
            }
        });
    }
}
