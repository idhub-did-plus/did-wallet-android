package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;
import com.idhub.wallet.net.collectiables.model.Collection;

import java.util.Random;

public class CollectionAdapter extends BaseRecyclerAdapter<Collection> {

    public CollectionAdapter(Context context) {
        super(context);
    }

    public int randomHeight() {
        Random random = new Random();
        int i = random.nextInt(82) + 163;
        return i;
    }
    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, Collection item) {
        ImageView imageView = holder.getImageView(R.id.collection_image);
        Glide.with(imageView.getContext()).load(item.large_image_url).placeholder(R.mipmap.cryptokitties_logo).into(imageView);
        holder.setText(R.id.collection_name, item.slug);
        int i = randomHeight();
        ViewCalculateUtil.setViewConstraintLayoutParam(imageView, 164, i);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_collection_item_view;
    }
}
