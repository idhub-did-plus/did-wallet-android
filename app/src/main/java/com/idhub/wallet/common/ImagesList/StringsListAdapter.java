package com.idhub.wallet.common.ImagesList;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idhub.wallet.R;

import java.util.ArrayList;
import java.util.List;

public class StringsListAdapter extends RecyclerView.Adapter<StringsListAdapter.StringsListAdapterViewHolder> {

    private Context mContext;
    private List<String> mStrings;
    private final LayoutInflater mLayoutInflater;
    private StringClickItem mStringClickItem;
    public StringsListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mStrings = new ArrayList<>();
    }


    public void setStringClickItem(StringClickItem stringClickItem){
        mStringClickItem = stringClickItem;
    }

    public void addAll(List<String> strs) {
        mStrings = strs;
        notifyDataSetChanged();
    }

    public void addItem(String path) {
        mStrings.add(path);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StringsListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.wallet_common_strings_item, viewGroup, false);
        return new StringsListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringsListAdapterViewHolder stringsListAdapterViewHolder, int i) {
        stringsListAdapterViewHolder.textView.setText(mStrings.get(i));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public class StringsListAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public StringsListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_str);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mStringClickItem != null) {
                        mStringClickItem.itemClick(mStrings.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface StringClickItem{
        void itemClick(String str);
    }
}
