package com.idhub.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.createmanager.IdentityManagerActivity;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.splash.SplashActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_main);
        init();
        initView();
        // Example of a call to a native method
    }

    private void init() {
        //检查钱包数
        int walletNum = WalletManager.getWalletNum();
        if (walletNum <= 0) {
            IdentityManagerActivity.startAction(this);
            finish();
        }
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        int tabCount = tabLayout.getTabCount();
        if (tabCount == 4) {
            tabLayout.getTabAt(0).setIcon(R.drawable.wallet_me_icon);
            tabLayout.getTabAt(1).setIcon(R.drawable.wallet_wallet_icon);
            tabLayout.getTabAt(2).setIcon(R.drawable.wallet_dapp_icon);
            tabLayout.getTabAt(3).setIcon(R.drawable.wallet_history_icon);
        }

    }

}
