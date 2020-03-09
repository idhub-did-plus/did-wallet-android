package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class InformationFileLayout extends LinearLayout {



    public InformationFileLayout(Context context) {
        super(context);
        init();
    }

    public InformationFileLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InformationFileLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.information_file_bg);
        setGravity(Gravity.CENTER);
        inflate(getContext(), R.layout.information_file_layout, this);

    }

}
