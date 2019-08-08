package com.idhub.wallet.dapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DappFragment extends MainBaseFragment {


    public DappFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallet_fragment_dapp, container, false);
    }

    @Override
    protected void loadData() {

    }
}
