package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;

public class AssetsErc1400ItemView extends ConstraintLayout {


    private ImageView mAssetsAmount;
    private TextView mAssetsName;
    private ImageView mAssetsImage;

    public AssetsErc1400ItemView(Context context, AttributeSet attrs) {
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

    public void setAssetsImage(int mipmap) {
        mAssetsImage.setImageResource(mipmap);
    }
}
