package com.idhub.wallet.assets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idhub.wallet.R;

public class AssetsPromptView extends LinearLayout implements View.OnClickListener {

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


        View stIconView = findViewById(R.id.st_icon);
        TextView stTitleView = findViewById(R.id.st_title);

        View stBottomBgView = findViewById(R.id.st_bottom_bg);
        TextView stContent1View = findViewById(R.id.st_content_1);
        TextView stContent2View = findViewById(R.id.st_content_2);

        findViewById(R.id.st_container).setOnClickListener(this);
        findViewById(R.id.identity_container).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.identity_container:

                break;
            case R.id.st_container:

                break;
        }
    }
}
