package com.idhub.wallet.identitymanager.identitycreate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.flowlayout.FlowLayout;
import com.idhub.wallet.common.flowlayout.TagAdapter;
import com.idhub.wallet.common.flowlayout.TagFlowLayout;
import com.idhub.wallet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MnemonicBackupConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private String mMnemonic;
    private TagFlowLayout mTagFlowMnemonicConfirmLayout;
    private TagFlowLayout mTagFlowMnemonicLayout;
    private TagAdapter<String> mMnemonicConfirmAdapter;
    private TagAdapter<String> mMnemonicAdapter;
    private List<String> mCodes;
    private View mWarningView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_mnemonic_backup_confirm);
        initView();
        initData();
    }

    private void initData() {
        mMnemonic = getIntent().getStringExtra("data");
        mCodes = Arrays.asList(mMnemonic.split(" "));
        List<String> mnemonicCodes = new ArrayList<>(mCodes);
        Collections.shuffle(mnemonicCodes);
        LayoutInflater layoutInflater = LayoutInflater.from(MnemonicBackupConfirmActivity.this);
        mMnemonicConfirmAdapter = new TagAdapter<String>(new ArrayList<>()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View tv = layoutInflater.inflate(R.layout.wallet_mnemonic_preview_item,
                        parent, false);
                TextView textView = tv.findViewById(R.id.mnemonic_preview_item);
                textView.setText(s);
                return tv;
            }
        };
        mTagFlowMnemonicConfirmLayout.setAdapter(mMnemonicConfirmAdapter);

        mMnemonicAdapter = new TagAdapter<String>(mnemonicCodes) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View tv = layoutInflater.inflate(R.layout.wallet_mnemonic_preview_item,
                        parent, false);
                TextView textView = tv.findViewById(R.id.mnemonic_preview_item);
                textView.setBackgroundColor(getResources().getColor(R.color.wallet_line_gray));
                textView.setText(s);
                return tv;
            }
        };
        mTagFlowMnemonicLayout.setAdapter(mMnemonicAdapter);
        mTagFlowMnemonicLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String s = mMnemonicAdapter.getItem(position);
                mMnemonicConfirmAdapter.addItem(s);
                mMnemonicAdapter.removeItem(position);
                checkConfirmCode();
                return false;
            }
        });

        mTagFlowMnemonicConfirmLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String item = mMnemonicConfirmAdapter.getItem(position);
                mMnemonicConfirmAdapter.removeItem(position);
                mMnemonicAdapter.addItem(item);
                checkConfirmCode();
                return false;
            }
        });
    }

    private void checkConfirmCode() {
        List<String> confirmDatas = mMnemonicConfirmAdapter.getSurplusDatas();
        for (int i = 0; i < confirmDatas.size(); i++) {
            if (!mCodes.get(i).equals(confirmDatas.get(i))) {
                mWarningView.setVisibility(View.VISIBLE);
                return;
            }
        }
        mWarningView.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        mTagFlowMnemonicConfirmLayout = findViewById(R.id.mnemonic_confirm);
        mTagFlowMnemonicLayout = findViewById(R.id.mnemonic);
        findViewById(R.id.tv_next).setOnClickListener(this);
        mWarningView = findViewById(R.id.warning);

    }

    public static void startAction(Context context, String mnemonic) {
        Intent intent = new Intent(context, MnemonicBackupConfirmActivity.class);
        intent.putExtra("data", mnemonic);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_next:
                //success
                checkSuccess();
                break;
        }
    }

    private void checkSuccess() {
        List<String> confirmDatas = mMnemonicConfirmAdapter.getSurplusDatas();
        for (int i = 0; i < confirmDatas.size(); i++) {
            if (!mCodes.get(i).equals(confirmDatas.get(i))) {
                return;
            }
        }
        MainActivity.startAction(MnemonicBackupConfirmActivity.this);
    }
}
