package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class CreditInformationLayout extends LinearLayout  {
    public CreditInformationLayout(Context context) {
        super(context);
        init();
    }

    public CreditInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreditInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.wallet_credit_information_layout, this);
    }

}
