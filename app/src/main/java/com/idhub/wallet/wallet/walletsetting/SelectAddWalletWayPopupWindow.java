package com.idhub.wallet.wallet.walletsetting;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.idhub.wallet.R;
import com.idhub.wallet.identitymanager.identitycreate.InputPasswordActivity;

public class SelectAddWalletWayPopupWindow extends PopupWindow implements View.OnClickListener {

    private Context mContxt;
    private View view;

    public SelectAddWalletWayPopupWindow(Context context) {
        super(context);
        mContxt = context;
        view = LayoutInflater.from(context).inflate(R.layout.wallet_popup_window_add_wallet, null, false);
        view.findViewById(R.id.tv_create).setOnClickListener(this);
        view.findViewById(R.id.tv_import).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setAnimationStyle(R.style.WalletAnimation);
        setBackgroundDrawable(new ColorDrawable(0xb0000000));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_create:
                InputPasswordActivity.startAction(mContxt);
                break;
            case R.id.tv_import:
                break;
            case R.id.tv_cancel:
                break;
        }
        dismiss();
    }
}
