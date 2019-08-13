package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.mainfragment.model.AssetsModel;

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

    public void setData(AssetsModel assetsModel) {
        mAssetsName.setText(assetsModel.getName());
        mAssetsAmount.setText(assetsModel.getBalance());
    }
}
