package com.idhub.wallet.createmanager.walletimport;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.utils.ValidatorUtil;

public class ImportWalletInputPasswordView extends ConstraintLayout {


    private EditText mPasswordView;
    private EditText mRepeatPasswordView;
    private EditText mPasswordTipView;

    public ImportWalletInputPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPasswordView = findViewById(R.id.password);
        mRepeatPasswordView = findViewById(R.id.repeat_password);
        mPasswordTipView = findViewById(R.id.tip_password);
    }

    public boolean verifyPassword() {
        String password = mPasswordView.getText().toString();
        String repeatPassword = mRepeatPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast(getContext().getString(R.string.wallet_no_empty_password));
            return false;
        }
        if (!ValidatorUtil.isPassword(password)) {
            ToastUtils.showLongToast(getContext().getString(R.string.wallet_password_length));
            return false;
        }
        if (TextUtils.isEmpty(repeatPassword)) {
            ToastUtils.showShortToast(getContext().getString(R.string.wallet_no_empty_repeat_password));
            return false;
        }
        if (!password.equals(repeatPassword)) {
            ToastUtils.showShortToast(getContext().getString(R.string.wallet_no_equal_password));
            return false;
        }
        return true;
    }

    public String getPassword(){
        return mPasswordView.getText().toString();
    }

    public String getPasswordTip(){
        return mPasswordTipView.getText().toString();
    }
}
