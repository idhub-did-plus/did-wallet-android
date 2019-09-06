package com.idhub.wallet;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.createmanager.IdentityManagerActivity;
import com.idhub.wallet.createmanager.UploadUserBasicInfoActivity;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.greendao.TransactionRecordDbManager;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;

import java.util.ArrayList;
import java.util.List;

import io.api.etherscan.model.Tx;
import io.api.etherscan.model.TxToken;
import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.interfaces.IncomingListener;
import wallet.idhub.com.clientlib.interfaces.IncomingService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.wallet_activity_main);
        initView();
        // Example of a call to a native method
        IncomingService incomingService = ApiFactory.getIncomingService();
        List<String> accounts = new ArrayList<>();
        accounts.add("0x14bc3096faef7a011ffd655f4a0fd2b534c09d19");
        accounts.add("0x4c000e507be6663e264a1a21507a69bfa5035d95");//小写
        incomingService.setAccounts(accounts);
        incomingService.subscribeTransaction(new IncomingListener<Tx>() {
            @Override
            public void income(List<Tx> txes) {
                List<TransactionRecordEntity> transactionRecordEntities = new ArrayList<>();
                for (Tx tx : txes) {
                    Log.e("LYW", "income:subscribeTransaction " + tx.toString());
                    TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
                    transactionRecordEntity.setTx(tx);
                    transactionRecordEntities.add(transactionRecordEntity);
                }
                new TransactionRecordDbManager().insertListDataTo50Datas(transactionRecordEntities);
            }
        });
        incomingService.subscribeTransfer(new IncomingListener<TxToken>() {
            @Override
            public void income(List<TxToken> txTokens) {
                List<TransactionRecordEntity> transactionRecordEntities = new ArrayList<>();
                for (TxToken txToken : txTokens) {
                    Log.e("LYW", "income:subscribeTransfer " + txToken.toString());
                    TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
                    transactionRecordEntity.setTxToken(txToken);
                    transactionRecordEntities.add(transactionRecordEntity);
                }
                new TransactionRecordDbManager().insertListDataTo50Datas(transactionRecordEntities);
            }
        });

    }

    private void init() {
        // 检查钱包数
        WalletManager.scanWallets();
        int walletNum = WalletManager.getWalletNum();
        if (walletNum <= 0) {
            IdentityManagerActivity.startAction(this);
            finish();
            return;
        }
        //检查是否上传头像和名字
        UserBasicInfoEntity userBasicInfo = UserBasicInfoSharpreference.getInstance().getUserBasicInfo();
        if (TextUtils.isEmpty(userBasicInfo.name)) {
            //检查姓名是否为空，为空则去填写，不为空meFragment加载
            UploadUserBasicInfoActivity.startAction(this);
            finish();
        }
        for (DidHubKeyStore value : WalletManager.getWalletKeystores().values()) {
            Log.e("LYW", "init: " + value.getAddress() );
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String data = intent.getStringExtra("data");
        if ("delete".equals(data)) {
            if (WalletManager.getWalletNum() <= 0) {
                IdentityManagerActivity.startAction(this);
                finish();
                return;
            }
            //删除如果是显示的address 需要通知更新
            WalletSelectedObservable.getInstance().update();
        }else if ("upgrade".equals(data)){
            WalletSelectedObservable.getInstance().update();
        }
    }

    public static void startAction(Context context,String source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("data", source);
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
