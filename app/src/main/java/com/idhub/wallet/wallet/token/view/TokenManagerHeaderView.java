package com.idhub.wallet.wallet.token.view;

import android.content.Context;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.token.activity.TokenRemoveActivity;

public class TokenManagerHeaderView extends LinearLayout implements View.OnClickListener {

    private View mAddView;

    public TokenManagerHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View assetsManager = findViewById(R.id.main_asserts_manager);
        assetsManager.setOnClickListener(this);
        mAddView = findViewById(R.id.add_assets);
    }

    public void setAddClickListener(OnClickListener onClickListener) {
        mAddView.setOnClickListener(onClickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_asserts_manager:
                TokenRemoveActivity.startAction(getContext());
                break;
        }
    }
}
