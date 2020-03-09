package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class InformationItemLayout extends LinearLayout {

    private TextView nameView;
    private EditText valueView;

    public InformationItemLayout(Context context) {
        super(context);
        init();
    }

    public InformationItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InformationItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.information_item_layout, this);
        nameView = findViewById(R.id.name);
        valueView = findViewById(R.id.value);
    }

    public void setContent(String name, String valueHint) {
        nameView.setText(name);
        valueView.setHint(valueHint);
    }

    public void setValue(String value) {
        valueView.setText(value);
    }
}
