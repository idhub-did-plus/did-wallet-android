package com.idhub.wallet.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;

public class MainTopTitleView extends ConstraintLayout {

    private View scanView;
    private View menuView;

    public MainTopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View bgView = findViewById(R.id.main_top_title_bg);
        menuView = findViewById(R.id.main_left_menu);
        scanView = findViewById(R.id.main_scan);
        TextView titleView = findViewById(R.id.title);
        ViewCalculateUtil.setViewGroupLayoutParam(bgView, LayoutParams.MATCH_PARENT, 64);
        ViewCalculateUtil.setViewConstraintLayoutParam(menuView, 17, 16, 36, 0, 15, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(scanView, 17, 17, 33, 0, 0, 18);
        ViewCalculateUtil.setViewConstraintLayoutParam(titleView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 30, 0, 0, 0);
        ViewCalculateUtil.setTextSize(titleView, 17);
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
