package com.idhub.wallet.common.title;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.idhub.wallet.R;
import com.idhub.wallet.common.statusbar.SystemBarTintManager;
import com.idhub.wallet.utils.UnitUtils;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class TitleLayout extends ConstraintLayout implements View.OnClickListener {


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ImageView mBackImageView;
    private TextView mTitleView;
    private TextView mImageOneView;
    private TextView mImageTwoView;
    private View titleView;
    private OnImageClickCallbackListener mOnFirstClickCallbackListener;
    private OnImageClickCallbackListener mOnSecondClickCallbackListener;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackImageView = findViewById(R.id.back_icon);
        mTitleView = findViewById(R.id.tv_title);
        mImageOneView = findViewById(R.id.image_one);
        mImageOneView.setVisibility(GONE);
        mImageTwoView = findViewById(R.id.image_two);
        mImageTwoView.setVisibility(GONE);
        mBackImageView.setOnClickListener(this);
        titleView = findViewById(R.id.title_view);
        immerseTitle();
    }

    @Override
    public void onClick(View v) {
        if (v == mBackImageView) {
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
    }

    public void setBackImg(int resId){
        mBackImageView.setImageResource(resId);
    }

    public void setBackImgVisible(int visibility){
        mBackImageView.setVisibility(visibility);
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

    public void setTitleColor(int resId) {
        mTitleView.setTextColor(getResources().getColor(resId));
    }

    public void setBackGroundColor(int resId) {
        this.setBackgroundColor(getResources().getColor(resId));
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

    public void immerseTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int mScreenWidth = displayMetrics.widthPixels;
            int paddingTop = SystemBarTintManager.getStatusBarHeight(getContext());
            titleView.setPadding(titleView.getPaddingLeft(), paddingTop, titleView.getPaddingRight(), titleView.getPaddingBottom());
            RelativeLayout.LayoutParams localObject = new RelativeLayout.LayoutParams(mScreenWidth, AutoSizeUtils.dp2px(getContext(),48) + paddingTop);
            titleView.setLayoutParams(localObject);
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
