package com.idhub.wallet.wallet.manager;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.MessageDialogFragment;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.createmanager.walletcreate.InputPasswordActivity;
import com.idhub.wallet.createmanager.walletimport.ImportWalletActivity;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.createmanager.UpgradeActivity;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.wallet.adapter.WalletSettingListAdapter;
import com.idhub.wallet.wallet.mainfragment.adapter.SelectWalletAdapter;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class WalletManagerActivity extends BaseActivity implements MessageDialogFragment.MessageDialogFragmentListener {

    private View view;
    private SelectAddWalletWayDialog mSelectAddWalletWayDialog;
    private LinkedList<WalletKeystore> mDidHubMnemonicKeyStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_wallet_manager);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, WalletManagerActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        view = findViewById(R.id.cl_setting_layout);
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_wallet_manager));
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_add_wallet, () -> {
            //add wallet dialog
            mSelectAddWalletWayDialog = new SelectAddWalletWayDialog(WalletManagerActivity.this, itemsOnClick);
            mSelectAddWalletWayDialog.show();
        });
        RecyclerView recyclerView = findViewById(R.id.rv_wallet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Hashtable<String, WalletKeystore> walletKeystores = WalletManager.getWalletKeystores();
        mDidHubMnemonicKeyStores = new LinkedList<>();
        for (Iterator<String> iterator = walletKeystores.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            mDidHubMnemonicKeyStores.add(walletKeystores.get(key));
        }
        WalletManageAdapter walletManageAdapter = new WalletManageAdapter(this);
        walletManageAdapter.addDatas(mDidHubMnemonicKeyStores);
        recyclerView.setAdapter(walletManageAdapter);
    }

    private View.OnClickListener itemsOnClick = v -> {
        int id = v.getId();
        switch (id) {
            case R.id.tv_create:
                //再次创建的时候需要检查未升级的时候需要提醒用户去升级才能继续创建钱包
                boolean b = checkAddressRegisterIDHub();
                if (b) {
                    InputPasswordActivity.startActionForResult(this, 100,true);
                } else {
                    MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upgrade_tip), getString(R.string.wallet_go_upgrade));
                    messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
                    messageDialogFragment.setMessagePasswordDialogFragmentListener(this);
                }
                break;
            case R.id.tv_import:
                if (checkAddressRegisterIDHub()) {
                    ImportWalletActivity.startActionForResult(this,101);
                } else {
                    MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upgrade_tip), getString(R.string.wallet_go_upgrade));
                    messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
                    messageDialogFragment.setMessagePasswordDialogFragmentListener(this);
                }
                break;
        }
        mSelectAddWalletWayDialog.dismiss();
    };

    private boolean checkAddressRegisterIDHub() {
        if (mDidHubMnemonicKeyStores.size() == 1) {
            WalletKeystore didHubMnemonicKeyStore = mDidHubMnemonicKeyStores.get(0);
            return didHubMnemonicKeyStore.getWallet().isAssociate();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            MainActivity.startAction(this,"add");
            finish();
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            MainActivity.startAction(this,"add");
            finish();
        }
    }

    @Override
    public void confirm() {
        //去升级
        if (mDidHubMnemonicKeyStores.size() == 1) {
            WalletKeystore didHubMnemonicKeyStore = mDidHubMnemonicKeyStores.get(0);
            UpgradeActivity.startAction(this, didHubMnemonicKeyStore.getId());
        }
    }


}
