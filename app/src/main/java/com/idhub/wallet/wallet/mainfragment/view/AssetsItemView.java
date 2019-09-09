package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;

public class AssetsItemView extends ConstraintLayout {


    private TextView mAssetsAmount;
    private TextView mAssetsName;
    private ImageView mAssetsImage;

    public AssetsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAssetsName = findViewById(R.id.tv_assets_name);
        mAssetsAmount = findViewById(R.id.tv_assets_amount);
        mAssetsImage = findViewById(R.id.tv_assets_icon);
    }

    public void setName(String name) {
        mAssetsName.setText(name);
    }

    public void setBalance(String balance){
        mAssetsAmount.setText(balance);

    }
}
