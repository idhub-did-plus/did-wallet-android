package com.idhub.wallet.assets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;

public class TokenItemView extends ConstraintLayout {

    private TextView nameTextView;
    private TextView valueTextView;
    private ImageView iconView;

    public TokenItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iconView = findViewById(R.id.icon);
        nameTextView = findViewById(R.id.name);
        TextView rateTextView = findViewById(R.id.rate);
        valueTextView = findViewById(R.id.value);
        TextView percentTextView = findViewById(R.id.percent);

    }

    public void setData() {

    }
    public void setName(String name) {
        nameTextView.setText(name);
    }

    public void setBalance(String balance){
        valueTextView.setText(balance);
    }


    public void setAssetsImage(int mipmap) {
        iconView.setImageResource(mipmap);
    }
}
