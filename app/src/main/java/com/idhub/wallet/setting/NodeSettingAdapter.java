package com.idhub.wallet.setting;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.setting.message.idhub.IdHubMessageItemView;

import java.util.ArrayList;
import java.util.List;

public class NodeSettingAdapter extends RecyclerView.Adapter<NodeSettingAdapter.NodeSettingAdapterViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mData;
    private Context mContext;
    private String mSelectPath;


    public NodeSettingAdapter(Context context, String selectPath) {
        mSelectPath = selectPath;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public String getNode(){
        return mSelectPath;
    }
    public void addDatas(List<String> datas) {
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NodeSettingAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NodeSettingAdapterViewHolder viewHolder = new NodeSettingAdapterViewHolder(mInflater.inflate(R.layout.wallet_node_setting_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NodeSettingAdapterViewHolder holder, int position) {
        String path = mData.get(position);
        if (path.equals(mSelectPath)) {
            holder.mNodeSelectIamgeView.setVisibility(View.VISIBLE);
        } else {
            holder.mNodeSelectIamgeView.setVisibility(View.INVISIBLE);
        }
        holder.mPathView.setText(path);
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        return size;
    }

    public class NodeSettingAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mNodeSelectIamgeView;
        private TextView mPathView;

        public NodeSettingAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mPathView = itemView.findViewById(R.id.node_path);
            mNodeSelectIamgeView = itemView.findViewById(R.id.node_select_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                mSelectPath = mPathView.getText().toString();
                notifyDataSetChanged();
            }
        }
    }


}
