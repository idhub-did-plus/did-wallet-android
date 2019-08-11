package com.idhub.wallet.common.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.idhub.wallet.R;

public class InputPasswordDialogFragment extends DialogFragment implements View.OnClickListener {

    private InputPasswordDialogFragmentListener mInputPasswordDialogFragmentListener;
    private EditText mPasswordEditText;
    private String mData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInputPasswordDialogFragmentListener = (InputPasswordDialogFragmentListener) context;
        } catch (Exception e) {
            throw new ClassCastException(((Activity) context).toString() + " must implementon MyDialogFragment_Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mData = bundle.getString("data");
        }
        View view = inflater.inflate(R.layout.wallet_fragment_dialog_input_password, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);
        mPasswordEditText = view.findViewById(R.id.et_password);
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
                String password = mPasswordEditText.getText().toString();
                if (mInputPasswordDialogFragmentListener != null) {
                    mInputPasswordDialogFragmentListener.inputPasswordConfirm(password,mData);
                }
                dismiss();
                break;
        }
    }

    public interface InputPasswordDialogFragmentListener {

        void inputPasswordConfirm(String password,String data);
    }
}
