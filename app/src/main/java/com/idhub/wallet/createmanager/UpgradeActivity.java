package com.idhub.wallet.createmanager;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.idhub.magic.clientlib.interfaces.Identity;
import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.UpgradeInitializeSharedpreferences;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.createmanager.walletcreate.MnemonicBackupHintActivity;
import com.idhub.wallet.didhub.RecoverAddress;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.MnemonicUtil;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.IdHubMessageDbManager;
import com.idhub.wallet.greendao.IdHubMessageType;
import com.idhub.base.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.DateUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import com.idhub.magic.clientlib.ApiFactory;
import com.idhub.magic.clientlib.interfaces.Listen;

public class UpgradeActivity extends BaseActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

    private String mRecoverAddressStr;
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mPwd;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String associatedAddress = (String) msg.obj;
                    //调用1056的initialize
                    Log.e("LYW", "handleMessage:begininitialize ");
                    ApiFactory.getIdentityChainLocal().initialize(associatedAddress).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ERC1056ResolverInterface.IdentityInitializedEventResponse>() {
                        @Override
                        public void onNext(ERC1056ResolverInterface.IdentityInitializedEventResponse identityInitializedEventResponse) {
                            String initiator = identityInitializedEventResponse.initiator;
                            String indeitity = identityInitializedEventResponse.indeitity;
                            BigInteger ein1 = identityInitializedEventResponse.ein;
                            UpgradeInitializeSharedpreferences.getInstance().setUpgradeInitializeIsSuccess(true);
                            Log.e("LYW", "onNext:initialize " + indeitity + "  " + initiator + "  " + ein1);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mLoadingAndErrorView.setVisibility(View.GONE);
                            MainActivity.startAction(UpgradeActivity.this, "upgrade");
                            Log.e("LYW", "onError:initialize " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            MainActivity.startAction(UpgradeActivity.this, "upgrade");
                            mLoadingAndErrorView.setVisibility(View.GONE);
                        }
                    });
                    break;
                case 2:
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    String message = (String) msg.obj;
                    ToastUtils.showLongToast(getString(R.string.wallet_upgrade_error) + message);
                    break;
            }
        }
    };

    private TextView mUpgradeView;
    private String mMnemonicStrs;
    private WalletKeystore mWalletKeystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upgrade);
        initView();
        initData();
    }

    private void initData() {
        String data = getIntent().getStringExtra("data");
        mWalletKeystore = WalletManager.getKeyStore(data);
    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, UpgradeActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_upgrade));
        mUpgradeView = findViewById(R.id.tv_upgrade);
        mUpgradeView.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
        NestedScrollView scrollView = findViewById(R.id.sv_upgrade);
        if (scrollView.getMeasuredHeight() == 0) {
            mUpgradeView.setBackgroundResource(R.drawable.wallet_shape_button);
            mUpgradeView.setOnClickListener(UpgradeActivity.this);
        }
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            View view = v.getChildAt(0);
            int height = view.getMeasuredHeight();
            int measuredHeight = v.getMeasuredHeight();
            if (scrollY + measuredHeight == height) {
                mUpgradeView.setBackgroundResource(R.drawable.wallet_shape_button);
                mUpgradeView.setOnClickListener(UpgradeActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_upgrade:
                //TODO:暂时这么先写 判断当前节点没有合约地址
                String identityRegistryInterface = DeployedContractAddress.IdentityRegistryInterface;
                if (TextUtils.isEmpty(identityRegistryInterface)) {
                    Toast.makeText(this, getString(R.string.wallet_none_contract_address), Toast.LENGTH_SHORT).show();
                    return;
                }
                //身份升级需要先验证输入用户密码
                if (TextUtils.isEmpty(mMnemonicStrs)) {
                    inputVerifyPassword();
                } else {
                    if (UpgradeInitializeSharedpreferences.getInstance().getIsUpgradeAction()) {
                        checkHasIdentity();
                    }else {
                        MnemonicBackupHintActivity.startActionforResult(this, mMnemonicStrs, 100);
                    }
                }
                break;
        }
    }

    private void inputVerifyPassword() {
        InputDialogFragment instance = InputDialogFragment.getInstance("upgrade", getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener(this);
    }

    private void createRecoverAddressAndMnemonic() {
        if (TextUtils.isEmpty(mMnemonicStrs)) {
            List<String> mnemonicCodes = MnemonicUtil.randomMnemonicCodes();
            RecoverAddress recoverAddress = new RecoverAddress(mnemonicCodes);
            mRecoverAddressStr = NumericUtil.prependHexPrefix(recoverAddress.getAddress());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mnemonicCodes.size(); i++) {
                stringBuilder.append(mnemonicCodes.get(i)).append(" ");
            }
            mMnemonicStrs = stringBuilder.toString();
        }
        MnemonicBackupHintActivity.startActionforResult(this, mMnemonicStrs, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            mLoadingAndErrorView.setVisibility(View.VISIBLE);
            //设置记录用户升级的操作
            UpgradeInitializeSharedpreferences.getInstance().setUpgradeAction(true);
            //upgrade
            WalletInfo walletInfo = new WalletInfo(mWalletKeystore);
            String privateKey = walletInfo.exportPrivateKey(mPwd);
            IDHubCredentialProvider.setDefaultCredentials(privateKey);
            IDHubCredentialProvider.setRecoverAddress(mRecoverAddressStr);
            Listen<IdentityRegistryInterface.IdentityCreatedEventResponse> identity = ApiFactory.getIdentityChainLocal().createIdentity();
            identity.listen(identityCreatedEventResponse -> {
                BigInteger ein = identityCreatedEventResponse.ein;
                Log.e("LYW", "onNext:upgrade ein " + ein);
                //升级1484success
                //升级成功存储数据库
                IdHubMessageEntity idHubMessageEntity = new IdHubMessageEntity();
                idHubMessageEntity.setTime(DateUtils.getCurrentDate());
                idHubMessageEntity.setType(IdHubMessageType.UPGRADE_1484_IDENTITY);
                String associatedAddress = mWalletKeystore.getAddress();
                idHubMessageEntity.setAddress(associatedAddress);
                idHubMessageEntity.setEin(ein.toString());
                idHubMessageEntity.setRecoverAddress(mRecoverAddressStr);
                idHubMessageEntity.setDefaultAddress(associatedAddress);
                new IdHubMessageDbManager().insertData(idHubMessageEntity, null);
                //备份成功进行身份升级注册 。身份升级只能是有第一个address的时候，升级成功设置address为defaultAddress
                WalletOtherInfoSharpreference.getInstance().setRecoverAddress(mRecoverAddressStr);
                WalletOtherInfoSharpreference.getInstance().setEIN(ein.toString());
                WalletKeystore keyStore = mWalletKeystore;
                Wallet wallet = keyStore.getWallet();
                wallet.setAssociate(true);
                wallet.setDefaultAddress(true);
                WalletManager.flushWallet(keyStore, true);

                Message message = Message.obtain();
                message.what = 1;
                message.obj = associatedAddress;
                handler.sendMessage(message);

            }, message -> {
                Log.e("LYW", "onActivityResult: " + message);
                Message messageError = Message.obtain();
                messageError.what = 2;
                messageError.obj = message;
                handler.sendMessage(messageError);
            });
        }
    }

    @Override
    public void inputConfirm(String data, String source) {
        WalletInfo walletInfo = new WalletInfo(mWalletKeystore);
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
                    boolean isUpgradeAction = UpgradeInitializeSharedpreferences.getInstance().getIsUpgradeAction();
                    if (isUpgradeAction) {
                        checkHasIdentity();
                    } else {
                        //生成恢复地址recoverAddress和助记词。助记词备份完之后提交create 成功之后保存recoverAddress
                        createRecoverAddressAndMnemonic();
                    }
                    mLoadingAndErrorView.setVisibility(View.GONE);
                } else {
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                }
            }

            @Override
            public void onError(Throwable e) {
                mLoadingAndErrorView.setVisibility(View.GONE);
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        });
    }

    //考虑到异常退出类的情况，已经升级身份本地没有记录
    private void checkHasIdentity() {
        Log.e("LYW", "checkHasIdentity: check" );
        String address = mWalletKeystore.getAddress();
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                IDHubCredentialProvider.setDefaultCredentials(new WalletInfo(mWalletKeystore).exportPrivateKey(mPwd));
                Boolean hasIdentity = ApiFactory.getIdentityChainLocal().hasIdentity(address);
                if (hasIdentity) {
                    String ein = ApiFactory.getIdentityChainLocal().getEINSync(address).toString();
                    Identity identity = ApiFactory.getIdentityChainLocal().getIdentitySync(Long.parseLong(ein));
                    ArrayList<String> strs = new ArrayList<>();
                    strs.add(ein);
                    strs.add(identity.getRecoveryAddress());
                    emitter.onNext(strs);
                } else {
                    emitter.onError(null);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<List<String>>() {

            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(List<String> list) {
                mLoadingAndErrorView.setVisibility(View.GONE);
                String ein = list.get(0);
                String recoverAddress = list.get(1);
                Log.e("LYW", "onNext:upgrade ein " + ein);
                //升级1484success
                //升级成功存储数据库
                IdHubMessageEntity idHubMessageEntity = new IdHubMessageEntity();
                idHubMessageEntity.setTime(DateUtils.getCurrentDate());
                idHubMessageEntity.setType(IdHubMessageType.UPGRADE_1484_IDENTITY);
                idHubMessageEntity.setAddress(address);
                idHubMessageEntity.setEin(ein.toString());
                idHubMessageEntity.setRecoverAddress(recoverAddress);
                idHubMessageEntity.setDefaultAddress(address);
                new IdHubMessageDbManager().insertData(idHubMessageEntity, null);
                //备份成功进行身份升级注册 。身份升级只能是有第一个address的时候，升级成功设置address为defaultAddress
                WalletOtherInfoSharpreference.getInstance().setRecoverAddress(recoverAddress);
                WalletOtherInfoSharpreference.getInstance().setEIN(ein.toString());
                WalletKeystore keyStore = mWalletKeystore;
                Wallet wallet = keyStore.getWallet();
                wallet.setAssociate(true);
                wallet.setDefaultAddress(true);
                WalletManager.flushWallet(keyStore, true);
                MainActivity.startAction(UpgradeActivity.this, "upgrade");
            }

            @Override
            public void onError(Throwable e) {
                mLoadingAndErrorView.setVisibility(View.GONE);
                createRecoverAddressAndMnemonic();
            }

            @Override
            public void onComplete() {

            }
        });


    }
}
