package com.idhub.wallet.createmanager;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.createmanager.walletcreate.InputPasswordActivity;

public class IdentityManagerActivity extends AppCompatActivity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_identity_create:
               InputPasswordActivity.startActionForResult(this,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            MainActivity.startAction(this,"IdentityManager");
            finish();
        }
    }
}
