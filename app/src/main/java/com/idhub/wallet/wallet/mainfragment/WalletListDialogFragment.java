package com.idhub.wallet.wallet.mainfragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.wallet.adapter.WalletListAdapter;
import com.idhub.wallet.wallet.walletsetting.WalletSettingActivity;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class WalletListDialogFragment extends DialogFragment implements View.OnClickListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_dialog_fragment_wallet_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.iv_cancel).setOnClickListener(this);
        view.findViewById(R.id.iv_setting).setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_wallet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Hashtable<String, DidHubKeyStore> walletKeystores = WalletManager.getWalletKeystores();
        LinkedList<DidHubKeyStore> didHubKeyStores = new LinkedList<>();
        for (Iterator<String> iterator = walletKeystores.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            didHubKeyStores.add(walletKeystores.get(key));
        }
        WalletListAdapter walletListAdapter = new WalletListAdapter(getContext());
        walletListAdapter.setOnItemClickListener(() -> {
            dismiss();
            WalletSelectedObservable.getInstance().update();
        });
        walletListAdapter.addDatas(didHubKeyStores);
        recyclerView.setAdapter(walletListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.iv_setting:
                //设置
                WalletSettingActivity.startAction(getContext());
                dismiss();
                break;
        }
    }
}
