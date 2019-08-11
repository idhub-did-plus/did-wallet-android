package com.idhub.wallet.wallet.export.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExportShowQRCodeFragment extends Fragment implements View.OnClickListener {


    private ImageView mQRCode;

    public ExportShowQRCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_show_qrcode, container, false);
        mQRCode = view.findViewById(R.id.wallet_QR_code);
        view.findViewById(R.id.wallet_show).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.wallet_show:
                //show QRcode
                break;
        }
    }
}
