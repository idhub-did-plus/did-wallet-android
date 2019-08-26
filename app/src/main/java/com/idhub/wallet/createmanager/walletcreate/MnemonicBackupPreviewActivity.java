package com.idhub.wallet.createmanager.walletcreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.flowlayout.FlowLayout;
import com.idhub.wallet.common.flowlayout.TagAdapter;
import com.idhub.wallet.common.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

public class MnemonicBackupPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private String mMnemonic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_mnemonic_backup_preview);
        init();
    }

    private void init() {
        findViewById(R.id.tv_next).setOnClickListener(this);
        TagFlowLayout tagFlowLayout = findViewById(R.id.gv_mnemonic);
        mMnemonic = getIntent().getStringExtra("data");
        List<String> mnemonicCodes = Arrays.asList(mMnemonic.split(" "));
        tagFlowLayout.setAdapter(new TagAdapter<String>(mnemonicCodes) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View tv = LayoutInflater.from(MnemonicBackupPreviewActivity.this).inflate(R.layout.wallet_mnemonic_preview_item,
                        parent, false);
                TextView textView = tv.findViewById(R.id.mnemonic_preview_item);
                textView.setText(s);
                return tv;
            }
        });
    }

    public static void startActionForResult(Context context, String mnemonic,int requestCode) {
        Intent intent = new Intent(context, MnemonicBackupPreviewActivity.class);
        intent.putExtra("data", mnemonic);
        ((Activity) context).startActivityForResult(intent,requestCode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_next:
                MnemonicBackupConfirmActivity.startActionForResult(MnemonicBackupPreviewActivity.this, mMnemonic,100);
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
}
