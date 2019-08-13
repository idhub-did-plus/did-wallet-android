package com.idhub.wallet.identitymanager.identitycreate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.model.MnemonicAndPath;

public class MnemonicBackupHintActivity extends AppCompatActivity implements View.OnClickListener {

    private String mMnemonic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_mnemonic_backup_hint);
        findViewById(R.id.tv_next).setOnClickListener(this);
        mMnemonic = getIntent().getStringExtra("data");
    }

    public static void startAction(Context context, String mnemonic) {
        Intent intent = new Intent(context, MnemonicBackupHintActivity.class);
        intent.putExtra("data", mnemonic);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_next:
                MnemonicBackupPreviewActivity.startAction(MnemonicBackupHintActivity.this,mMnemonic);
                break;
        }
    }
}
