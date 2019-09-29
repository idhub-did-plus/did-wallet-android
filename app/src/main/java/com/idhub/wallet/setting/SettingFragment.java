package com.idhub.wallet.setting;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.setting.message.HistoryMessageActivity;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;
import com.idhub.wallet.wallet.walletsetting.WalletSettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends MainBaseFragment implements View.OnClickListener {


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_setting, container, false);
        view.findViewById(R.id.history_message).setOnClickListener(this);
        view.findViewById(R.id.wallet_manager).setOnClickListener(this);
        view.findViewById(R.id.node_setup).setOnClickListener(this);
        view.findViewById(R.id.about_we).setOnClickListener(this);
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_setting_title));
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
            case R.id.wallet_manager:
                WalletSettingActivity.startAction(getContext());
                break;
            case R.id.node_setup:
                NodeSettingActivity.startAction(getContext());
                break;
            case R.id.about_we:

                break;
        }
    }
}
