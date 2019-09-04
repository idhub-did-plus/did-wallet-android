package com.idhub.wallet.me.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;

public class MeBottomItemView extends ConstraintLayout {

    private TextView mNameView;
    private TextView mStateView;

    public MeBottomItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameView = findViewById(R.id.tv_level_name);
        mStateView = findViewById(R.id.tv_state);
    }

    public void setName(String name) {
        mNameView.setText(name);
    }

    public void setState(String state) {
        mStateView.setText(state);
    }

}
