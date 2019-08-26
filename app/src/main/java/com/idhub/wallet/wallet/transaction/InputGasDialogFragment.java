package com.idhub.wallet.wallet.transaction;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.idhub.wallet.R;
import com.idhub.wallet.utils.ToastUtils;


public class InputGasDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText mGasPriceEditTextView;
    private EditText mGasLimitEditTextView;
    private InputGasDialogFragmentListener mInputGasDialogFragmentListener;
    private String mGasPrice;
    private String mGasLimit;

    public static InputGasDialogFragment getInstance(String gasPrice, String gasLimit) {
        InputGasDialogFragment inputGasDialogFragment = new InputGasDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gasPrice", gasPrice);
        bundle.putString("gasLimit", gasLimit);
        inputGasDialogFragment.setArguments(bundle);
        return inputGasDialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInputGasDialogFragmentListener = (InputGasDialogFragmentListener) context;
        } catch (Exception e) {
            throw new ClassCastException(((Activity) context).toString() + " must implementon MyDialogFragment_Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mGasPrice = bundle.getString("gasPrice");
            mGasLimit = bundle.getString("gasLimit");
        }
        View view = inflater.inflate(R.layout.wallet_fragment_dialog_input_gas, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);
        mGasPriceEditTextView = view.findViewById(R.id.et_gas_amount);
        mGasLimitEditTextView = view.findViewById(R.id.et_gas_limit);
        mGasPriceEditTextView.setText(mGasPrice);
        mGasLimitEditTextView.setText(mGasLimit);
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
                String gasPrice = mGasPriceEditTextView.getText().toString();
                String gasLimit = mGasLimitEditTextView.getText().toString();
                if (TextUtils.isEmpty(gasPrice)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_gasPrice_not_empty));
                    return;
                }
                if (TextUtils.isEmpty(gasLimit)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_gasLimit_not_empty));
                    return;
                }
                if (mInputGasDialogFragmentListener != null) {
                    mInputGasDialogFragmentListener.inputGasConfirm(gasPrice, gasLimit);
                }
                dismiss();
                break;
        }
    }


    public interface InputGasDialogFragmentListener {
        void inputGasConfirm(String gasPrice, String gasLimit);
    }


}
