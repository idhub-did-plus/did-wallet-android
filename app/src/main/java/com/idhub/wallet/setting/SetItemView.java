package com.idhub.wallet.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;

public class SetItemView extends LinearLayout {

    private ImageView iconView;
    private TextView nameView;
    private TextView valueView;

    public SetItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View bgView = findViewById(R.id.item_bg);
        iconView = findViewById(R.id.set_item_icon);
        nameView = findViewById(R.id.set_item_name);
        valueView = findViewById(R.id.value);
        ImageView arrowView = findViewById(R.id.set_item_arrow_right);
        ViewCalculateUtil.setViewConstraintLayoutParam(bgView, ViewGroup.LayoutParams.MATCH_PARENT, 40);
        ViewCalculateUtil.setViewLinearLayoutParam(iconView, 24, 24, 9, 7, 16, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(nameView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 16, 0);
        ViewCalculateUtil.setTextSize(nameView, 15);
        ViewCalculateUtil.setViewLinearLayoutParam(valueView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 0, 11);
        ViewCalculateUtil.setTextSize(valueView, 12);
        ViewCalculateUtil.setViewLinearLayoutParam(arrowView, 7, 11, 0, 0, 0, 16);
    }

    public void setData(int imageResource,String nameValue){
        iconView.setImageResource(imageResource);
        nameView.setText(nameValue);
    }

    public void setValue(String value) {
        valueView.setVisibility(VISIBLE);
        valueView.setText(value);
    }
}
