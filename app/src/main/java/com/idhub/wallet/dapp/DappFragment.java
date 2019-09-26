package com.idhub.wallet.dapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DappFragment extends MainBaseFragment {


    private EditText mUrlEdit;

    public DappFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_dapp, container, false);
        mUrlEdit = view.findViewById(R.id.et_dapp_url);
        mUrlEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String url = mUrlEdit.getText().toString();
                if (!TextUtils.isEmpty(url)){
                    Web3Activity.startAction(getContext(), url);
                    return true;
                }
            }
            return false;
        });
        return view;
    }

    @Override
    protected void loadData() {

    }
}
