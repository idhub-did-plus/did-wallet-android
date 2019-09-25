package com.idhub.wallet.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idhub.wallet.R;
import com.idhub.wallet.utils.ScreenUtil;
import com.idhub.wallet.utils.ToastUtils;

public class SignMessageDialogFragment extends DialogFragment implements View.OnClickListener {

    private SignMessageDialogFragmentListener mSignMessageDialogFragmentListener;
    private EditText mPasswordEditText;
    private String signTitle;
    private String tipContent;
    private String signMessage;
    private String tipTitle;

    public static SignMessageDialogFragment getInstance(String tipTitle,String tipContent, String signTitle, String signMessage) {
        SignMessageDialogFragment inputPasswordDialogFragment = new SignMessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tipContent", tipContent);
        bundle.putString("tipTitle", tipTitle);
        bundle.putString("signTitle", signTitle);
        bundle.putString("signMessage", signMessage);
        inputPasswordDialogFragment.setArguments(bundle);
        return inputPasswordDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setSignMessageListener(SignMessageDialogFragmentListener signMessageListener) {
        mSignMessageDialogFragmentListener = signMessageListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        resizeDialogFragment();
    }

    private void resizeDialogFragment() {
        Dialog dialog = getDialog();
        if (null != dialog) {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
            int screenHeight = ScreenUtil.getScreenHeight(getContext());
            Log.e("LYW", "resizeDialogFragment: " + screenHeight );
            lp.height = (29 * screenHeight / 32);//获取屏幕的宽度，定义自己的宽度
            int screenWidth = ScreenUtil.getScreenWidth(getContext());
            Log.e("LYW", "resizeDialogFragment: " +screenWidth);
            lp.width = (8 * screenWidth / 9);
            if (window != null) {
                window.setLayout(lp.width, lp.height);
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            tipTitle = bundle.getString("tipTitle");
            tipContent = bundle.getString("tipContent");
            signTitle = bundle.getString("signTitle");
            signMessage = bundle.getString("signMessage");
        }
        View view = inflater.inflate(R.layout.wallet_sign_message, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tipTitleView = view.findViewById(R.id.tip_title);
        tipTitleView.setText(tipTitle);
        TextView tipContentView = view.findViewById(R.id.tip_content);
        tipContentView.setText(tipContent);
        TextView signMessageTitleView = view.findViewById(R.id.tv_sign_message);
        signMessageTitleView.setText(signTitle);
        TextView signMessageView = view.findViewById(R.id.sign_message);
        signMessageView.setText(signMessage);
        view.findViewById(R.id.btn_reject).setOnClickListener(this);
        view.findViewById(R.id.button_container).setOnClickListener(this);
        mPasswordEditText = view.findViewById(R.id.et_password);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_reject:
                dismiss();
                break;
            case R.id.button_container:
                String password = mPasswordEditText.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_please_input_content));
                    return;
                }
                if (mSignMessageDialogFragmentListener != null) {
                    mSignMessageDialogFragmentListener.inputConfirm(password);
                }
                dismiss();
                break;
        }
    }


    public interface SignMessageDialogFragmentListener {

        void inputConfirm(String password);
    }
}
