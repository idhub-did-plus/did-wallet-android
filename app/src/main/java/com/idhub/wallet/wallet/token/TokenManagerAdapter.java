package com.idhub.wallet.wallet.token;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;

public class TokenManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private final int VIEW_TYPE_HEADER_VIEW = 1;
    private final int VIEW_TYPE_ITEM_VIEW = 2;

    public TokenManagerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER_VIEW;
        } else {
            return VIEW_TYPE_ITEM_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if (i == VIEW_TYPE_HEADER_VIEW) {
            View view = mInflater.inflate(R.layout.wallet_recycler_view_token_header_view, viewGroup, false);
            viewHolder = new TokenManagerAdapterHeaderViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.wallet_recycler_view_token_item, viewGroup, false);
            viewHolder = new TokenManagerAdapterViewItemHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof TokenManagerAdapterHeaderViewHolder) {

        } else if (viewHolder instanceof TokenManagerAdapterViewItemHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    private class TokenManagerAdapterViewItemHolder extends RecyclerView.ViewHolder {

        public TokenManagerAdapterViewItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class TokenManagerAdapterHeaderViewHolder extends RecyclerView.ViewHolder {

        public TokenManagerAdapterHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
