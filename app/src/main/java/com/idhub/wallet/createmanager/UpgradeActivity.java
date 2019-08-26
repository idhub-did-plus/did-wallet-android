package com.idhub.wallet.createmanager;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.createmanager.walletcreate.MnemonicBackupHintActivity;
import com.idhub.wallet.didhub.RecoverAddress;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.MnemonicUtil;
import com.idhub.wallet.didhub.util.NumericUtil;

import java.util.List;

public class UpgradeActivity extends AppCompatActivity implements View.OnClickListener {

    private String mData;
    private String mRecoverAddressStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upgrade);
        initView();
        initData();
    }

    private void initData() {
        mData = getIntent().getStringExtra("data");
    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, UpgradeActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_upgrade));
        findViewById(R.id.tv_upgrade).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_upgrade:
                //生成恢复地址recoverAddress和助记词。助记词备份完之后提交create 成功之后保存recoverAddress
                createRecoverAddressAndMnemonic();
                break;
        }
    }

    private void createRecoverAddressAndMnemonic() {
        List<String> mnemonicCodes = MnemonicUtil.randomMnemonicCodes();
        RecoverAddress recoverAddress = new RecoverAddress(mnemonicCodes);
        mRecoverAddressStr = NumericUtil.prependHexPrefix(recoverAddress.getAddress());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mnemonicCodes.size(); i++) {
            stringBuilder.append(mnemonicCodes.get(i)).append(" ");
        }
        MnemonicBackupHintActivity.startActionforResult(this, stringBuilder.toString(), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //备份成功进行身份升级注册 。身份升级只能是有第一个address的时候，升级成功设置address为defaultAddress
            WalletOtherInfoSharpreference.getInstance().setRecoverAdress(mRecoverAddressStr);
            DidHubKeyStore keyStore = WalletManager.getKeyStore(mData);
            Wallet wallet = keyStore.getWallet();
            wallet.setIsgl(true);
            wallet.setDefaultAddress(true);
            WalletManager.flushWallet(keyStore, true);
            MainActivity.startAction(this,"upgrade");
            WalletSelectedObservable.getInstance().update();
        }
    }
}
