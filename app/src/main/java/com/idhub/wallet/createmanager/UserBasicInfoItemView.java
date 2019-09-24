package com.idhub.wallet.createmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.idhub.wallet.R;

public class UserBasicInfoItemView extends LinearLayout {

    private EditText mContentView;

    public UserBasicInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = findViewById(R.id.et_content);
    }

    public void setData(String hint) {
        mContentView.setHint(hint);
    }
    public String getInputData() {
        return mContentView.getText().toString();
    }

}
