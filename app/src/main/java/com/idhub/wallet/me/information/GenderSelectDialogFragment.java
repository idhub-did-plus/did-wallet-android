package com.idhub.wallet.me.information;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idhub.wallet.R;

public class GenderSelectDialogFragment extends DialogFragment implements View.OnClickListener {


    private GenderSelectDialogFragmentListener mGenderSelectDialogFragmentListener;

    public static GenderSelectDialogFragment getInstance(){
        GenderSelectDialogFragment inputPasswordDialogFragment = new GenderSelectDialogFragment();
        Bundle bundle = new Bundle();
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

    public void setGenderSelectDialogFragmentListener(GenderSelectDialogFragmentListener genderSelectDialogFragmentListener) {
        mGenderSelectDialogFragmentListener = genderSelectDialogFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wallet_gender_select_dialog_fragment, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.male).setOnClickListener(this);
        view.findViewById(R.id.female).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.male:
                if (mGenderSelectDialogFragmentListener != null) {
                    mGenderSelectDialogFragmentListener.genderSelect(getString(R.string.wallet_gender_boy));
                }
                dismiss();
                break;
            case R.id.female:
                if (mGenderSelectDialogFragmentListener != null) {
                    mGenderSelectDialogFragmentListener.genderSelect(getString(R.string.wallet_gender_girl));
                }
                dismiss();
                break;
        }
    }

    public interface GenderSelectDialogFragmentListener {
        void genderSelect(String gender);
    }
}
