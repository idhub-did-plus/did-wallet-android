package com.idhub.wallet.me.information.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.wallet.R;

public class InformationInputItemView extends ConstraintLayout {

    private EditText mInformationEtView;
    private TextView mTitleView;

    public InformationInputItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInformationEtView = findViewById(R.id.et_information);
        mTitleView = findViewById(R.id.tv_title);
    }


    public void setData(String title, String hint) {
        mTitleView.setText(title);
        mInformationEtView.setHint(hint);
    }

    public void setValue(String value) {
        if (!TextUtils.isEmpty(value)) {
            mInformationEtView.setText(value);
        }
    }
    public String getInputData() {
        return mInformationEtView.getText().toString();
    }
}
