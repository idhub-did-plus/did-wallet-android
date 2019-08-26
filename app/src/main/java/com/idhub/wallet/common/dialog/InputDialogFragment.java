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
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.wallet.R;

public class InputDialogFragment extends DialogFragment implements View.OnClickListener {

    private InputDialogFragmentListener mInputPasswordDialogFragmentListener;
    private EditText mPasswordEditText;
    private String mData;
    private String mTitle;
    private TextView mTitleView;
    private int mInputType;

    public static InputDialogFragment getInstance(String source,String title,int type){
        InputDialogFragment inputPasswordDialogFragment = new InputDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("source", source);
        bundle.putString("title", title);
        bundle.putInt("type", type);
        inputPasswordDialogFragment.setArguments(bundle);
        return inputPasswordDialogFragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInputPasswordDialogFragmentListener = (InputDialogFragmentListener) context;
        } catch (Exception e) {
            throw new ClassCastException(((Activity) context).toString() + " must implementon MyDialogFragment_Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mData = bundle.getString("source");
            mTitle = bundle.getString("title");
            mInputType = bundle.getInt("type");
        }
        View view = inflater.inflate(R.layout.wallet_fragment_common_dialog_input, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);
        mTitleView = view.findViewById(R.id.tv_title);
        mTitleView.setText(mTitle);
        mPasswordEditText = view.findViewById(R.id.et_password);
        mPasswordEditText.setInputType(mInputType);
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
                    mInputPasswordDialogFragmentListener.inputConfirm(password,mData);
                }
                dismiss();
                break;
        }
    }

    public void setEditTextInputType(int type){
    }
    public interface InputDialogFragmentListener {

        void inputConfirm(String data,String source);
    }
}
