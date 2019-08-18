package com.idhub.wallet.wallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherSharpreference;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class WalletListAdapter extends RecyclerView.Adapter<WalletListAdapter.WalletListAdapterViewHolder> {

    private LayoutInflater mInflater;
    private List<DidHubKeyStore> keyStores;
    private OnWalletItemClickListener onItemClickListener;

    public WalletListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        keyStores = new ArrayList<>();
    }

    public void addDatas(List<DidHubKeyStore> keyStores) {
        this.keyStores = keyStores;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnWalletItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        WalletItemView walletItemView = (WalletItemView) walletListAdapterViewHolder.itemView;
        DidHubKeyStore keyStore = keyStores.get(i);
        String selectedId = WalletOtherSharpreference.getInstance().getSelectedId();
        if (keyStore.getId().equals(selectedId)) {
            walletItemView.setMenuIvVisible(VISIBLE);
            walletItemView.setMenuIv(R.mipmap.wallet_selected);
        } else {
            walletItemView.setMenuIvVisible(INVISIBLE);
        }
        walletItemView.setData(keyStore);
    }

    @Override
    public int getItemCount() {
        return keyStores.size();
    }

    public class WalletListAdapterViewHolder extends RecyclerView.ViewHolder {

        public WalletListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                DidHubKeyStore didHubKeyStore = keyStores.get(adapterPosition);
                boolean b = WalletOtherSharpreference.getInstance().setSelectedId(didHubKeyStore.getId());
                if (b) {
                    onItemClickListener.itemClick();
                }
            });
        }
    }

    public interface OnWalletItemClickListener {
        void itemClick();
    }

}
