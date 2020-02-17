package com.idhub.wallet.assets.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TokenFragment extends Fragment {


    public TokenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_token, container, false);
        return view;
    }

}
