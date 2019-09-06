package com.idhub.wallet.hository.message.moretransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.idhub.wallet.R;
import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.common.title.TitleLayout;

import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.hository.message.MessageFragmentPagerAdapter;

public class MoreTransactionMessageActivity extends AppCompatActivity {

    private String mSearchAddress = WalletManager.getAddress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_transaction_message);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MoreTransactionMessageActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_message_transaction));
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        MoreMessageFragmentPagerAdapter adapter = new MoreMessageFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public String getWalletAddress(){
        return mSearchAddress;
    }
}
