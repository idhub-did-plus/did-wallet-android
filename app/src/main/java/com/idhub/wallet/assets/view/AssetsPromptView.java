package com.idhub.wallet.assets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;

public class AssetsPromptView extends LinearLayout {

    public AssetsPromptView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View iconView = findViewById(R.id.identity_icon);
        TextView identityTitleView = findViewById(R.id.identity_title);
        ImageView identityBottomBgView = findViewById(R.id.identity_bottom_bg);
        TextView content1View = findViewById(R.id.identity_content_1);
        TextView content2View = findViewById(R.id.identity_content_2);
        TextView content3View = findViewById(R.id.identity_content_3);
        ViewCalculateUtil.setTextSize(content1View, 12);
        ViewCalculateUtil.setTextSize(content2View, 12);
        ViewCalculateUtil.setTextSize(content3View, 12);
        ViewCalculateUtil.setViewConstraintLayoutParam(content1View, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 16, 0, 48, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(content2View, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 10, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(content3View, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 10, 0, 0, 0);

        ViewCalculateUtil.setViewConstraintLayoutParam(identityBottomBgView, 0, 115, 2, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(identityTitleView, 313, 0);
        ViewCalculateUtil.setTextSize(identityTitleView, 18);
        ViewCalculateUtil.setViewConstraintLayoutParam(iconView, 30, 30, 8, 0, 16, 0);


        View stIconView = findViewById(R.id.st_icon);
        ViewCalculateUtil.setViewConstraintLayoutParam(stIconView,30,30,12,0,16,0);
        TextView stTitleView = findViewById(R.id.st_title);
        ViewCalculateUtil.setViewConstraintLayoutParam(stTitleView,313,30);
        ViewCalculateUtil.setTextSize(stTitleView, 18);
        View stBottomBgView = findViewById(R.id.st_bottom_bg);
        ViewCalculateUtil.setViewConstraintLayoutParam(stBottomBgView, 0, 84, 2, 0, 0, 0);
        TextView stContent1View = findViewById(R.id.st_content_1);
        TextView stContent2View = findViewById(R.id.st_content_2);
        ViewCalculateUtil.setTextSize(stContent1View, 12);
        ViewCalculateUtil.setTextSize(stContent2View, 12);
        ViewCalculateUtil.setViewConstraintLayoutParam(stContent1View, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 14, 0, 48, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(stContent2View, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 10, 0, 0, 0);

    }
}
