package com.idhub.wallet.wallet.export;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.MainFragmentPagerAdapter;
import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;

public class ExportWalletContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_export_wallet_content);
        initView();
    }

    public static void startAction(Context context, String data) {
        Intent intent = new Intent(context, ExportWalletContentActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        String[] items = new String[2];
        if (data.equals("keystore")) {
            titleLayout.setTitle(getString(R.string.wallet_export_keystore));
            items[0] = getString(R.string.wallet_keystore);
        } else if (data.equals("privatekey")) {
            titleLayout.setTitle(getString(R.string.wallet_export_private_key));
            items[0] = getString(R.string.wallet_private_key);
        }
        items[1] = getString(R.string.wallet_QR_code);
        ExportWalletFragmentPagerAdapter adapter = new ExportWalletFragmentPagerAdapter(getSupportFragmentManager(), this, items);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
