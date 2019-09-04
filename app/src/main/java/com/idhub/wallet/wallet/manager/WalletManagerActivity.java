package com.idhub.wallet.wallet.manager;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.dialog.MessageDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.createmanager.walletcreate.MnemonicBackupHintActivity;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.MnemonicAndPath;
import com.idhub.wallet.createmanager.UpgradeActivity;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.export.ExportWalletContentActivity;

import io.reactivex.observers.DisposableObserver;

public class WalletManagerActivity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener, MessageDialogFragment.MessageDialogFragmentListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_wallet_manager);
        mID = getIntent().getStringExtra("data");
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_manager));
        mExportMnemonic = findViewById(R.id.export_mnemonic);
        mExportMnemonic.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_export_mnemonic));
        mExportMnemonic.setOnClickListener(this);
        mExportKeystore = findViewById(R.id.export_keystore);
        mExportKeystore.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_export_keystore));
        mExportKeystore.setOnClickListener(this);
        mExportPrivateKey = findViewById(R.id.export_private_key);
        mExportPrivateKey.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_export_private_key));
        mExportPrivateKey.setOnClickListener(this);
        mAssociatedAddress = findViewById(R.id.associated_address);
        mAssociatedAddress.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_associated_address));
        mAssociatedAddress.setOnClickListener(this);
        mDelete = findViewById(R.id.delete);
        mDelete.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_delete));
        mDelete.setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, WalletManagerActivity.class);
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
            //判断如果只有一个地址就是去升级，否则就是设置关联地址
            showMessageDialog();
        } else if (v == mDelete) {
            showPasswordDialog(DELETE);
        }
    }

    private void showMessageDialog() {
        int walletNum = WalletManager.getWalletNum();
        if (walletNum <= 1 && !WalletManager.getCurrentKeyStore().getWallet().isAssociate()) {
            MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upgrade_tip), getString(R.string.wallet_go_upgrade));
            messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
            messageDialogFragment.setMessagePasswordDialogFragmentListener(this);
        } else {
            MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upgrade_associated_address), getString(R.string.wallet_confirm));
            messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
            messageDialogFragment.setMessagePasswordDialogFragmentListener(this);
        }
    }

    private void showPasswordDialog(String data) {
        InputDialogFragment instance = InputDialogFragment.getInstance(data, getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener(this);
    }

    @Override
    public void inputConfirm(String data, String source) {
        //check password
        DidHubKeyStore keyStore = WalletManager.getKeyStore(mID);
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
                        MnemonicBackupHintActivity.startActionforResult(WalletManagerActivity.this, mnemonic, 100);
                    } else if (source.equals(KEYSTORE)) {
                        String s = walletInfo.exportKeystore(data);
                        ExportWalletContentActivity.startAction(WalletManagerActivity.this, source, s);
                    } else if (source.equals(PRIVATEKEY)) {
                        String s = walletInfo.exportPrivateKey(data);
                        ExportWalletContentActivity.startAction(WalletManagerActivity.this, source, s);
                    } else if (source.equals(DELETE)) {
                        WalletManager.delete(WalletManager.getKeyStore(mID), data);
                        MainActivity.startAction(WalletManagerActivity.this,DELETE);
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
    public void confirm() {
        //升级
        int walletNum = WalletManager.getWalletNum();
        if (walletNum <= 1 && !WalletManager.getCurrentKeyStore().getWallet().isAssociate()) {
            //升级
            UpgradeActivity.startAction(this, mID);
        } else {
            //关联


            DidHubKeyStore keyStore = WalletManager.getKeyStore(mID);
            keyStore.getWallet().setAssociate(true);
            WalletManager.flushWallet(keyStore, true);
            MainActivity.startAction(this,"associated");
            WalletSelectedObservable.getInstance().update();
        }
    }
}
