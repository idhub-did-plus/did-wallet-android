package com.idhub.wallet.identitymanager.identitycreate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;

public class InputPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_input_password);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_create_wallet));
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, InputPasswordActivity.class);
        context.startActivity(intent);
    }
}
