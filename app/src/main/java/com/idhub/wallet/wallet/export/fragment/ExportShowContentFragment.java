package com.idhub.wallet.wallet.export.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExportShowContentFragment extends Fragment implements View.OnClickListener {


    private TextView mText;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1 && !TextUtils.isEmpty(mTag)) {
                mBtnText.setText("复制 " + mTag);
            }
        }
    };
    private TextView mBtnText;
    private String mTag;

    public ExportShowContentFragment() {
        // Required empty public constructor
    }

    public static ExportShowContentFragment newInstance(String tag) {
        ExportShowContentFragment fragment = new ExportShowContentFragment();
        Bundle args = new Bundle();
        args.putString("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTag = bundle.getString("tag");
        }
        View view = inflater.inflate(R.layout.wallet_fragment_show_keystore, container, false);
        mText = view.findViewById(R.id.wallet_content);
        mBtnText = view.findViewById(R.id.wallet_copy);
        if (!TextUtils.isEmpty(mTag))
            mBtnText.setText("复制 " + mTag);
        mBtnText.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.wallet_copy:
                String str = mText.getText().toString();
                copy(str);
                mBtnText.setText("已复制");
                handler.sendEmptyMessageDelayed(1, 2000);
                break;
        }
    }

    private void copy(String copyStr) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("CopyStr", copyStr);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }
}
