package com.idhub.wallet.wallet.token;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.base.node.AssetsModel;

import java.util.ArrayList;
import java.util.List;

public class TokenManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<AssetsModel> mData;
    private final int VIEW_TYPE_HEADER_VIEW = 1;
    private final int VIEW_TYPE_ITEM_VIEW = 2;
    private Context mContext;
    private AssetsManagerListener mAssetsManagerListener;
    public TokenManagerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void addAll(List<AssetsModel> assetsModels) {
        mData.clear();
        mData.addAll(assetsModels);
        notifyDataSetChanged();
    }

    public void setAssetsListener(AssetsManagerListener assetsListener) {
        mAssetsManagerListener = assetsListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER_VIEW;
        } else {
            return VIEW_TYPE_ITEM_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if (i == VIEW_TYPE_HEADER_VIEW) {
            View view = mInflater.inflate(R.layout.wallet_recycler_view_token_header_view, viewGroup, false);
            viewHolder = new TokenManagerAdapterHeaderViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.wallet_recycler_view_token_item, viewGroup, false);
            viewHolder = new TokenManagerAdapterViewItemHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof TokenManagerAdapterHeaderViewHolder) {

        } else if (viewHolder instanceof TokenManagerAdapterViewItemHolder) {
            TokenManagerItemView itemView = (TokenManagerItemView) viewHolder.itemView;
            AssetsModel assetsModel = mData.get(i - 1);
            itemView.setData(assetsModel);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    private class TokenManagerAdapterViewItemHolder extends RecyclerView.ViewHolder {

        public TokenManagerAdapterViewItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class TokenManagerAdapterHeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

        public TokenManagerAdapterHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            ((TokenManagerHeaderView) itemView).setAddClickListener(this);
        }

        @Override
        public void onClick(View v) {
            InputDialogFragment instance = InputDialogFragment.getInstance("contractAddress", mContext.getString(R.string.wallet_input_contract_address), InputType.TYPE_CLASS_TEXT);
            FragmentManager supportFragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
            instance.show(supportFragmentManager, "input_dialog_fragment");
            instance.setInputDialogFragmentListener(this);
        }

        @Override
        public void inputConfirm(String data, String source) {
            //data查询，添加数据库
            if (mAssetsManagerListener != null) {
                mAssetsManagerListener.addAssets(data);
            }
        }
    }

    public interface AssetsManagerListener{
        void addAssets(String contractAddress);
    }
}
