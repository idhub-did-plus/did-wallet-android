package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class BaseInformationLayout extends LinearLayout {
    public BaseInformationLayout(Context context) {
        super(context);
        init();
    }

    public BaseInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.wallet_base_information_layout, this);
    }
}
