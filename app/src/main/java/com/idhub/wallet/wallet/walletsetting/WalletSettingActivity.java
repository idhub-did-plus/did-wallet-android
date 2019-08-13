package com.idhub.wallet.wallet.walletsetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.wallet.adapter.WalletSettingListAdapter;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class WalletSettingActivity extends AppCompatActivity {

    private View view;

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
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_add_wallet, new TitleLayout.OnImageClickCallbackListener() {
            @Override
            public void onImageClick() {
                //add wallet dialog
                SelectAddWalletWayPopupWindow selectAddWalletWayPopupWindow = new SelectAddWalletWayPopupWindow(WalletSettingActivity.this);
                selectAddWalletWayPopupWindow.showAtLocation(view,Gravity.BOTTOM,0,0);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.rv_wallet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Hashtable<String, DidHubKeyStore> walletKeystores = WalletManager.getWalletKeystores();
        LinkedList<DidHubKeyStore> didHubKeyStores = new LinkedList<>();
        for(Iterator<String> iterator = walletKeystores.keySet().iterator(); iterator.hasNext();){
            String key=iterator.next();
            didHubKeyStores.add(walletKeystores.get(key));
        }
        WalletSettingListAdapter walletSettingListAdapter = new WalletSettingListAdapter(this);
        walletSettingListAdapter.addDatas(didHubKeyStores);
        recyclerView.setAdapter(walletSettingListAdapter);
    }
}
