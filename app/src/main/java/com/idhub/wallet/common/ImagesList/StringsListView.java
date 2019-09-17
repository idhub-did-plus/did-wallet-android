package com.idhub.wallet.common.ImagesList;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

public class StringsListView extends RecyclerView {

    private StringsListAdapter mStringsListAdapter;

    public StringsListView(@NonNull Context context) {
        super(context);
    }

    public StringsListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StringsListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setLayoutManager(new LinearLayoutManager(getContext()));
        mStringsListAdapter = new StringsListAdapter(getContext());
        setAdapter(mStringsListAdapter);
    }

    public void addItem(String path) {
        mStringsListAdapter.addItem(path);
    }
    public void addAll(List<String> path) {
        mStringsListAdapter.addAll(path);
    }
}
