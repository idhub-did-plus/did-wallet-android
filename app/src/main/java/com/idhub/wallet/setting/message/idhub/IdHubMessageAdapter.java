package com.idhub.wallet.setting.message.idhub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;
import com.idhub.wallet.setting.message.transaction.TransactionMessageItemView;

import java.util.ArrayList;
import java.util.List;

public class IdHubMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<IdHubMessageEntity> mData;

    private Context mContext;


    public IdHubMessageAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void addDatas(List<IdHubMessageEntity> datas) {
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new TransactionMessageAdapterViewHolder(mInflater.inflate(R.layout.wallet_idhub_message_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((IdHubMessageItemView) holder.itemView).setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        return size;
    }

    public class TransactionMessageAdapterViewHolder extends RecyclerView.ViewHolder {

        public TransactionMessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
