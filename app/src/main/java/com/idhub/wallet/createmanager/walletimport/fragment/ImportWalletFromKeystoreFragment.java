package com.idhub.wallet.createmanager.walletimport.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.idhub.wallet.R;
import com.idhub.wallet.createmanager.walletimport.ImportWalletTopView;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportWalletFromKeystoreFragment extends Fragment implements View.OnClickListener {


    private EditText mPasswordView;
    private ImportWalletTopView mTopView;

    public ImportWalletFromKeystoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_import_wallet_from_keystore, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.tv_import).setOnClickListener(this);
        mPasswordView = view.findViewById(R.id.password);
        mTopView = view.findViewById(R.id.top_view);
        mTopView.setData(getString(R.string.wallet_import_wallet_from_keystore_tip),getString(R.string.wallet_edit_keystore_tip));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_import:
                String key = mTopView.getKey();
                if (TextUtils.isEmpty(key)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_no_empty_keystore));
                    return;
                }
                String password = mPasswordView.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_no_empty_password));
                    return;
                }
                Wallet wallet = new Wallet();
                wallet.setName("ETH-Wallet");
                wallet.setAssociate(false);

                WalletInfo walletInfo = WalletManager.importWalletFromKeystore(wallet, key, password, true);
                break;
        }
    }
}
