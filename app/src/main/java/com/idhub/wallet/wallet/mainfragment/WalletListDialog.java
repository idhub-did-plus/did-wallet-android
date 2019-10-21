package com.idhub.wallet.wallet.mainfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.wallet.adapter.WalletListAdapter;
import com.idhub.wallet.wallet.walletsetting.WalletSettingActivity;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class WalletListDialog extends Dialog implements View.OnClickListener {

    private String mAddress;
    private WalletListSelectItemListener mWalletListSelectItemListener;

    public WalletListDialog(Context context,String address) {
        super(context, R.style.WalletShowDialog);
        mAddress = address;
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_dialog_fragment_wallet_list,null);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        params.width = (int) (dm.widthPixels * 0.9);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCanceledOnTouchOutside(true);
        initView();
    }


    public void setWalletListSelectItemListener(WalletListSelectItemListener walletListSelectItemListener) {
        this.mWalletListSelectItemListener = walletListSelectItemListener;
    }

    private void initView() {
        findViewById(R.id.iv_cancel).setOnClickListener(this);
        findViewById(R.id.iv_setting).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.rv_wallet);
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
                WalletSettingActivity.startAction(getContext());
                dismiss();
                break;
        }
    }

    public interface WalletListSelectItemListener{
        void selectItem(String id);
    }
}
