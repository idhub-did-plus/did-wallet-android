package com.idhub.wallet.me.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.MessageDialogFragment;
import com.idhub.wallet.createmanager.UpgradeActivity;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;

public class UploadInformationTypeActivity extends AppCompatActivity implements View.OnClickListener, MessageDialogFragment.MessageDialogFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upload_information_type);
        initView();
    }

    private void initView() {
         findViewById(R.id.tv_upload_idhub_information).setOnClickListener(this);
         findViewById(R.id.tv_upload_file).setOnClickListener(this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UploadInformationTypeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (WalletManager.getDefaultKeystore() == null) {
            MessageDialogFragment messageDialogFragment = MessageDialogFragment.getInstance(getString(R.string.wallet_upload_upgrade_tip), getString(R.string.wallet_go_upgrade));
            messageDialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
            messageDialogFragment.setMessagePasswordDialogFragmentListener(this);
            return;
        }
        int id = v.getId();
        switch (id) {
            case R.id.tv_upload_idhub_information:
                UploadIDHubInformationActivity.startAction(this);
                break;
            case R.id.tv_upload_file:
                UploadFileActivity.startAction(this);
                break;
        }
    }

    @Override
    public void confirm() {
        WalletKeystore didHubMnemonicKeyStore = WalletManager.getCurrentKeyStore();
        UpgradeActivity.startAction(this, didHubMnemonicKeyStore.getId());
    }
}
