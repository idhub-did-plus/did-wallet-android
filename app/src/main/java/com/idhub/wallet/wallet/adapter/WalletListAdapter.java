package com.idhub.wallet.wallet.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class WalletListAdapter extends RecyclerView.Adapter<WalletListAdapter.WalletListAdapterViewHolder> {

    private LayoutInflater mInflater;
    private List<DidHubKeyStore> keyStores;
    private OnWalletItemClickListener onItemClickListener;
    private String mSelectAddress;

    public WalletListAdapter(Context context, String address) {
        mSelectAddress = address;
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
        walletItemView.setClicked(false);
        DidHubKeyStore keyStore = keyStores.get(i);
        if (NumericUtil.prependHexPrefix(keyStore.getAddress()).equals(NumericUtil.prependHexPrefix(mSelectAddress))) {
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
                onItemClickListener.itemClick(didHubKeyStore.getId());
            });
        }
    }

    public interface OnWalletItemClickListener {
        void itemClick(String id);
    }

}
