package com.idhub.wallet.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;

public class MainTopTitleView extends ConstraintLayout {

    private View scanView;
    private View menuView;
    private TextView titleView;

    public MainTopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View bgView = findViewById(R.id.main_top_title_bg);
        menuView = findViewById(R.id.main_left_menu);
        scanView = findViewById(R.id.main_scan);
        titleView = findViewById(R.id.title);
    }

    public void setTitle(String title) {
        titleView.setVisibility(VISIBLE);
        titleView.setText(title);
    }

    public void setScanIsVisible(int visible) {
        scanView.setVisibility(visible);
    }

    public void setScanClickListener(OnClickListener clickListener) {
        scanView.setVisibility(VISIBLE);
        scanView.setOnClickListener(clickListener);
    }

    public void setMenuIsVisible(int visible) {
        menuView.setVisibility(visible);
    }
}
