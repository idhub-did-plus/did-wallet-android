package com.idhub.wallet.common.ImagesList;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.idhub.wallet.R;

import java.util.ArrayList;
import java.util.List;

public class ImagesListAdapter extends RecyclerView.Adapter<ImagesListAdapter.ImagesListAdapterViewHolder> {

    private Context mContext;
    private List<String> mImages;
    private final LayoutInflater mLayoutInflater;

    public ImagesListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mImages = new ArrayList<>();
    }

    public void addAll(List<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    public void addItem(String path) {
        mImages.add(path);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImagesListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.wallet_common_images_item, viewGroup, false);
        return new ImagesListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesListAdapterViewHolder imagesListAdapterViewHolder, int i) {
        Glide.with(mContext).load(mImages.get(i)).into(imagesListAdapterViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ImagesListAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImagesListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }
}
