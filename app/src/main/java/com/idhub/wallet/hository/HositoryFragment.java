package com.idhub.wallet.hository;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.hository.message.HistoryMessageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HositoryFragment extends MainBaseFragment implements View.OnClickListener {


    public HositoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_hository, container, false);
        view.findViewById(R.id.history_message).setOnClickListener(this);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_message:
                HistoryMessageActivity.startAction(getContext());
                break;
        }
    }
}
