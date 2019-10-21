package com.idhub.wallet.wallet.info;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Keys;

public class AddAssociationAddressDialogFragment extends DialogFragment implements View.OnClickListener {

    private AddAssociationAddressDialogFragmentListener addAssociationAddressDialogFragmentListener;
    private EditText mAssociationPsdEditView;
    private EditText mDefaultPsdEditView;
    private String defaultAddress;
    private String associationAddress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    public static AddAssociationAddressDialogFragment getInstance(String defaultAddress, String associationAddress){
        AddAssociationAddressDialogFragment inputPasswordDialogFragment = new AddAssociationAddressDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("defaultAddress", defaultAddress);
        bundle.putString("associationAddress", associationAddress);
        inputPasswordDialogFragment.setArguments(bundle);
        return inputPasswordDialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setAddAssociationAddressDialogFragmentListener(AddAssociationAddressDialogFragmentListener listener) {
        addAssociationAddressDialogFragmentListener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            defaultAddress = bundle.getString("defaultAddress");
            associationAddress = bundle.getString("associationAddress");
        }
        View view = inflater.inflate(R.layout.wallet_add_association_address_dialog, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        TextView confirmBtn = view.findViewById(R.id.tv_confirm);
        confirmBtn.setOnClickListener(this);
        mDefaultPsdEditView = view.findViewById(R.id.et_default_password);
        mAssociationPsdEditView = view.findViewById(R.id.et_password);
        TextView defaultAddressView = view.findViewById(R.id.default_address);
        defaultAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(defaultAddress)));
        TextView associationAddressView = view.findViewById(R.id.associated_address);
        associationAddressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(associationAddress)));
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
                String defaultPsd = mDefaultPsdEditView.getText().toString();
                if (TextUtils.isEmpty(defaultPsd)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_default_password_empty));
                    return;
                }
                String associationPsd = mAssociationPsdEditView.getText().toString();
                if (TextUtils.isEmpty(associationPsd)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_association_password_empty));
                    return;
                }
                if (addAssociationAddressDialogFragmentListener != null) {
                    addAssociationAddressDialogFragmentListener.confirm(defaultPsd,associationPsd);
                }
                dismiss();
                break;
        }
    }

    public interface AddAssociationAddressDialogFragmentListener {
        void confirm(String defaultPsd,String associationPsd);
    }
}
