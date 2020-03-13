package com.idhub.wallet.dapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.zxinglib.QrCodeActivity;
import com.idhub.wallet.main.MainTopTitleView;

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
//        ViewCalculateUtil.setTextSize(mUrlEdit,15);
//        ViewCalculateUtil.setViewConstraintLayoutParam(mUrlEdit, 335, 40, 15, 0, 0, 0);
        mUrlEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String url = mUrlEdit.getText().toString();
                if (!TextUtils.isEmpty(url)) {
                    Web3Activity.startAction(getContext(), url);
                    return true;
                }
            }
            return false;
        });

        MainTopTitleView titleView = view.findViewById(R.id.title);
        titleView.setTitle(getString(R.string.wallet_dapp));
        titleView.setMenuIsVisible(View.GONE);
        titleView.setScanClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QrCodeActivity.startAction(getActivity(), 100);
            }
        });

        return view;
    }

    @Override
    protected void loadData() {

    }
}
