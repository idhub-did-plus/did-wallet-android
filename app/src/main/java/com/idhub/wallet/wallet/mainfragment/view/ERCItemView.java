package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.idhub.wallet.R;

public class ERCItemView extends ConstraintLayout {


    private TextView mAssetsAmount;
    private TextView mAssetsName;

    public ERCItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAssetsName = findViewById(R.id.tv_assets_name);
        mAssetsAmount = findViewById(R.id.tv_assets_amount);
    }

    public void setName(String name) {
        mAssetsName.setText(name);
    }

    public void setBalance(String balance){
        mAssetsAmount.setText(balance);

    }
}
