package com.idhub.wallet.createmanager.walletimport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.createmanager.walletcreate.InputPasswordActivity;
import com.idhub.wallet.hository.message.HistoryMessageActivity;
import com.idhub.wallet.hository.message.MessageFragmentPagerAdapter;
import com.idhub.wallet.hository.message.moretransaction.MoreTransactionMessageActivity;

public class ImportWalletActivity extends AppCompatActivity {
    public static final String IMPORT = "import";
    public static final String NO_IMPORT_EQUAL_ADDRESS = "no_import_equal_address";
    public static final String IMPORT_FIRST_ASSOCIATION = "import_first_association";
    public static final String IMPORT_EQUAL_ASSOCIATION = "import_equal_association";
    public static final String NO_IMPORT_OTHER_IDENTIFY = "no_import_other_identify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_import_wallet);
        initView();
    }

    public static void startActionForResult(Context context, int requestCode) {
        Intent intent = new Intent(context, ImportWalletActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_import_wallt));
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ImportWalletFragmentPagerAdapter adapter = new ImportWalletFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
