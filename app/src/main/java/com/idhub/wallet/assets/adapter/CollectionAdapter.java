package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

import java.util.Random;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class CollectionAdapter extends BaseRecyclerAdapter<AssetsCollectionItem> {

    private Context context;

    public CollectionAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public int randomHeight() {
        Random random = new Random();
        int i = random.nextInt(82) + 163;
        return i;
    }
    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, AssetsCollectionItem item) {
        ImageView imageView = holder.getImageView(R.id.collection_image);
        AssetsCollectionItem.CollectionBean collectionBean = item.collection;
        Glide.with(imageView.getContext()).load(collectionBean.large_image_url).placeholder(R.mipmap.cryptokitties_logo).into(imageView);
        holder.setText(R.id.collection_name, collectionBean.slug);
        int i = randomHeight();
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(AutoSizeUtils.dp2px(context, 164),AutoSizeUtils.dp2px(context, i) );
        imageView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_collection_grid_item_view;
    }
}
