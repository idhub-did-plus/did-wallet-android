package com.idhub.wallet.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;

public class AboutWeActivity extends BaseActivity {

    public static void startAction(Context context) {
        context.startActivity(new Intent(context,AboutWeActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_about_we);
        initView();
    }

    private void initView() {
        TextView version = findViewById(R.id.tv_version);
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            version.setText(String.valueOf(pi.versionName));
        } catch (Exception e) {
        }

    }
}
