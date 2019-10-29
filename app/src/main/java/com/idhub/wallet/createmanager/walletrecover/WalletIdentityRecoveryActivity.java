package com.idhub.wallet.createmanager.walletrecover;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.Identity1484To1056BindSharedPreferences;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.createmanager.walletcreate.InputPasswordActivity;
import com.idhub.wallet.createmanager.walletimport.ImportWalletTopView;
import com.idhub.wallet.didhub.DidHubIdentify;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.address.EthereumAddressCreator;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.greendao.IdHubMessageDbManager;
import com.idhub.wallet.greendao.IdHubMessageType;
import com.idhub.base.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.DateUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import com.idhub.magic.clientlib.ApiFactory;

public class WalletIdentityRecoveryActivity extends BaseActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

    private LoadingAndErrorView mLoadingAndErrorView;
    private ImportWalletTopView mTopView;
    private static final String sMneMonicPath = BIP44Util.ETHEREUM_PATH;
    private String mPrivateKey;
    private String mRecoveryAddress;
    private WalletKeystore mWalletKeystore;
    private String mPassword;

    public static void startActionForResult(Activity activity,int requestCode) {
        Intent intent = new Intent(activity, WalletIdentityRecoveryActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
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
        mTopView.setData(getString(R.string.wallet_recovery_identity_from_mnemonic_tip), getString(R.string.wallet_edit_mnemonic_tip));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_import:
                //TODO:暂时这么先写 判断当前节点没有合约地址
                String identityRegistryInterface = DeployedContractAddress.IdentityRegistryInterface;
                if (TextUtils.isEmpty(identityRegistryInterface)) {
                    Toast.makeText(this, getString(R.string.wallet_none_contract_address), Toast.LENGTH_SHORT).show();
                    return;
                }
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
                        mRecoveryAddress = new EthereumAddressCreator().fromPrivateKey(s);
                        //create wallet
                        if (mWalletKeystore == null) {
                            InputPasswordActivity.startActionForResult(WalletIdentityRecoveryActivity.this,100,false);
                        }else {
                            inputEIN();
                        }
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
            if (data != null) {
                mPassword = data.getStringExtra("password");
            }
            mWalletKeystore = DidHubIdentify.mDidHubMnemonicKeyStore;
            if (mWalletKeystore == null) {
                finish();
                return;
            }
            inputEIN();
        }
    }

    private void inputEIN() {
        InputDialogFragment instance = InputDialogFragment.getInstance("ein", getString(R.string.wallet_input_ein_recovery_identity), InputType.TYPE_CLASS_TEXT);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener(this);
    }

    @Override
    public void inputConfirm(String ein, String source) {
        String address = mWalletKeystore.getAddress();
        Log.e("LYW", "inputConfirm: " + address );
        mLoadingAndErrorView.setVisibility(View.VISIBLE);
        IDHubCredentialProvider.setDefaultCredentials(mPrivateKey);
        ApiFactory.getIdentityChainLocal().recoveryIdentity(ein, address, new WalletInfo(mWalletKeystore).exportPrivateKey(mPassword)).listen(rst -> {
            //恢复成功保存钱包
            Log.e("LYW", "inputConfirm:recoverySuccess ");
            try {
                Boolean hasIdentity = ApiFactory.getIdentityChainLocal().hasIdentity(address);
                Log.e("LYW", "onActivityResult: " + hasIdentity );
                if (hasIdentity) {
                    handleRecoverySuccess(rst);
                }else {
                    handleRecoveryIdentityErrorMessage("");
                }
            } catch (Exception e) {
                e.printStackTrace();
                handleRecoveryIdentityErrorMessage(e.getMessage());
            }

        }, msg -> {
            handleRecoveryIdentityErrorMessage(msg);
        });
    }

    private void handleRecoverySuccess(IdentityRegistryInterface.RecoveryTriggeredEventResponse rst) {
        Wallet wallet = mWalletKeystore.getWallet();
        wallet.setAssociate(true);
        wallet.setDefaultAddress(true);
        WalletManager.createWallet(mWalletKeystore);
        BigInteger rstEin = rst.ein;
        //升级1484success
        //升级成功存储数据库
        IdHubMessageEntity idHubMessageEntity = new IdHubMessageEntity();
        idHubMessageEntity.setTime(DateUtils.getCurrentDate());
        idHubMessageEntity.setType(IdHubMessageType.RECOVERY_IDENTITY);
        String associatedAddress = rst.newAssociatedAddress;
        idHubMessageEntity.setAddress(associatedAddress);
        idHubMessageEntity.setEin(rstEin.toString());
        idHubMessageEntity.setRecoverAddress(mRecoveryAddress);
        idHubMessageEntity.setDefaultAddress(associatedAddress);
        new IdHubMessageDbManager().insertData(idHubMessageEntity, null);
        //备份成功进行身份升级注册 。身份升级只能是有第一个address的时候，升级成功设置address为defaultAddress
        WalletOtherInfoSharpreference.getInstance().setRecoverAddress(mRecoveryAddress);
        WalletOtherInfoSharpreference.getInstance().setEIN(rstEin.toString());
        Message message = Message.obtain();
        message.what = 1;
        message.obj = mWalletKeystore.getAddress();
        handler.sendMessage(message);
    }

    private void handleRecoveryIdentityErrorMessage(String msg) {
        Message messageError = Message.obtain();
        messageError.what = 2;
        messageError.obj = msg;
        handler.sendMessage(messageError);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String address = ((String) msg.obj);
                    //调用1056的reset
                    ApiFactory.getIdentityChainLocal().reset(address,new WalletInfo(mWalletKeystore).exportPrivateKey(mPassword)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ERC1056ResolverInterface.IdentityResetedEventResponse>() {
                        @Override
                        public void onNext(ERC1056ResolverInterface.IdentityResetedEventResponse identityResetedEventResponse) {
                            String initiator = identityResetedEventResponse.initiator;
                            String indeitity = identityResetedEventResponse.newIndeitity;
                            String oldIdentity = identityResetedEventResponse.oldIdentity;
                            BigInteger ein1 = identityResetedEventResponse.ein;
                            Identity1484To1056BindSharedPreferences.getInstance().setUpgradeInitializeIsSuccess(true);
                            Log.e("LYW", "onNext: " + indeitity +"  "+ initiator+"  " +ein1 +" "+oldIdentity );
                        }

                        @Override
                        public void onError(Throwable e) {
                            mLoadingAndErrorView.setVisibility(View.GONE);
                            MainActivity.startAction(WalletIdentityRecoveryActivity.this, "upgrade");
                            Log.e("LYW", "onError: " +e.getMessage() );
                        }

                        @Override
                        public void onComplete() {
                            MainActivity.startAction(WalletIdentityRecoveryActivity.this, "upgrade");
                            mLoadingAndErrorView.setVisibility(View.GONE);
                        }
                    });
                    break;
                case 2:
                    Log.e("LYW", "inputConfirm: " + msg );
                    ToastUtils.showShortToast(getString(R.string.wallet_recovery_identity_fail));
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    break;
            }
        }
    };
}
