package com.idhub.wallet.wallet.export.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.utils.StringUtils;

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
    private String mData;

    public ExportShowContentFragment() {
        // Required empty public constructor
    }

    public static ExportShowContentFragment newInstance(String tag,String data) {
        ExportShowContentFragment fragment = new ExportShowContentFragment();
        Bundle args = new Bundle();
        args.putString("tag", tag);
        args.putString("data", data);
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
            mData = bundle.getString("data");
        }
        View view = inflater.inflate(R.layout.wallet_fragment_show_keystore, container, false);
        mText = view.findViewById(R.id.wallet_content);
        mText.setText(mData);
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
                StringUtils.copy(getContext(),str);
                mBtnText.setText("已复制");
                handler.sendEmptyMessageDelayed(1, 2000);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }
}
