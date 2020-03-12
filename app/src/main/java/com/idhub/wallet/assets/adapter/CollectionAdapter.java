package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.model.CollectionTokenBean;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

import java.util.List;
import java.util.Random;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class CollectionAdapter extends BaseRecyclerAdapter<CollectionTokenBean> {

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
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, CollectionTokenBean item) {
        ImageView imageView = holder.getImageView(R.id.collection_image);
        Glide.with(imageView.getContext()).load(item.image_url).placeholder(R.mipmap.collection_default).into(imageView);
        holder.setText(R.id.collection_name, item.name);
//        int i = randomHeight();
//        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(AutoSizeUtils.dp2px(context, 164),AutoSizeUtils.dp2px(context, i) );
//        imageView.setLayoutParams(layoutParams);
        TextView numView = holder.getTextView(R.id.collection_num);
        List<AssetsCollectionItem> collectionItems = item.assetsCollectionItem;
        int size = collectionItems.size();
        if (size > 1) {
            numView.setText(size +" " +"items");
        } else {
            numView.setText(size +" " +"item");
        }

    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_collection_grid_item_view;
    }
}
