package com.idhub.wallet.wallet.token;

import android.content.Context;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.idhub.wallet.R;

public class TokenManagerHeaderView extends LinearLayout  {

    private View mAddView;

    public TokenManagerHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAddView = findViewById(R.id.add_assets);
    }

    public void setAddClickListener(OnClickListener onClickListener) {
        mAddView.setOnClickListener(onClickListener);
    }
}
