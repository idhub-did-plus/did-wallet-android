package com.idhub.wallet.auth;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.SelectUploadFileTypeDialogFragment;
import com.idhub.wallet.me.information.UploadFileActivity;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class InformationFileLayout extends LinearLayout implements SelectUploadFileTypeDialogFragment.SelectUploadFileTypeDialogFragmentListener {


    private TextView nameView;
    private SelectUploadFileTypeDialogFragment mSelectUploadFileTypeDialogFragment;
    private ImageView fileImageView;
    private ImageView fileAddIconView;
    private String path;

    public InformationFileLayout(Context context) {
        super(context);
        init();
    }

    public InformationFileLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InformationFileLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.information_file_bg);
        setGravity(Gravity.CENTER);
        inflate(getContext(), R.layout.information_file_layout, this);
        nameView = findViewById(R.id.name);
        fileAddIconView = findViewById(R.id.file_add_icon);
        fileImageView = findViewById(R.id.file);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                if (context instanceof AppCompatActivity) {
                    mSelectUploadFileTypeDialogFragment = SelectUploadFileTypeDialogFragment.getInstance("adapter");
                    AppCompatActivity activity = (AppCompatActivity) context;
                    mSelectUploadFileTypeDialogFragment.show(activity.getSupportFragmentManager(), "select_upload_file");
                    mSelectUploadFileTypeDialogFragment.setUploadFileTypeDialogFragmentListeneristener(InformationFileLayout.this);
                }
            }
        });

    }

    public void setNameValue(String value) {
        nameView.setText(value);
    }

    public void setThisOrientation(int orientation) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.setLayoutParams(layoutParams);
        if (orientation == HORIZONTAL) {
            LayoutParams params = new LayoutParams(nameView.getLayoutParams());
            params.topMargin =  AutoSizeUtils.dp2px(getContext(), 12);
            params.bottomMargin = 0;
            params.leftMargin = AutoSizeUtils.dp2px(getContext(), 7);
            params.rightMargin =  AutoSizeUtils.dp2px(getContext(), 12);
            nameView.setLayoutParams(params);
        }
        setOrientation(orientation);
    }

    @Override
    public void selectFileResult(String path, String source) {
        if (mSelectUploadFileTypeDialogFragment != null)
            mSelectUploadFileTypeDialogFragment.dismiss();
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (!TextUtils.isEmpty(source))
            this.path = path;
        if (path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png")) {
            nameView.setVisibility(GONE);
            fileAddIconView.setVisibility(GONE);
            fileImageView.setVisibility(VISIBLE);
            Glide.with(getContext()).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(fileImageView);
        } else {
            nameView.setVisibility(VISIBLE);
            fileAddIconView.setVisibility(GONE);
            fileImageView.setVisibility(GONE);
            nameView.setText(path);
        }
    }

    public void setFile(String path) {
        selectFileResult(path, "");
    }

    public String getFilePath() {
        return path;
    }
}
