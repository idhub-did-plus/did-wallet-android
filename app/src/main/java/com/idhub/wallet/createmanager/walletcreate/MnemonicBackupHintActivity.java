package com.idhub.wallet.createmanager.walletcreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;

public class MnemonicBackupHintActivity extends BaseActivity implements View.OnClickListener {

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
//        TitleLayout titleLayout = findViewById(R.id.title);
//        titleLayout.setBackImgVisible(View.INVISIBLE);
//        titleLayout.setFirstTextAndClickCallBack("稍后备份", ()->{
////            finish();
//        });
    }

    public static void startActionforResult(Context context, String mnemonic,int requestCode) {
        Intent intent = new Intent(context, MnemonicBackupHintActivity.class);
        intent.putExtra("data", mnemonic);
        ((Activity) context).startActivityForResult(intent,requestCode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_next:
                MnemonicBackupPreviewActivity.startActionForResult(MnemonicBackupHintActivity.this, mMnemonic,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
