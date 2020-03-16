package com.idhub.wallet.setting.message.moretransaction;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.common.title.TitleLayout;

import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;

public class MoreTransactionMessageActivity extends BaseActivity implements View.OnClickListener, WalletListDialog.WalletListSelectItemListener {

    private String mSearchAddress = WalletManager.getAddress();
    private TextView mAddressView;
    private LoadingAndErrorView loadingAndErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_more_transaction_message);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MoreTransactionMessageActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_message_transaction));
        mAddressView = findViewById(R.id.tv_address);
        mAddressView.setText(mSearchAddress);
        mAddressView.setOnClickListener(this);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        MoreMessageFragmentPagerAdapter adapter = new MoreMessageFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        loadingAndErrorView = findViewById(R.id.loading_and_error);
    }

    public void setLoadingShow(boolean isShow) {
        if (isShow) {
            loadingAndErrorView.setVisibility(View.VISIBLE);
        } else {
            loadingAndErrorView.setVisibility(View.GONE);
        }
    }
    public String getWalletAddress(){
        return mSearchAddress;
    }

    @Override
    public void onClick(View v) {
        if (v == mAddressView) {
            //选择钱包地址
            WalletListDialog walletListDialog = new WalletListDialog(this);
            walletListDialog.setWalletListSelectItemListener(this);
            walletListDialog.show();
        }
    }

    @Override
    public void selectItem(String id) {
        mSearchAddress = NumericUtil.prependHexPrefix(WalletManager.getKeyStore(id).getAddress());
        mAddressView.setText(mSearchAddress);
        TransactionObservable.getInstance().update();
    }
}
