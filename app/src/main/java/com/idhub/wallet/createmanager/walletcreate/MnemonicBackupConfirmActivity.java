package com.idhub.wallet.createmanager.walletcreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.flowlayout.FlowLayout;
import com.idhub.wallet.common.flowlayout.TagAdapter;
import com.idhub.wallet.common.flowlayout.TagFlowLayout;
import com.idhub.wallet.common.flowlayout.TagView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MnemonicBackupConfirmActivity extends BaseActivity implements View.OnClickListener {

    private String mMnemonic;
    private TagFlowLayout mTagFlowMnemonicConfirmLayout;
    private TagFlowLayout mTagFlowMnemonicLayout;
    private TagAdapter<ConfirmMnemonic> mMnemonicConfirmAdapter;
    private TagAdapter<String> mMnemonicAdapter;
    private List<String> mCodes;
    private View mWarningView;
    private List<String> mnemonicCodes;
    private HashMap<Integer, TagView> selectMaps;

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
        mnemonicCodes = new LinkedList<>(mCodes);
        Collections.shuffle(mnemonicCodes);
        LayoutInflater layoutInflater = LayoutInflater.from(MnemonicBackupConfirmActivity.this);
        mMnemonicConfirmAdapter = new TagAdapter<ConfirmMnemonic>(new ArrayList<>()) {
            @Override
            public View getView(FlowLayout parent, int position, ConfirmMnemonic confirmMnemonic) {
                View tv = layoutInflater.inflate(R.layout.wallet_mnemonic_preview_item,
                        parent, false);
                TextView textView = tv.findViewById(R.id.mnemonic_preview_item);
                ImageView errorView = tv.findViewById(R.id.memonic_error_icon);
                View view = tv.findViewById(R.id.mnemonic_layout);
                ViewCalculateUtil.setViewGroupLayoutParam(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ViewCalculateUtil.setViewConstraintLayoutParam(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 10, 0, 10, 0);
                ViewCalculateUtil.setViewPadding(textView,7,7,15,15);
                ViewCalculateUtil.setTextSize(textView,15);
                textView.setText(confirmMnemonic.data);
                if (confirmMnemonic.isCorrect) {
                    errorView.setVisibility(View.GONE);
                } else {
                    errorView.setVisibility(View.VISIBLE);
                }
                return tv;
            }
        };
        mTagFlowMnemonicConfirmLayout.setAdapter(mMnemonicConfirmAdapter);

        mMnemonicAdapter = new TagAdapter<String>(mnemonicCodes) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View tv = layoutInflater.inflate(R.layout.wallet_mnemonic_item,
                        parent, false);
                TextView textView = tv.findViewById(R.id.mnemonic_preview_item);
                ViewCalculateUtil.setViewPadding(textView,7,7,15,15);
                ViewCalculateUtil.setTextSize(textView,15);
//                textView.setBackgroundColor(getResources().getColor(R.color.wallet_line_gray));
                textView.setText(s);
                return tv;
            }
        };
        mTagFlowMnemonicLayout.setAdapter(mMnemonicAdapter);
        selectMaps = new HashMap<>();
        mMnemonicAdapter.setSelectListener(new TagAdapter.SelectListener() {
            @Override
            public void onSelected(int position, TagView view) {
                View tagView = view.getTagView();
                if (tagView instanceof TextView) {
                    TextView textView = (TextView) tagView;
                    textView.setTextColor(getResources().getColor(R.color.wallet_color_D9D9D9));
                    selectMaps.put(position, view);
                }
            }

            @Override
            public void unSelected(int position, TagView view) {
                View tagView = view.getTagView();
                if (tagView instanceof TextView) {
                    TextView textView = (TextView) tagView;
                    textView.setTextColor(getResources().getColor(R.color.wallet_text_black));
                }
            }
        });

        mTagFlowMnemonicLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String item = mMnemonicAdapter.getItem(position);
                mMnemonicConfirmAdapter.addItem(new ConfirmMnemonic(item,true));
                checkConfirmCode();
                return false;
            }
        });

        mTagFlowMnemonicConfirmLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ConfirmMnemonic item = mMnemonicConfirmAdapter.getItem(position);
                mMnemonicConfirmAdapter.removeItem(position);
                //设置下边item恢复状态
                setBottomViewState(item.data);
                checkConfirmCode();
                return false;
            }
        });
    }

    private void setBottomViewState(String item) {
        int i = mnemonicCodes.indexOf(item);
        TagView view = (TagView) selectMaps.get(i);
        mTagFlowMnemonicLayout.doSelect(view, i);
    }

    private void checkConfirmCode() {

        List<ConfirmMnemonic> datas = mMnemonicConfirmAdapter.getSurplusDatas();
        int temp = -1;
        for (int i = 0; i < datas.size(); i++) {
            ConfirmMnemonic confirmMnemonic = datas.get(i);
            if (temp == -1 && !mCodes.get(i).equals(confirmMnemonic.data)) {
                confirmMnemonic.isCorrect = false;
                temp = i;
            } else if (temp != -1 && i > temp) {
                confirmMnemonic.isCorrect = false;
            } else {
                confirmMnemonic.isCorrect = true;
            }
        }
        mMnemonicConfirmAdapter.notifyDataChanged();
//        mWarningView.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        mTagFlowMnemonicConfirmLayout = findViewById(R.id.mnemonic_confirm);
        mTagFlowMnemonicLayout = findViewById(R.id.mnemonic);
        View btnView = findViewById(R.id.tv_next);
        btnView.setOnClickListener(this);
        mWarningView = findViewById(R.id.warning);
        TextView backUpTitle = findViewById(R.id.tv_backup_top);
        TextView backUpHintTitle = findViewById(R.id.tv_backup_hint);
        ViewCalculateUtil.setTextSize(backUpTitle, 20);
        ViewCalculateUtil.setViewConstraintLayoutParam(backUpHintTitle,0, ViewGroup.LayoutParams.WRAP_CONTENT,5,0,25,25);
        ViewCalculateUtil.setViewConstraintLayoutParam(backUpTitle,0, ViewGroup.LayoutParams.WRAP_CONTENT,20,0,25,25);
        ViewCalculateUtil.setTextSize(backUpHintTitle, 13);
        ViewCalculateUtil.setViewConstraintLayoutParam(mTagFlowMnemonicConfirmLayout,0, 219,15,0,25,25);
        ViewCalculateUtil.setViewPadding(mTagFlowMnemonicConfirmLayout, 15, 15, 15, 15);
        ViewCalculateUtil.setViewConstraintLayoutParam(mTagFlowMnemonicLayout,0, ViewGroup.LayoutParams.WRAP_CONTENT,20,0,25,25);
        ViewCalculateUtil.setViewConstraintLayoutParam(btnView, ViewGroup.LayoutParams.MATCH_PARENT, 45,28,15,30,30);

    }

    public static void startActionForResult(Context context, String mnemonic,int requestCode) {
        Intent intent = new Intent(context, MnemonicBackupConfirmActivity.class);
        intent.putExtra("data", mnemonic);
        ((Activity) context).startActivityForResult(intent,requestCode);
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
        List<ConfirmMnemonic> datas = mMnemonicConfirmAdapter.getSurplusDatas();
        if (datas.size() != mCodes.size()) {
            return;
        }
        boolean isSuccess = true;
        for (int i = 0; i < datas.size(); i++) {
            if (!mCodes.get(i).equals(datas.get(i).data)) {
                isSuccess = false;
            }
        }
        if (isSuccess) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private class ConfirmMnemonic{
        public ConfirmMnemonic(String data, boolean isCorrect) {
            this.data = data;
            this.isCorrect = isCorrect;
        }

        public String data;
        public boolean isCorrect;

    }
}
