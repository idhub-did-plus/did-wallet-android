package com.idhub.wallet.createmanager.walletimport;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.base.ui.ViewCalculateUtil;
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
        ViewCalculateUtil.setViewConstraintLayoutParam(mKeyView, LayoutParams.MATCH_PARENT, 199, 15, 0, 25, 25);
        ViewCalculateUtil.setViewPadding(mKeyView,15, 0, 15, 15);
        ViewCalculateUtil.setViewConstraintLayoutParam(mTipView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 20, 0, 23, 24);

    }

    public void setData(String tip, String hint) {
        mTipView.setText(tip);
        mKeyView.setHint(hint);
    }

    public String getKey() {
        return mKeyView.getText().toString().trim();
    }
}
