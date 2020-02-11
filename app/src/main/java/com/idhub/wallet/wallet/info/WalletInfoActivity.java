package com.idhub.wallet.wallet.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idhub.base.greendao.entity.IdentityEntity;
import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.dialog.MessageDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.createmanager.walletcreate.MnemonicBackupHintActivity;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.MnemonicAndPath;
import com.idhub.wallet.createmanager.UpgradeActivity;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.IdentityDbManager;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.StringUtils;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.export.ExportWalletContentActivity;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import com.idhub.magic.clientlib.ApiFactory;

import org.web3j.crypto.Keys;

public class WalletInfoActivity extends BaseActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener, AddAssociationAddressDialogFragment.AddAssociationAddressDialogFragmentListener {

    private WalletManagerItemView mExportPasswordHint;
    private WalletManagerItemView mExportMnemonic;
    private WalletManagerItemView mExportKeystore;
    private WalletManagerItemView mExportPrivateKey;
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mID;
    private WalletManagerItemView mAssociatedAddress;
    private final String MNEMONIC = "mnemonic";
    private final String KEYSTORE = "keystore";
    private final String PRIVATEKEY = "privatekey";
    private final String DELETE = "delete";
    private WalletManagerItemView mDelete;
//    private ImageView headView;
    private TextView mWalletNameView;
    private TextView mWalletAddressView;
    private WalletKeystore mDefaultKeystore;
    private WalletKeystore mAssociationKeyStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_wallet_info);
        mID = getIntent().getStringExtra("data");
        initView();
        initData();
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_manager));
//        headView = findViewById(R.id.iv_head);
        mWalletNameView = findViewById(R.id.tv_wallet_name);
        mWalletAddressView = findViewById(R.id.tv_wallet_address);
        mWalletAddressView.setOnClickListener(this);
        mExportPasswordHint = findViewById(R.id.export_password_hint);
        mExportPasswordHint.setVisibility(View.GONE);
        mExportPasswordHint.setData(R.mipmap.wallet_export_mnemonic, getString(R.string.wallet_export_password_hint));
        mExportPasswordHint.setOnClickListener(this);
        mExportMnemonic = findViewById(R.id.export_mnemonic);
        mExportMnemonic.setData(R.mipmap.wallet_export_mnemonic, getString(R.string.wallet_export_mnemonic));
        mExportMnemonic.setOnClickListener(this);
        mExportKeystore = findViewById(R.id.export_keystore);
        mExportKeystore.setData(R.mipmap.wallet_export_keystore, getString(R.string.wallet_export_keystore));
        mExportKeystore.setOnClickListener(this);
        mExportPrivateKey = findViewById(R.id.export_private_key);
        mExportPrivateKey.setData(R.mipmap.wallet_export_private_key, getString(R.string.wallet_export_private_key));
        mExportPrivateKey.setOnClickListener(this);
        mAssociatedAddress = findViewById(R.id.associated_address);
        mAssociatedAddress.setData(R.mipmap.wallet_association_address, getString(R.string.wallet_associated_address));
        mAssociatedAddress.setOnClickListener(this);
        mDelete = findViewById(R.id.delete);
        mDelete.setData(R.mipmap.wallet_delete_wallet, getString(R.string.wallet_delete));
        mDelete.setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }

    private void initData() {
        WalletKeystore keyStore = WalletManager.getKeyStore(mID);
        String address = keyStore.getAddress();
        mDefaultKeystore = WalletManager.getDefaultKeystore();
        mWalletNameView.setText(keyStore.getWallet().getName());
        mWalletAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(keyStore.getAddress())));
        if (keyStore instanceof DidHubKeyStore) {
            mExportMnemonic.setVisibility(View.GONE);
        } else if (keyStore instanceof DidHubMnemonicKeyStore) {
            mExportMnemonic.setVisibility(View.VISIBLE);
        }
//        if (mCurrentIdentity!= null && mCurrentIdentity.getIsAssociate()) {
//            mAssociatedAddress.setVisibility(View.GONE);
//            mDelete.setVisibility(View.GONE);
//        }
        mDelete.setVisibility(View.GONE);
    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, WalletInfoActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == mExportMnemonic) {
            showPasswordDialog(MNEMONIC);
        } else if (v == mExportKeystore) {
            showPasswordDialog(KEYSTORE);
        } else if (v == mExportPrivateKey) {
            showPasswordDialog(PRIVATEKEY);
        } else if (v == mAssociatedAddress) {
            //判断当前节点下是否有默认地址
            showMessageDialog();
        } else if (v == mDelete) {
            showPasswordDialog(DELETE);
        } else if (v == mExportPasswordHint) {

        } else if (v == mWalletAddressView) {
            String s = mWalletAddressView.getText().toString();
            StringUtils.copy(this, s);
            ToastUtils.showShortToast(getString(R.string.wallet_copy_address_success));
        }
    }

    private void showMessageDialog() {
        //判断当前节点下是否有默认地址，没有默认地址进行升级
        if (mDefaultKeystore == null) {
            MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upgrade_tip), getString(R.string.wallet_go_upgrade));
            messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
            messageDialogFragment.setMessagePasswordDialogFragmentListener(new MessageDialogFragment.MessageDialogFragmentListener() {
                @Override
                public void confirm() {
                    UpgradeActivity.startAction(WalletInfoActivity.this, mID);
                }
            });
        } else {
            //TODO:暂时这么先写 判断当前节点没有合约地址
            String identityRegistryInterface = DeployedContractAddress.IdentityRegistryInterface;
            if (TextUtils.isEmpty(identityRegistryInterface)) {
                Toast.makeText(this, getString(R.string.wallet_none_contract_address), Toast.LENGTH_SHORT).show();
                return;
            }
            MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upgrade_associated_address), getString(R.string.wallet_confirm));
            messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
            messageDialogFragment.setMessagePasswordDialogFragmentListener(new MessageDialogFragment.MessageDialogFragmentListener() {
                @Override
                public void confirm() {
                    //关联
                    //输入密码
                    mAssociationKeyStore = WalletManager.getKeyStore(mID);
                    AddAssociationAddressDialogFragment dialogFragment = AddAssociationAddressDialogFragment.getInstance(mDefaultKeystore.getAddress(), mAssociationKeyStore.getAddress());
                    dialogFragment.show(getSupportFragmentManager(), "add_association_address_dialog_fragment");
                    dialogFragment.setAddAssociationAddressDialogFragmentListener(WalletInfoActivity.this);
                }
            });
        }
    }

    private void showPasswordDialog(String data) {
        InputDialogFragment instance = InputDialogFragment.getInstance(data, getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener(this);
    }

    @Override
    public void inputConfirm(String data, String source) {
        //check password
        WalletKeystore keyStore = WalletManager.getKeyStore(mID);
        if (keyStore == null) {
            ToastUtils.showShortToast(Messages.WALLET_INVALID_KEYSTORE);
            return;
        }
        WalletInfo walletInfo = new WalletInfo(keyStore);
        DisposableObserver<Boolean> observer = new DisposableObserver<Boolean>() {
            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                mLoadingAndErrorView.setVisibility(View.GONE);
                if (aBoolean) {
                    if (source.equals(MNEMONIC)) {
                        MnemonicAndPath mnemonicAndPath = walletInfo.exportMnemonic(data);
                        String mnemonic = mnemonicAndPath.getMnemonic();
                        MnemonicBackupHintActivity.startActionforResult(WalletInfoActivity.this, mnemonic, 100);
                    } else if (source.equals(KEYSTORE)) {
                        String s = walletInfo.exportKeystore(data);
                        ExportWalletContentActivity.startAction(WalletInfoActivity.this, source, s);
                    } else if (source.equals(PRIVATEKEY)) {
                        String s = walletInfo.exportPrivateKey(data);
                        ExportWalletContentActivity.startAction(WalletInfoActivity.this, source, s);
                    } else if (source.equals(DELETE)) {
                        WalletManager.delete(WalletManager.getKeyStore(mID), data);
                        MainActivity.startAction(WalletInfoActivity.this, DELETE);
                    }
                } else {
                    ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
        walletInfo.verifyPassword(data, observer);
    }

    @Override
    public void confirm(String defaultPsd, String associationPsd) {
        WalletInfo defaultAddressWalletInfo = new WalletInfo(mDefaultKeystore);
        WalletInfo associationAddressWalletInfo = new WalletInfo(mAssociationKeyStore);
        mLoadingAndErrorView.setVisibility(View.VISIBLE);

        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            boolean defaultPsdResult = defaultAddressWalletInfo.verifyPassword(defaultPsd);
            if (!defaultPsdResult) {
                emitter.onError(new Throwable(getString(R.string.wallet_default_password_false)));
                return;
            }
            boolean associationPsdResult = associationAddressWalletInfo.verifyPassword(associationPsd);
            if (!associationPsdResult) {
                emitter.onError(new Throwable(getString(R.string.wallet_association_password_false)));
                return;
            }
            emitter.onNext(true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                String ein = new IdentityDbManager().getEIN(defaultAddressWalletInfo.getAddress());
                //关联地址签名消息。默认地址发交易
                IDHubCredentialProvider.setDefaultCredentials(defaultAddressWalletInfo.exportPrivateKey(defaultPsd));
                ApiFactory.getIdentityChainLocal().addAssociatedAddress(new BigInteger(ein), mDefaultKeystore.getAddress(), mAssociationKeyStore.getAddress()
                        , associationAddressWalletInfo.exportPrivateKey(associationPsd)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableObserver<IdentityRegistryInterface.AssociatedAddressAddedEventResponse>() {
                            @Override
                            public void onNext(IdentityRegistryInterface.AssociatedAddressAddedEventResponse associatedAddressAddedEventResponse) {
                                saveAssociate(mAssociationKeyStore.getAddress());
                                MainActivity.startAction(WalletInfoActivity.this, "associated");
                                WalletSelectedObservable.getInstance().update();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Message message = Message.obtain();
                                message.what = 2;
                                message.obj = e.getMessage();
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
//                ApiFactory.getIdentityChainLocal().addAssociatedAddress(new BigInteger(ein), mDefaultKeystore.getAddress(), mAssociationKeyStore.getAddress(),associationAddressWalletInfo.exportPrivateKey(associationPsd))
//                        .listen(rst -> {
//                            mAssociationKeyStore.getWallet().setCurrentAssociate(true);
//                            WalletManager.flushWallet(mAssociationKeyStore, true);
//                            Message message = Message.obtain();
//                            message.what = 1;
//                            message.obj = rst;
//                            handler.sendMessage(message);
//                        }, msg -> {
//                            Message message = Message.obtain();
//                            message.what = 2;
//                            message.obj = msg;
//                            handler.sendMessage(message);
//                        });
            }

            @Override
            public void onError(Throwable e) {
                mLoadingAndErrorView.setVisibility(View.GONE);
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void saveAssociate(String address) {
        IdentityEntity identityEntity = new IdentityEntity();
        identityEntity.setIdentityAddress(Keys.toChecksumAddress(address));
        identityEntity.setNode(WalletNoteSharedPreferences.getInstance().getNode());
        identityEntity.setIsAssociate(true);
        new IdentityDbManager().insertData(identityEntity,null);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoadingAndErrorView.setVisibility(View.GONE);
            switch (msg.what) {
                case 1:

                    break;
                case 2:
                    String message = ((String) msg.obj);
                    ToastUtils.showShortToast(getString(R.string.wallet_upgrade_association_false) + message);
                    break;
            }
        }
    };
}
