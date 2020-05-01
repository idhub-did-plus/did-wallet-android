package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.wallet.token.activity.Erc1400DetailActivity;
import com.idhub.wallet.wallet.token.view.TokenManagerHeaderView;
import com.idhub.wallet.wallet.token.view.TokenManagerItemView;

import java.util.ArrayList;
import java.util.List;

public class StListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<AssetsModel> mData;
    private Context mContext;

    public StListItemAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void addAll(List<AssetsModel> assetsModels) {
        mData.clear();
        mData.addAll(assetsModels);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = mInflater.inflate(R.layout.wallet_recycler_view_token_item, viewGroup, false);
        viewHolder = new TokenManagerAdapterViewItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof TokenManagerAdapterViewItemHolder) {
            TokenManagerItemView itemView = (TokenManagerItemView) viewHolder.itemView;
            itemView.setIconVisible(View.INVISIBLE);
            AssetsModel assetsModel = mData.get(i);
            itemView.setData(assetsModel);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class TokenManagerAdapterViewItemHolder extends RecyclerView.ViewHolder {

        public TokenManagerAdapterViewItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AssetsModel model = mData.get(getLayoutPosition());
                    Erc1400DetailActivity.startAction(mContext,model);
                }
            });
        }
    }
}
