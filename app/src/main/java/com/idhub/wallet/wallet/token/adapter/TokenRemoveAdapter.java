package com.idhub.wallet.wallet.token.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.R;
import com.idhub.wallet.wallet.token.view.TokenRemoveItemView;

import java.util.ArrayList;
import java.util.List;

public class TokenRemoveAdapter extends RecyclerView.Adapter<TokenRemoveAdapter.TokenRemoveViewHolder>  {

    private List<AssetsModel> assetsModels;

    public TokenRemoveAdapter() {
        this.assetsModels = new ArrayList<>();
    }

    public void addItems(List<AssetsModel> models){
        assetsModels.clear();
        assetsModels.addAll(models);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TokenRemoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TokenRemoveViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_recycler_view_token_remove_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TokenRemoveViewHolder holder, int position) {
        ((TokenRemoveItemView) holder.itemView).setData(assetsModels.get(position));
    }

    @Override
    public int getItemCount() {
        return assetsModels.size();
    }

    public class TokenRemoveViewHolder extends RecyclerView.ViewHolder {

        public TokenRemoveViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
