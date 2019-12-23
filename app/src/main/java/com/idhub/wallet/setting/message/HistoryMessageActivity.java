package com.idhub.wallet.setting.message;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.setting.message.moretransaction.MoreTransactionMessageActivity;

public class HistoryMessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_history_message);
        initView();
    }

    public static void startAction(Context context) {
        context.startActivity(new Intent(context,HistoryMessageActivity.class));
    }
    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_message_center));
        titleLayout.setFirstTextAndClickCallBack(getString(R.string.wallet_more_detail), new TitleLayout.OnImageClickCallbackListener() {
            @Override
            public void onImageClick() {
                MoreTransactionMessageActivity.startAction(HistoryMessageActivity.this);
            }
        });
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        MessageFragmentPagerAdapter adapter = new MessageFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
