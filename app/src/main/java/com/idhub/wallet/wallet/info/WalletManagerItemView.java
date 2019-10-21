package com.idhub.wallet.wallet.info;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;

public class WalletManagerItemView extends ConstraintLayout {


    private TextView mName;
    private ImageView mIcon;

    public WalletManagerItemView(Context context, AttributeSet attrs) {
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
