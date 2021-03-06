package com.idhub.wallet.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingFragment())
                .commit();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

}