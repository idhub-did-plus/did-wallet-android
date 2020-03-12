package com.idhub.wallet.common.loading;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idhub.wallet.R;


public class LoadingAndErrorView extends RelativeLayout {

    private ProgressBar mProgressBar;

    private ImageView mImageView;

    private TextView mTextView;

    private View mBgView;
    private View mContentBgView;


    public LoadingAndErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mProgressBar = findViewById(R.id.progressbar);
        mImageView = findViewById(R.id.image_view);
        mTextView = findViewById(R.id.text_view);
        mContentBgView = findViewById(R.id.content_bg);
        mBgView = findViewById(R.id.bg);
        mBgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        mContentBgView.setOnClickListener(onClickListener);
    }

    public void onLoading() {
        mProgressBar.setVisibility(VISIBLE);
        mTextView.setVisibility(GONE);
        mImageView.setVisibility(GONE);
        setVisibility(VISIBLE);
    }

    public void onGone() {
        mProgressBar.setVisibility(GONE);
        mTextView.setVisibility(GONE);
        mImageView.setVisibility(GONE);
        setVisibility(GONE);
    }

    public void onErrorMessage(String msg) {
        mProgressBar.setVisibility(GONE);
        mTextView.setVisibility(VISIBLE);
        mImageView.setVisibility(VISIBLE);
//        mImageView.setImageResource(R.mipmap.exchange_common_net_error);
        mTextView.setText(msg);
        setVisibility(VISIBLE);
    }

    public void onEmptyMessage(String msg) {
        mProgressBar.setVisibility(GONE);
        mTextView.setVisibility(VISIBLE);
        mImageView.setVisibility(VISIBLE);
//        mImageView.setImageResource(R.mipmap.exchange_common_no_data);
        mTextView.setText(msg);
        setVisibility(VISIBLE);
    }


}
