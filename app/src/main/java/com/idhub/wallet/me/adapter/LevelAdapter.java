package com.idhub.wallet.me.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.me.information.Level1Activity;
import com.idhub.wallet.me.information.Level2Activity;
import com.idhub.wallet.me.information.Level3Activity;
import com.idhub.wallet.me.information.Level4Activity;
import com.idhub.wallet.me.information.Level5Activity;
import com.idhub.wallet.me.model.MeLevelEntity;

import java.util.ArrayList;
import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelAdapterViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<MeLevelEntity> mDatas;

    public LevelAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
    }

    public void addAll(List<MeLevelEntity> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LevelAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.wallet_fragment_me_level_item_view, viewGroup, false);
        return new LevelAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapterViewHolder levelAdapterViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class LevelAdapterViewHolder extends RecyclerView.ViewHolder {

        public LevelAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                MeLevelEntity meLevelEntity = mDatas.get(adapterPosition);
                String level = meLevelEntity.level;
                switch (level) {
                    case "1":
                        Level1Activity.startAction(mContext);
                        break;
                    case "2":
                        Level2Activity.startAction(mContext);
                        break;
                    case "3":
                        Level3Activity.startAction(mContext);
                        break;
                    case "4":
                        Level4Activity.startAction(mContext);
                        break;
                    case "5":
                        Level5Activity.startAction(mContext);
                        break;
                }
            });
        }
    }
}
