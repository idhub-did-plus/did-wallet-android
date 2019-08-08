package com.idhub.wallet.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;

public class TitleLayout extends ConstraintLayout implements View.OnClickListener {


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ImageView mBackImageView;
    private TextView mBackTextView;
    private TextView mTitleView;
    private TextView mImageOneView;
    private TextView mImageTwoView;
    private OnImageClickCallbackListener mOnFirstClickCallbackListener;
    private OnImageClickCallbackListener mOnSecondClickCallbackListener;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackImageView = findViewById(R.id.back_icon);
        mBackTextView = findViewById(R.id.back_text);
        mTitleView = findViewById(R.id.tv_title);
        mImageOneView = findViewById(R.id.image_one);
        mImageOneView.setVisibility(GONE);
        mImageTwoView = findViewById(R.id.image_two);
        mImageTwoView.setVisibility(GONE);
        mBackImageView.setOnClickListener(this);
        mBackTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBackImageView || v == mBackTextView) {
            ((Activity) getContext()).finish();
        }
        if (v == mImageOneView && mOnFirstClickCallbackListener != null) {
            mOnFirstClickCallbackListener.onImageClick();
        }
        if (v == mImageTwoView && mOnSecondClickCallbackListener != null) {
            mOnSecondClickCallbackListener.onImageClick();
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mBackImageView.setOnClickListener(onClickListener);
        mBackTextView.setOnClickListener(onClickListener);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setTitleWithLeftImg(String title, int imgRes) {
        mTitleView.setText(title);
        Drawable drawable = getResources().getDrawable(imgRes);
        mTitleView.setCompoundDrawablePadding(15);
        mTitleView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setTitle(SpannableString title) {
        mTitleView.setText(title);
    }

    public void setFirstImageAndClickCallBack(int imageOneResId, OnImageClickCallbackListener onImageClickCallbackListener) {
        mOnFirstClickCallbackListener = onImageClickCallbackListener;
        if (imageOneResId != -1) {
            mImageTwoView.setText("");
            mImageOneView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(imageOneResId), null, null, null);
            mImageOneView.setVisibility(VISIBLE);
            mImageOneView.setOnClickListener(this);
        } else {
            mImageOneView.setVisibility(GONE);
        }
    }

    public void setFirstTextAndClickCallBack(String TextOneContent, OnImageClickCallbackListener onImageClickCallbackListener) {
        mOnFirstClickCallbackListener = onImageClickCallbackListener;
        if (!TextUtils.isEmpty(TextOneContent)) {
            mImageOneView.setText(TextOneContent);
            mImageOneView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            mImageOneView.setVisibility(VISIBLE);
            mImageOneView.setOnClickListener(this);
        } else {
            mImageOneView.setVisibility(GONE);
        }
    }

    public void setSecondTextAndClickCallBack(String TextTwoContent, OnImageClickCallbackListener onImageClickCallbackListener) {
        mOnSecondClickCallbackListener = onImageClickCallbackListener;
        if (!TextUtils.isEmpty(TextTwoContent)) {
            mImageTwoView.setText(TextTwoContent);
            mImageOneView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            mImageTwoView.setVisibility(VISIBLE);
            mImageTwoView.setOnClickListener(this);
        } else {
            mImageTwoView.setVisibility(GONE);
        }
    }

    public void setSecondImageAndClickCallBack(int imageTwoResId, OnImageClickCallbackListener onImageClickCallbackListener) {
        mOnSecondClickCallbackListener = onImageClickCallbackListener;
        if (imageTwoResId != -1) {
            mImageTwoView.setText("");
            mImageTwoView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(imageTwoResId), null, null, null);
            mImageTwoView.setVisibility(VISIBLE);
            mImageTwoView.setOnClickListener(this);
        } else {
            mImageTwoView.setVisibility(GONE);
        }
    }

    public void setFirstImageVisibility(int visibility) {
        if (mOnFirstClickCallbackListener == null)
            return;
        mImageOneView.setVisibility(visibility);
    }

    public void setSecondImageVisibility(int visibility) {
        if (mOnSecondClickCallbackListener == null)
            return;
        mImageTwoView.setVisibility(visibility);
    }

//    public void setTransparentStyle() {
//        setBackgroundColor(mContext.getResources().getColor(R.color.basic_transparent));
//        mBackImageView.setImageResource(R.mipmap.basic_back_white_icon);
//        mBackTextView.setTextColor(mContext.getResources().getColor(R.color.basic_white));
//        mTitleView.setTextColor(mContext.getResources().getColor(R.color.basic_white));
//
//    }

    public interface OnImageClickCallbackListener {
        void onImageClick();
    }
}
