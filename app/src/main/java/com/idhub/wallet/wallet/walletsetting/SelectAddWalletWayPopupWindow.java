package com.idhub.wallet.wallet.walletsetting;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.idhub.wallet.R;

public class SelectAddWalletWayPopupWindow extends PopupWindow {

    private Context mContxt;
    private View view;
    private View.OnClickListener mOnClickListener;

    public SelectAddWalletWayPopupWindow(Context context,View.OnClickListener onClickListener ) {
        super(context);
        mOnClickListener = onClickListener;
        mContxt = context;
        view = LayoutInflater.from(context).inflate(R.layout.wallet_popup_window_add_wallet, null, false);
        view.findViewById(R.id.tv_create).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.tv_import).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(mOnClickListener);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setAnimationStyle(R.style.WalletAnimation);
        setBackgroundDrawable(new ColorDrawable(0xb0000000));
    }


}
