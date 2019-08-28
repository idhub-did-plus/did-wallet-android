package com.idhub.wallet.createmanager;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.idhub.magic.center.contracts.IdentityRegistryInterface;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.createmanager.walletcreate.MnemonicBackupHintActivity;
import com.idhub.wallet.didhub.RecoverAddress;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.MnemonicUtil;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.ToastUtils;

import java.math.BigInteger;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.interfaces.ExceptionListener;
import wallet.idhub.com.clientlib.interfaces.Listen;
import wallet.idhub.com.clientlib.interfaces.ResultListener;

public class UpgradeActivity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

    private String mData;
    private String mRecoverAddressStr;
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upgrade);
        initView();
        initData();
    }

    private void initData() {
        mData = getIntent().getStringExtra("data");
    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, UpgradeActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_upgrade));
        findViewById(R.id.tv_upgrade).setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_upgrade:
                //身份升级需要先验证输入用户密码
                inputVerifyPassword();
                break;
        }
    }

    private void inputVerifyPassword() {
        InputDialogFragment instance = InputDialogFragment.getInstance("upgrade", getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
    }

    private void createRecoverAddressAndMnemonic() {
        List<String> mnemonicCodes = MnemonicUtil.randomMnemonicCodes();
        RecoverAddress recoverAddress = new RecoverAddress(mnemonicCodes);
        mRecoverAddressStr = NumericUtil.prependHexPrefix(recoverAddress.getAddress());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mnemonicCodes.size(); i++) {
            stringBuilder.append(mnemonicCodes.get(i)).append(" ");
        }
        MnemonicBackupHintActivity.startActionforResult(this, stringBuilder.toString(), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            WalletInfo walletInfo = new WalletInfo(WalletManager.getKeyStore(mData));
            String privateKey = walletInfo.exportPrivateKey(mPwd);
            IDHubCredentialProvider.setDefaultCredentials(privateKey);
            IDHubCredentialProvider.setRecoverAddress(mRecoverAddressStr);
            Listen<IdentityRegistryInterface.IdentityCreatedEventResponse> identity = ApiFactory.getIdentityChainLocal().createIdentity();
            //error
            identity.listen(identityCreatedEventResponse -> {
                BigInteger ein = identityCreatedEventResponse.ein;
                //success
                //备份成功进行身份升级注册 。身份升级只能是有第一个address的时候，升级成功设置address为defaultAddress
                WalletOtherInfoSharpreference.getInstance().setRecoverAdress(mRecoverAddressStr);
                WalletOtherInfoSharpreference.getInstance().setEIN(NumericUtil.bigIntegerToHexWithZeroPadded(ein,64));
                DidHubKeyStore keyStore = WalletManager.getKeyStore(mData);
                Wallet wallet = keyStore.getWallet();
                wallet.setAssociate(true);
                wallet.setDefaultAddress(true);
                WalletManager.flushWallet(keyStore, true);
                MainActivity.startAction(UpgradeActivity.this, "upgrade");
            }, message -> ToastUtils.showShortToast(message));
        }
    }

    @Override
    public void inputConfirm(String data, String source) {
        WalletInfo walletInfo = new WalletInfo(WalletManager.getKeyStore(mData));
        walletInfo.verifyPassword(data, new DisposableObserver<Boolean>() {
            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    mPwd = data;
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    //生成恢复地址recoverAddress和助记词。助记词备份完之后提交create 成功之后保存recoverAddress
                    createRecoverAddressAndMnemonic();
                } else {
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
