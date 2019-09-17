package com.idhub.wallet.me.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.me.VipStateType;

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
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            mStateView.setText(getContext().getString(R.string.wallet_no_appply_for));
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            mStateView.setText(getContext().getString(R.string.wallet_apply_for_ing));
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            mStateView.setText(getContext().getString(R.string.wallet_vip));
        }
    }

}
