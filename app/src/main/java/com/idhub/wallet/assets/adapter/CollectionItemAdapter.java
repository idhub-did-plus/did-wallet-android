package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

public class CollectionItemAdapter extends BaseRecyclerAdapter<AssetsCollectionItem> {
    public CollectionItemAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, AssetsCollectionItem item) {
        ImageView imageView = holder.getImageView(R.id.image);
        Glide.with(imageView).load(item.image_original_url).into(imageView);
        holder.setText(R.id.name, item.name);
        holder.setText(R.id.description, item.description);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_collection_list_item_view;
    }
}
