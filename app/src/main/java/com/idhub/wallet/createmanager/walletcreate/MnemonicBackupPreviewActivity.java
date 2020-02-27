package com.idhub.wallet.createmanager.walletcreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.flowlayout.FlowLayout;
import com.idhub.wallet.common.flowlayout.TagAdapter;
import com.idhub.wallet.common.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

public class MnemonicBackupPreviewActivity extends BaseActivity implements View.OnClickListener {

    private String mMnemonic;
    private MnemonicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_mnemonic_backup_preview);
        initView();
        initData();
    }

    private void initData() {
        mMnemonic = getIntent().getStringExtra("data");
        List<String> mnemonicCodes = Arrays.asList(mMnemonic.split(" "));
        adapter.addAll(mnemonicCodes);
    }

    private void initView() {
        TextView backUpTitleView = findViewById(R.id.tv_backup_top);
        TextView backUpHintView = findViewById(R.id.tv_backup_hint);
        ViewCalculateUtil.setTextSize(backUpHintView, 13);
        ViewCalculateUtil.setViewConstraintLayoutParam(backUpHintView, 0, ViewGroup.LayoutParams.WRAP_CONTENT, 5, 0, 25, 25);
        ViewCalculateUtil.setTextSize(backUpTitleView, 20);
        ViewCalculateUtil.setViewConstraintLayoutParam(backUpTitleView, 0, ViewGroup.LayoutParams.WRAP_CONTENT, 20, 0, 25, 25);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ViewCalculateUtil.setViewConstraintLayoutParam(recyclerView, 0, ViewGroup.LayoutParams.WRAP_CONTENT, 30, 0, 18, 18);
        TextView btnView = findViewById(R.id.tv_next);
        ViewCalculateUtil.setViewConstraintLayoutParam(btnView, ViewGroup.LayoutParams.MATCH_PARENT, 45, 50, 0, 30, 30);
        ViewCalculateUtil.setTextSize(btnView, 20);
        btnView.setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MnemonicAdapter(this);
        recyclerView.setAdapter(adapter);
//        TagFlowLayout tagFlowLayout = findViewById(R.id.gv_mnemonic);
//        mMnemonic = getIntent().getStringExtra("data");
//        List<String> mnemonicCodes = Arrays.asList(mMnemonic.split(" "));
//        tagFlowLayout.setAdapter(new TagAdapter<String>(mnemonicCodes) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                View tv = LayoutInflater.from(MnemonicBackupPreviewActivity.this).inflate(R.layout.wallet_mnemonic_preview_item,
//                        parent, false);
//                TextView textView = tv.findViewById(R.id.mnemonic_preview_item);
//                textView.setText(s);
//                return tv;
//            }
//        });
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
