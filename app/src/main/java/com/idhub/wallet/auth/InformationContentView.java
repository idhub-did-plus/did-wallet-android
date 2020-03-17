package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.idhub.wallet.R;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class InformationContentView extends CardView {

    private LinearLayout contentLayoutView;
    private TextView titleNameView;
    private ImageView titleIconView;

    public InformationContentView(Context context) {
        super(context);
        init();
    }

    public InformationContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InformationContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setRadius(AutoSizeUtils.dp2px(getContext(), 4));
        inflate(getContext(), R.layout.wallet_information_title_layout, this);
        titleNameView = findViewById(R.id.info_title_name);
        titleIconView = findViewById(R.id.info_title_icon);
        View titleLayoutView = findViewById(R.id.info_title);
        ImageView arrowIconView = findViewById(R.id.arrow_icon);
        contentLayoutView = findViewById(R.id.info_content);
        titleLayoutView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = contentLayoutView.getVisibility();
                if (visibility == View.VISIBLE) {
                    contentLayoutView.setVisibility(GONE);
                    arrowIconView.setImageResource(R.mipmap.wallet_down_arrow);
                } else {
                    contentLayoutView.setVisibility(VISIBLE);
                    arrowIconView.setImageResource(R.mipmap.wallet_top_arrow);
                }
            }
        });

    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        return super.addViewInLayout(child, index, params);
    }

    public void setContent(int resId, String title) {
        titleIconView.setImageResource(resId);
        titleNameView.setText(title);
    }

    public void addView(View view) {
        contentLayoutView.addView(view);
    }
}
