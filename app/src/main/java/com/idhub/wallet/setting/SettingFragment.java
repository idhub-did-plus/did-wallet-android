package com.idhub.wallet.setting;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.main.MainTopTitleView;
import com.idhub.wallet.setting.message.HistoryMessageActivity;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends MainBaseFragment implements View.OnClickListener {


    private SetItemView walletContainer;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_setting, container, false);
        walletContainer = view.findViewById(R.id.wallet_container);
        walletContainer.setData(R.mipmap.wallet_main_set_wallet, getString(R.string.wallet_wallet_manager));
        initData();
        walletContainer.setOnClickListener(this);
        SetItemView messageContainer = view.findViewById(R.id.message_container);
        messageContainer.setData(R.mipmap.wallet_main_set_message,getString(R.string.wallet_message_center));
        messageContainer.setOnClickListener(this);
        SetItemView nodeContainer = view.findViewById(R.id.node_container);
        nodeContainer.setData(R.mipmap.wallet_main_set_node,getString(R.string.wallet_node_setup));
        nodeContainer.setOnClickListener(this);
        SetItemView languageContainer = view.findViewById(R.id.language_container);
        languageContainer.setData(R.mipmap.wallet_main_set_languages,getString(R.string.wallet_language));
        languageContainer.setOnClickListener(this);
        SetItemView userAgreementContainer = view.findViewById(R.id.user_agreement_container);
        userAgreementContainer.setData(R.mipmap.wallet_main_set_agreement,getString(R.string.wallet_user_agreement));
        userAgreementContainer.setOnClickListener(this);
        SetItemView aboutContainer = view.findViewById(R.id.about_container);
        aboutContainer.setData(R.mipmap.wallet_main_set_about,getString(R.string.wallet_about_we));
        aboutContainer.setOnClickListener(this);

        MainTopTitleView mainTopTitleView = view.findViewById(R.id.title);
        mainTopTitleView.setScanIsVisible(View.GONE);
        mainTopTitleView.setMenuIsVisible(View.GONE);
        mainTopTitleView.setTitle(getString(R.string.wallet_setting_title));
        WalletSelectedObservable.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                initData();
            }
        });
        return view;
    }

    private void initData() {
        WalletKeystore currentKeyStore = WalletManager.getCurrentKeyStore();
        if (currentKeyStore != null) {
            walletContainer.setValue(currentKeyStore.getWallet().getName());
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_container:
                HistoryMessageActivity.startAction(getContext());
                break;
            case R.id.wallet_container:
                WalletManagerActivity.startAction(getContext());
                break;
            case R.id.node_container:
                NodeSettingActivity.startAction(getContext());
                break;
            case R.id.about_container:
                AboutWeActivity.startAction(getContext());
                break;
            case R.id.language_container:
                LanguagesActivity.startAction(getContext());
                break;
            case R.id.user_agreement_container:
                //用户协议
                break;
        }
    }
}
