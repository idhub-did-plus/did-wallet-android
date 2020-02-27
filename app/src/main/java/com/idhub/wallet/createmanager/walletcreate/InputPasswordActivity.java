package com.idhub.wallet.createmanager.walletcreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.DidHubIdentify;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.model.MnemonicAndPath;
import com.idhub.wallet.greendao.IdHubMessageDbManager;
import com.idhub.wallet.greendao.IdHubMessageType;
import com.idhub.base.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.utils.DateUtils;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.utils.ValidatorUtil;


import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InputPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText mPassword;
    private EditText mPasswordRepeat;
    private EditText mWalletName;
    private EditText mPasswordHint;
    private LoadingAndErrorView mLoadingAndErrorView;
    private boolean mIsSave;
    private String mPasswordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_input_password);
        initView();
        Intent intent = getIntent();
        mIsSave = intent.getBooleanExtra("isSave", true);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_create_wallet));
        View btnView = findViewById(R.id.tv_create);
        btnView.setOnClickListener(this);
        ViewCalculateUtil.setViewConstraintLayoutParam(btnView, ViewGroup.LayoutParams.MATCH_PARENT, 45, 50, 0, 30, 30);

        mWalletName = findViewById(R.id.et_wallet_name);
        ViewCalculateUtil.setViewConstraintLayoutParam(mWalletName, ViewGroup.LayoutParams.MATCH_PARENT, 50, 15, 0, 25, 25);
        mWalletName.setHint(getString(R.string.wallet_wallet_name));
        mPassword = findViewById(R.id.et_password);
        ViewCalculateUtil.setViewConstraintLayoutParam(mPassword, ViewGroup.LayoutParams.MATCH_PARENT, 50, 10, 0, 25, 25);
        mPassword.setHint(getString(R.string.wallet_password));
        mPasswordRepeat = findViewById(R.id.et_password_again);
        ViewCalculateUtil.setViewConstraintLayoutParam(mPasswordRepeat, ViewGroup.LayoutParams.MATCH_PARENT, 50, 10, 0, 25, 25);
        mPasswordRepeat.setHint(getString(R.string.wallet_password_again));
        mPasswordHint = findViewById(R.id.et_password_tip);
        ViewCalculateUtil.setViewConstraintLayoutParam(mPasswordHint, ViewGroup.LayoutParams.MATCH_PARENT, 50, 10, 0, 25, 25);
        mPasswordHint.setHint(getString(R.string.wallet_password_tip));
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);


        TextView createName = findViewById(R.id.tv_create_name);
        View contentTopView = findViewById(R.id.tv_warning_top);
        View contentBottomView = findViewById(R.id.tv_warning_bottom);
        ViewCalculateUtil.setViewConstraintLayoutParam(createName, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 20, 0, 25, 25);
        ViewCalculateUtil.setTextSize(createName,15);
        ViewCalculateUtil.setViewLinearLayoutParam(contentTopView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,5,0,25,25);
        ViewCalculateUtil.setViewLinearLayoutParam(contentBottomView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,5,0,25,25);
    }


    public static void startActionForResult(Context context, int requestCode,boolean isSave) {
        Intent intent = new Intent(context, InputPasswordActivity.class);
        intent.putExtra("isSave", isSave);
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
            ToastUtils.showShortToast(getString(R.string.wallet_no_empty_wallet_name));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast(getString(R.string.wallet_no_empty_password));
            return;
        }
        if (!ValidatorUtil.isPassword(password)) {
            ToastUtils.showShortToast(getString(R.string.wallet_password_length));
            return;
        }

        if (TextUtils.isEmpty(passwordRepeat)) {
            ToastUtils.showShortToast(getString(R.string.wallet_no_empty_repeat_password));
            return;
        }
        if (!password.equals(passwordRepeat)) {
            ToastUtils.showShortToast(getString(R.string.wallet_no_equal_password));
            return;
        }
        mPasswordStr = password;
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
                //判断第一次创建 设置IdHubMessage
                DidHubMnemonicKeyStore keyStore = didHubIdentify.mDidHubMnemonicKeyStore;
                if (keyStore != null) {
                    int walletNum = WalletManager.getWalletNum();
                    if (walletNum == 1) {
                        IdHubMessageEntity idHubMessageEntity = new IdHubMessageEntity();
                        idHubMessageEntity.setAddress(keyStore.getAddress());
                        idHubMessageEntity.setType(IdHubMessageType.CREATE_1056_Identity);
                        idHubMessageEntity.setTime(DateUtils.getCurrentDate());
                        new IdHubMessageDbManager().insertData(idHubMessageEntity, operation -> {
                            boolean completed = operation.isCompleted();
                        });
                    }
                    WalletInfo walletInfo = new WalletInfo(keyStore);
                    MnemonicAndPath mnemonicAndPath = walletInfo.exportMnemonic(password);
                    MnemonicBackupHintActivity.startActionforResult(InputPasswordActivity.this, mnemonicAndPath.getMnemonic(),100);
                } else {
                    ToastUtils.showShortToast("Wallet creation failed");
                }
                mLoadingAndErrorView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(getString(R.string.wallet_create_error));
                mLoadingAndErrorView.setVisibility(View.GONE);
                Log.e("LYW", "onError: "  + e.getMessage());
            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        };
        Observable.create((ObservableOnSubscribe<DidHubIdentify>) emitter -> {
            DidHubIdentify identity = DidHubIdentify.createIdentity(walletName, password, mPasswordHint.getText().toString(),mIsSave);
            emitter.onNext(identity);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            if (!mIsSave) {
                intent.putExtra("password", mPasswordStr);
            }
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
