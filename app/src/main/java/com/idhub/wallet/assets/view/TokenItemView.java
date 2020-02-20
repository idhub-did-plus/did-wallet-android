package com.idhub.wallet.assets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.base.ui.ViewCalculateUtil;
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
        View containerView = findViewById(R.id.container_view);
        iconView = findViewById(R.id.icon);
        nameTextView = findViewById(R.id.name);
        TextView rateTextView = findViewById(R.id.rate);
        valueTextView = findViewById(R.id.value);
        TextView percentTextView = findViewById(R.id.percent);
        ViewCalculateUtil.setViewConstraintLayoutParam(containerView, LayoutParams.MATCH_PARENT, 64, 8, 0,16, 16);
        ViewCalculateUtil.setViewConstraintLayoutParam(iconView,36, 36, 14, 0,16, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(nameTextView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 13, 0, 16, 0);
        ViewCalculateUtil.setTextSize(nameTextView,14);
        ViewCalculateUtil.setTextSize(rateTextView,12);
        ViewCalculateUtil.setViewConstraintLayoutParam(valueTextView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 14, 0, 0, 16);
        ViewCalculateUtil.setTextSize(valueTextView,14);
        ViewCalculateUtil.setTextSize(percentTextView,12);
        ViewCalculateUtil.setViewConstraintLayoutParam(percentTextView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 0, 16);

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
