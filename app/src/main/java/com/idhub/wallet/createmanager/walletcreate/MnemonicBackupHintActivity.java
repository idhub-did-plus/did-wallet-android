package com.idhub.wallet.createmanager.walletcreate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;

public class MnemonicBackupHintActivity extends AppCompatActivity implements View.OnClickListener {

    private String mMnemonic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_mnemonic_backup_hint);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_next).setOnClickListener(this);
        mMnemonic = getIntent().getStringExtra("data");
        String id = getIntent().getStringExtra("id");
//        TitleLayout titleLayout = findViewById(R.id.title);
//        titleLayout.setBackImgVisible(View.INVISIBLE);
//        titleLayout.setFirstTextAndClickCallBack("稍后备份", ()->{
////            finish();
//        });
    }

    public static void startAction(Context context, String mnemonic,String id) {
        Intent intent = new Intent(context, MnemonicBackupHintActivity.class);
        intent.putExtra("data", mnemonic);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_next:
                MnemonicBackupPreviewActivity.startAction(MnemonicBackupHintActivity.this, mMnemonic);
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }
}
