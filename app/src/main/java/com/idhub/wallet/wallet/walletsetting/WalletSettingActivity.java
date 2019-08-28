package com.idhub.wallet.wallet.walletsetting;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.MessageDialogFragment;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.createmanager.walletcreate.InputPasswordActivity;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.createmanager.UpgradeActivity;
import com.idhub.wallet.wallet.adapter.WalletSettingListAdapter;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class WalletSettingActivity extends AppCompatActivity implements MessageDialogFragment.MessageDialogFragmentListener {

    private View view;
    private SelectAddWalletWayPopupWindow mSelectAddWalletWayPopupWindow;
    private LinkedList<DidHubKeyStore> mDidHubKeyStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_wallet_setting);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, WalletSettingActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        view = findViewById(R.id.cl_setting_layout);
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_wallet_manager));
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_add_wallet, () -> {
            //add wallet dialog
            mSelectAddWalletWayPopupWindow = new SelectAddWalletWayPopupWindow(WalletSettingActivity.this, itemsOnClick);
            mSelectAddWalletWayPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        });
        RecyclerView recyclerView = findViewById(R.id.rv_wallet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Hashtable<String, DidHubKeyStore> walletKeystores = WalletManager.getWalletKeystores();
        mDidHubKeyStores = new LinkedList<>();
        for (Iterator<String> iterator = walletKeystores.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            mDidHubKeyStores.add(walletKeystores.get(key));
        }
        WalletSettingListAdapter walletSettingListAdapter = new WalletSettingListAdapter(this);
        walletSettingListAdapter.addDatas(mDidHubKeyStores);
        recyclerView.setAdapter(walletSettingListAdapter);
    }

    private View.OnClickListener itemsOnClick = v -> {
        int id = v.getId();
        switch (id) {
            case R.id.tv_create:
                //再次创建的时候需要检查未升级的时候需要提醒用户去升级才能继续创建钱包
                boolean b = checkAddressRegisterIDHub();
                if (b) {
                    InputPasswordActivity.startActionForResult(this, 100);
                } else {
                    MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upgrade_tip), getString(R.string.wallet_go_upgrade));
                    messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
                    messageDialogFragment.setMessagePasswordDialogFragmentListener(this);
                }
                break;
            case R.id.tv_import:
                break;
        }
        mSelectAddWalletWayPopupWindow.dismiss();
    };

    private boolean checkAddressRegisterIDHub() {
        if (mDidHubKeyStores.size() == 1) {
            DidHubKeyStore didHubKeyStore = mDidHubKeyStores.get(0);
            return didHubKeyStore.getWallet().isAssociate();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            WalletSelectedObservable.getInstance().update();
            MainActivity.startAction(this,"add");
            finish();
        }
    }

    @Override
    public void confirm() {
        //去升级
        if (mDidHubKeyStores.size() == 1) {
            DidHubKeyStore didHubKeyStore = mDidHubKeyStores.get(0);
            UpgradeActivity.startAction(this, didHubKeyStore.getId());
        }
    }


}
