package com.idhub.wallet.wallet.manager;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.idhub.wallet.R;

public class SelectAddWalletWayDialog extends Dialog {

    private View.OnClickListener mOnClickListener;

    public SelectAddWalletWayDialog(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.WalletShowDialog);
        mOnClickListener = onClickListener;
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_popup_window_add_wallet, null);
        view.findViewById(R.id.tv_create).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.tv_import).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(mOnClickListener);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        params.width = (int) (dm.widthPixels * 0.9);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCanceledOnTouchOutside(true);
    }


}
