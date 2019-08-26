package com.idhub.wallet.me.view;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.me.information.UploadFileActivity;

public class MeTopView extends ConstraintLayout implements View.OnClickListener {

    private TextView mRecoverAddressView;

    public MeTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecoverAddressView = findViewById(R.id.tv_recover_address);
        String recoverAdress = WalletOtherInfoSharpreference.getInstance().getRecoverAdress();
        if (!TextUtils.isEmpty(recoverAdress)) {
            mRecoverAddressView.setText("recoverAddress:" + recoverAdress);
        }
        findViewById(R.id.upload_file).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.upload_file:
                UploadFileActivity.startAction(getContext());
                break;
        }
    }
}
