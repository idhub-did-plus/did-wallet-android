package com.idhub.wallet.wallet.manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputPasswordDialogFragment;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.wallet.export.ExportWalletContentActivity;

public class WalletManagerActivity extends AppCompatActivity implements View.OnClickListener, InputPasswordDialogFragment.InputPasswordDialogFragmentListener {

    private WalletManagerItem mExportMnemonic;
    private WalletManagerItem mExportKeystore;
    private WalletManagerItem mExportPrivateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_wallet_manager);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.title);
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
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, WalletManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == mExportMnemonic) {
            showPasswordDialog("mnemonic");
        } else if (v == mExportKeystore) {
            showPasswordDialog("keystore");
        } else if (v == mExportPrivateKey) {
            showPasswordDialog("privatekey");
        }
    }

    private void showPasswordDialog(String data) {
        InputPasswordDialogFragment inputPasswordDialogFragment = new InputPasswordDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        inputPasswordDialogFragment.setArguments(bundle);
        inputPasswordDialogFragment.show(getSupportFragmentManager(), "input_password_dialog_fragment");
    }

    @Override
    public void inputPasswordConfirm(String password, String data) {
        //check password
        Toast.makeText(this, "" + password, Toast.LENGTH_SHORT).show();
        if (data.equals("mnemonic")) {

        } else {
            ExportWalletContentActivity.startAction(WalletManagerActivity.this, data);
        }
    }
}
