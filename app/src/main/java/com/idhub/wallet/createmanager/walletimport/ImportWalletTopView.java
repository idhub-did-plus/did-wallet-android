package com.idhub.wallet.createmanager.walletimport;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;

public class ImportWalletTopView extends ConstraintLayout {

    private TextView mTipView;
    private EditText mKeyView;

    public ImportWalletTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTipView = findViewById(R.id.tip);
        mKeyView = findViewById(R.id.key);
    }

    public void setData(String tip, String hint) {
        mTipView.setText(tip);
        mKeyView.setHint(hint);
    }

    public String getKey() {
        return mKeyView.getText().toString();
    }
}
