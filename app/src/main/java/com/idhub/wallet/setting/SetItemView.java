package com.idhub.wallet.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        iconView = findViewById(R.id.set_item_icon);
        nameView = findViewById(R.id.set_item_name);
        valueView = findViewById(R.id.value);
        ImageView arrowView = findViewById(R.id.set_item_arrow_right);
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
