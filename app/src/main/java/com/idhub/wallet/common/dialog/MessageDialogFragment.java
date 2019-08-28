package com.idhub.wallet.common.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.idhub.wallet.R;

public class MessageDialogFragment extends DialogFragment implements View.OnClickListener {

    private MessageDialogFragmentListener mMessagePasswordDialogFragmentListener;
    private String mMessage;
    private String mConfirmBtn;


    public static MessageDialogFragment getInstance(String message, String confirmBtn){
        MessageDialogFragment inputPasswordDialogFragment = new MessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("confirmBtn", confirmBtn);
        inputPasswordDialogFragment.setArguments(bundle);
        return inputPasswordDialogFragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setMessagePasswordDialogFragmentListener(MessageDialogFragmentListener listener) {
        mMessagePasswordDialogFragmentListener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMessage = bundle.getString("message");
            mConfirmBtn = bundle.getString("confirmBtn");
        }
        View view = inflater.inflate(R.layout.wallet_fragment_common_dialog_message, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        TextView confirmBtn = view.findViewById(R.id.tv_confirm);
        confirmBtn.setOnClickListener(this);
        confirmBtn.setText(mConfirmBtn);
        TextView message = view.findViewById(R.id.tv_message);
        message.setText(mMessage);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                if (mMessagePasswordDialogFragmentListener != null) {
                    mMessagePasswordDialogFragmentListener.confirm();
                }
                dismiss();
                break;
        }
    }

    public interface MessageDialogFragmentListener {
        void confirm();
    }
}
