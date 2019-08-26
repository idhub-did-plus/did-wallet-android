package com.idhub.wallet.wallet.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;

import java.util.ArrayList;
import java.util.List;

public class WalletSettingListAdapter extends RecyclerView.Adapter<WalletSettingListAdapter.WalletListAdapterViewHolder> {

    private LayoutInflater mInflater;
    private List<DidHubKeyStore> keyStores;

    public WalletSettingListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        keyStores = new ArrayList<>();
    }
    public void addDatas(List<DidHubKeyStore> keyStores){
        this.keyStores = keyStores;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.wallet_recyclerview_wallet_item, viewGroup, false);
        WalletListAdapterViewHolder adapterViewHolder = new WalletListAdapterViewHolder(view);
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListAdapterViewHolder walletListAdapterViewHolder, int i) {
        WalletItemView walletItemView = (WalletItemView)walletListAdapterViewHolder.itemView;
        walletItemView.setData(keyStores.get(i));
    }

    @Override
    public int getItemCount() {
        return keyStores.size();
    }

    public class WalletListAdapterViewHolder extends RecyclerView.ViewHolder {

        public WalletListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
