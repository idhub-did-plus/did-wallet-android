package com.idhub.wallet.hository.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idhub.wallet.MainFragmentPagerAdapter;
import com.idhub.wallet.R;
import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.hository.message.moretransaction.MoreTransactionMessageActivity;

public class HistoryMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_message);
        initView();
    }

    public static void startAction(Context context) {
        context.startActivity(new Intent(context,HistoryMessageActivity.class));
    }
    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_message_center));
        titleLayout.setFirstTextAndClickCallBack("查看更多", new TitleLayout.OnImageClickCallbackListener() {
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
