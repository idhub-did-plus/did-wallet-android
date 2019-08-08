package com.idhub.wallet.wallet.manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;

public class WalletManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_wallet_manager);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_manager));
        WalletManagerItem mExportMnemonic = findViewById(R.id.export_mnemonic);
        mExportMnemonic.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_export_mnemonic));
        WalletManagerItem mExportKeystore = findViewById(R.id.export_keystore);
        mExportKeystore.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_export_keystore));
        WalletManagerItem mExportPrivateKey = findViewById(R.id.export_private_key);
        mExportPrivateKey.setData(R.mipmap.wallet_eth_icon, getString(R.string.wallet_export_private_key));
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, WalletManagerActivity.class);
        context.startActivity(intent);
    }
}
