package com.idhub.wallet.identitymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.identitymanager.identitycreate.InputPasswordActivity;

public class IdentityManagerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_identity_manager);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_identity_create).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_identity_create:
                InputPasswordActivity.startAction(this);
                break;
        }
    }
}
