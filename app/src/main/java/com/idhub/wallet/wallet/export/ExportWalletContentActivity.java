package com.idhub.wallet.wallet.export;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;

public class ExportWalletContentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_export_wallet_content);
        init();
    }

    public static void startAction(Context context, String title,String data) {
        Intent intent = new Intent(context, ExportWalletContentActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    private void init() {
        TitleLayout titleLayout = findViewById(R.id.title);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String data = intent.getStringExtra("data");
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        String[] items = new String[2];
        if (title.equals("keystore")) {
            titleLayout.setTitle(getString(R.string.wallet_export_keystore));
            items[0] = getString(R.string.wallet_keystore);
        } else if (title.equals("privatekey")) {
            titleLayout.setTitle(getString(R.string.wallet_export_private_key));
            items[0] = getString(R.string.wallet_private_key);
        }
        items[1] = getString(R.string.wallet_QR_code);
        ExportWalletFragmentPagerAdapter adapter = new ExportWalletFragmentPagerAdapter(getSupportFragmentManager(), this, items,data);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
