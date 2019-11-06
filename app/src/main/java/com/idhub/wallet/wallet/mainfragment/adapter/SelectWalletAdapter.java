package com.idhub.wallet.wallet.mainfragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.wallet.mainfragment.view.SelectWalletItemView;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SelectWalletAdapter extends RecyclerView.Adapter<SelectWalletAdapter.WalletListAdapterViewHolder> {

    private List<WalletKeystore> keyStores;
    private OnWalletItemClickListener onItemClickListener;
    private Context mContext;

    public SelectWalletAdapter(Context context) {
        mContext = context;

        keyStores = new ArrayList<>();
    }

    public void addDatas(List<WalletKeystore> keyStores) {
        this.keyStores = keyStores;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnWalletItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WalletListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wallet_select_item_view, viewGroup, false);
        WalletListAdapterViewHolder adapterViewHolder = new WalletListAdapterViewHolder(view);
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListAdapterViewHolder walletListAdapterViewHolder, int i) {
        SelectWalletItemView selectWalletItemView = (SelectWalletItemView) walletListAdapterViewHolder.itemView;
        WalletKeystore keyStore = keyStores.get(i);
        boolean selected = NumericUtil.prependHexPrefix(keyStore.getAddress()).equals(NumericUtil.prependHexPrefix(WalletManager.getAddress()));
        selectWalletItemView.setItemIsSelect(selected);
        selectWalletItemView.setData(keyStore);
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
                WalletKeystore didHubMnemonicKeyStore = keyStores.get(adapterPosition);
                onItemClickListener.itemClick(didHubMnemonicKeyStore.getId());
            });
        }
    }

    public interface OnWalletItemClickListener {
        void itemClick(String id);
    }

}
