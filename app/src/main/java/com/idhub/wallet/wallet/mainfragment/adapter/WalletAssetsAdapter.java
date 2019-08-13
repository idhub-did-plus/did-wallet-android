package com.idhub.wallet.wallet.mainfragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.mainfragment.model.AssetsModel;
import com.idhub.wallet.wallet.mainfragment.view.ERCItemView;

import java.util.ArrayList;
import java.util.List;

public class WalletAssetsAdapter extends RecyclerView.Adapter<WalletAssetsAdapter.WalletAssetsAdapterViewHolder> {

    private LayoutInflater mInflater;
    private List<AssetsModel> mAssetsModels;

    public WalletAssetsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mAssetsModels = new ArrayList<>();
    }

    public void addDatas(List<AssetsModel> assetsModels) {
        mAssetsModels = assetsModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletAssetsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.wallet_recyclerview_assets_erc20_item, viewGroup, false);
        return new WalletAssetsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAssetsAdapterViewHolder walletAssetsAdapterViewHolder, int i) {
        ERCItemView itemView = (ERCItemView) walletAssetsAdapterViewHolder.itemView;
        itemView.setData(mAssetsModels.get(i));
    }

    @Override
    public int getItemCount() {
        return mAssetsModels.size();
    }

    public class WalletAssetsAdapterViewHolder extends RecyclerView.ViewHolder {
        public WalletAssetsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
