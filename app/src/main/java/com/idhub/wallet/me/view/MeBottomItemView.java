package com.idhub.wallet.me.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.me.VipStateType;

public class MeBottomItemView extends ConstraintLayout {

    private TextView mNameView;
    private TextView mStateView;
    private ImageView mVipIcon;
    private int mVipSuccessIcon;
    private int mVipFailIcon;

    public MeBottomItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameView = findViewById(R.id.tv_level_name);
        mVipIcon = findViewById(R.id.iv_vip_icon);
        mStateView = findViewById(R.id.tv_state);
    }

    public void setData(String name,int vipIcon,int vipFailIcon) {
        mNameView.setText(name);
        mVipSuccessIcon = vipIcon;
        mVipFailIcon = vipFailIcon;
    }

    public void setState(String state) {
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            mStateView.setText(getContext().getString(R.string.wallet_no_appply_for));
            setVipFailIcon();
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            mStateView.setText(getContext().getString(R.string.wallet_apply_for_ing));
            setVipFailIcon();
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            mStateView.setText("");
            setVipSuccessIcon();
        } else if (VipStateType.REFUSED_APPLY_FOR.equals(state)) {
            mStateView.setText(getContext().getString(R.string.wallet_refused_apply));
            setVipFailIcon();
        }
    }

    private void setVipSuccessIcon(){
        if (mVipSuccessIcon != 0) {
            mVipIcon.setImageResource(mVipSuccessIcon);
        }
    }
    private void setVipFailIcon(){
        if (mVipFailIcon != 0) {
            mVipIcon.setImageResource(mVipFailIcon);
        }
    }

}
