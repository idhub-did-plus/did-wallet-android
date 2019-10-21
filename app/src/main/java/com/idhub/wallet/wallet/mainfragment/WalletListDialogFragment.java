package com.idhub.wallet.wallet.mainfragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.wallet.adapter.WalletListAdapter;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class WalletListDialogFragment extends DialogFragment implements View.OnClickListener {

    private String mAddress;
    private WalletListSelectItemListener mWalletListSelectItemListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mAddress = arguments.getString("address");
        }
        View view = inflater.inflate(R.layout.wallet_dialog_fragment_wallet_list, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(view);
        return view;
    }

    public static WalletListDialogFragment getInstance(String address) {
        WalletListDialogFragment walletListDialogFragment = new WalletListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address", address);
        walletListDialogFragment.setArguments(bundle);
        return walletListDialogFragment;
    }

    public void setWalletListSelectItemListener(WalletListSelectItemListener walletListSelectItemListener) {
        this.mWalletListSelectItemListener = walletListSelectItemListener;
    }

    private void initView(View view) {
        view.findViewById(R.id.iv_cancel).setOnClickListener(this);
        view.findViewById(R.id.iv_setting).setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_wallet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Hashtable<String, WalletKeystore> walletKeystores = WalletManager.getWalletKeystores();
        LinkedList<WalletKeystore> didHubMnemonicKeyStores = new LinkedList<>();
        for (Iterator<String> iterator = walletKeystores.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            didHubMnemonicKeyStores.add(walletKeystores.get(key));
        }
        WalletListAdapter walletListAdapter = new WalletListAdapter(getContext(),mAddress);
        walletListAdapter.setOnItemClickListener(id -> {
            dismiss();
            if (mWalletListSelectItemListener != null) {
                mWalletListSelectItemListener.selectItem(id);
            }
        });
        walletListAdapter.addDatas(didHubMnemonicKeyStores);
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
                WalletManagerActivity.startAction(getContext());
                dismiss();
                break;
        }
    }

    public interface WalletListSelectItemListener{
        void selectItem(String id);
    }
}
