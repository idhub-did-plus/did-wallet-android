package com.idhub.wallet.wallet.manager;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;

public class WalletManagerItem extends ConstraintLayout {


    private TextView mName;
    private ImageView mIcon;

    public WalletManagerItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mName = findViewById(R.id.tv_name);
        mIcon = findViewById(R.id.iv_icon);
    }

    public void setData(int resId,String name) {
        mIcon.setImageResource(resId);
        mName.setText(name);
    }
}
