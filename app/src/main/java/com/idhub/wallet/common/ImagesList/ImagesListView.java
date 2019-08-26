package com.idhub.wallet.common.ImagesList;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

public class ImagesListView extends RecyclerView {

    private ImagesListAdapter mImagesListAdapter;

    public ImagesListView(@NonNull Context context) {
        super(context);
    }

    public ImagesListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImagesListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        mImagesListAdapter = new ImagesListAdapter(getContext());
        setAdapter(mImagesListAdapter);
    }

    public void addItem(String path) {
        mImagesListAdapter.addItem(path);
    }
}
