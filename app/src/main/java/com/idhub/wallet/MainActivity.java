package com.idhub.wallet;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.common.tablayout.TabLayout;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_main);
        initView();
        // Example of a call to a native method
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

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
