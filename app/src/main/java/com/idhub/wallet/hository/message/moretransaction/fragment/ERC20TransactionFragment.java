package com.idhub.wallet.hository.message.moretransaction.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ERC20TransactionFragment extends Fragment {


    public ERC20TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallet_fragment_erc20_transaction, container, false);
    }

}
