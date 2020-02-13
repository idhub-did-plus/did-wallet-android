package com.idhub.wallet.createmanager;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.main.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.createmanager.walletcreate.InputPasswordActivity;
import com.idhub.wallet.createmanager.walletimport.ImportWalletActivity;
import com.idhub.wallet.createmanager.walletrecover.WalletIdentityRecoveryActivity;

public class IdentityManagerActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_identity_manager);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, IdentityManagerActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        findViewById(R.id.tv_identity_create).setOnClickListener(this);
        findViewById(R.id.tv_identity_recover).setOnClickListener(this);
        findViewById(R.id.tv_identity_import).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_identity_create:
               InputPasswordActivity.startActionForResult(this,100,true);
                break;
            case R.id.tv_identity_import:
                ImportWalletActivity.startActionForResult(this,101);
                break;
            case R.id.tv_identity_recover:
                WalletIdentityRecoveryActivity.startActionForResult(this,102);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            MainActivity.startAction(this,"IdentityManager");
            finish();
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            MainActivity.startAction(this,"IdentityManager");
            finish();
        } else if (requestCode == 102 && resultCode == RESULT_OK) {
            MainActivity.startAction(this, "upgrade");
            finish();
        }
    }
}
