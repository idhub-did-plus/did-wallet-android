package com.idhub.wallet.createmanager;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.idhub.magic.center.contracts.ERC1056ResolverInterface;
import com.idhub.magic.center.contracts.IdentityRegistryInterface;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.createmanager.walletcreate.MnemonicBackupHintActivity;
import com.idhub.wallet.didhub.RecoverAddress;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.MnemonicUtil;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.IdHubMessageDbManager;
import com.idhub.wallet.greendao.IdHubMessageType;
import com.idhub.wallet.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.DateUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.math.BigInteger;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.interfaces.Listen;

public class UpgradeActivity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

    private String mData;
    private String mRecoverAddressStr;
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mPwd;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    IdentityRegistryInterface.IdentityCreatedEventResponse identityCreatedEventResponse = (IdentityRegistryInterface.IdentityCreatedEventResponse) msg.obj;
                    BigInteger ein = identityCreatedEventResponse.ein;
                    //升级1484success
                    //升级成功存储数据库
                    IdHubMessageEntity idHubMessageEntity = new IdHubMessageEntity();
                    idHubMessageEntity.setTime(DateUtils.getCurrentDate());
                    idHubMessageEntity.setType(IdHubMessageType.UPGRADE_1484_IDENTITY);
                    String associatedAddress = identityCreatedEventResponse.associatedAddress;
                    idHubMessageEntity.setAddress(associatedAddress);
                    idHubMessageEntity.setEin(ein.toString());
                    idHubMessageEntity.setRecoverAddress(identityCreatedEventResponse.recoveryAddress);
                    idHubMessageEntity.setDefaultAddress(associatedAddress);
                    new IdHubMessageDbManager().insertData(idHubMessageEntity, null);
                    //备份成功进行身份升级注册 。身份升级只能是有第一个address的时候，升级成功设置address为defaultAddress
                    WalletOtherInfoSharpreference.getInstance().setRecoverAddress(identityCreatedEventResponse.recoveryAddress);
                    WalletOtherInfoSharpreference.getInstance().setEIN(ein.toString());
                    WalletKeystore keyStore = WalletManager.getKeyStore(mData);
                    Wallet wallet = keyStore.getWallet();
                    wallet.setAssociate(true);
                    wallet.setDefaultAddress(true);
                    WalletManager.flushWallet(keyStore, true);
                    //调用1056的initialize
                    ApiFactory.getIdentityChainLocal().initialize(associatedAddress).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ERC1056ResolverInterface.IdentityInitializedEventResponse>() {
                        @Override
                        public void onNext(ERC1056ResolverInterface.IdentityInitializedEventResponse identityInitializedEventResponse) {
                            String initiator = identityInitializedEventResponse.initiator;
                            String indeitity = identityInitializedEventResponse.indeitity;
                            BigInteger ein1 = identityInitializedEventResponse.ein;
                            Log.e("LYW", "onNext: " + indeitity +"  "+ initiator+"  " +ein1 );
                        }

                        @Override
                        public void onError(Throwable e) {
                            mLoadingAndErrorView.setVisibility(View.GONE);
                            MainActivity.startAction(UpgradeActivity.this, "upgrade");
                            Log.e("LYW", "onError: " +e.getMessage() );
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
                //身份升级需要先验证输入用户密码
                inputVerifyPassword();
                break;
        }
    }

    private void inputVerifyPassword() {
        InputDialogFragment instance = InputDialogFragment.getInstance("upgrade", getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener(this);
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
            mLoadingAndErrorView.setVisibility(View.VISIBLE);
            WalletInfo walletInfo = new WalletInfo(WalletManager.getKeyStore(mData));
            String privateKey = walletInfo.exportPrivateKey(mPwd);
            IDHubCredentialProvider.setDefaultCredentials(privateKey);
            IDHubCredentialProvider.setRecoverAddress(mRecoverAddressStr);
            Listen<IdentityRegistryInterface.IdentityCreatedEventResponse> identity = ApiFactory.getIdentityChainLocal().createIdentity();
            //error
            identity.listen(identityCreatedEventResponse -> {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = identityCreatedEventResponse;
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
                mLoadingAndErrorView.setVisibility(View.GONE);
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        });
    }
}
