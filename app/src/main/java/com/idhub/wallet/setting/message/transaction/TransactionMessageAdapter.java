package com.idhub.wallet.setting.message.transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.base.greendao.entity.TransactionRecordEntity;

import java.util.ArrayList;
import java.util.List;

public class TransactionMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<TransactionRecordEntity> mData;
    private final int MORE_TRANSACTION_ITEM = 1;
    private final int TRANSACTION_ITEM = 0;
    private Context mContext;
    private TransactionMesageLoadListener transactionMesageLoadListener;

    public TransactionMessageAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void addAllDatas(List<TransactionRecordEntity> datas) {
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<TransactionRecordEntity> datas) {
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void setTransactionMesageLoadListener(TransactionMesageLoadListener transactionMesageLoadListener) {
        this.transactionMesageLoadListener = transactionMesageLoadListener;
    }

    @Override
    public int getItemViewType(int position) {
        int size = mData.size();
        if (size >= 50 && position == size) {
            return MORE_TRANSACTION_ITEM;
        } else {
            return TRANSACTION_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TRANSACTION_ITEM) {
            viewHolder = new TransactionMessageAdapterViewHolder(mInflater.inflate(R.layout.wallet_transaction_message_item, parent, false));
        } else {
            viewHolder = new MoreTransactionMessageAdapterViewHolder(mInflater.inflate(R.layout.wallet_common_more_data_view, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TransactionMessageAdapterViewHolder) {
            ((TransactionMessageItemView) holder.itemView).setData(mData.get(position));
        } else if (holder instanceof MoreTransactionMessageAdapterViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        if (size >= 50) {
            size = size + 1;
        }
        return size;
    }

    public class TransactionMessageAdapterViewHolder extends RecyclerView.ViewHolder {

        public TransactionMessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MoreTransactionMessageAdapterViewHolder extends RecyclerView.ViewHolder {

        public MoreTransactionMessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (transactionMesageLoadListener != null)
                        transactionMesageLoadListener.Load();
                }
            });
        }
    }

    public interface TransactionMesageLoadListener {
        void Load();
    }
}
