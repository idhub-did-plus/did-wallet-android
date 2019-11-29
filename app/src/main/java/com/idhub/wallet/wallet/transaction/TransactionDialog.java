package com.idhub.wallet.wallet.transaction;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idhub.wallet.R;

public class TransactionDialog extends Dialog implements View.OnClickListener {

    public TransactionDialog(Context context,String message) {
        super(context, R.style.WalletShowDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_transaction_dialog_view, null);
        TextView title = view.findViewById(R.id.title);
        TextView messageView = view.findViewById(R.id.message);
        view.findViewById(R.id.confirm).setOnClickListener(this);
        messageView.setText(message);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        params.width = (int) (dm.widthPixels * 0.9);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                dismiss();
                break;
        }
    }
}
