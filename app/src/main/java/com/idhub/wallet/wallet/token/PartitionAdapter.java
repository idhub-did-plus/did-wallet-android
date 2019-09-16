package com.idhub.wallet.wallet.token;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.wallet.assets.AssetsType;
import com.idhub.wallet.wallet.transaction.TransactionActivity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

public class PartitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<PartitionEntity> mData;
    private final int VIEW_TYPE_BOTTOM_VIEW = 1;
    private final int VIEW_TYPE_ITEM_VIEW = 2;
    private Context mContext;
    private AssetsModel mAssetsModel;

    public PartitionAdapter(Context context, AssetsModel assetsModel) {
        mAssetsModel = assetsModel;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void addAll(List<PartitionEntity> assetsModels) {
        mData.clear();
        mData.addAll(assetsModels);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return VIEW_TYPE_BOTTOM_VIEW;
        } else {
            return VIEW_TYPE_ITEM_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if (i == VIEW_TYPE_BOTTOM_VIEW) {
            View view = mInflater.inflate(R.layout.wallet_erc1400_detail_bottom, viewGroup, false);
            viewHolder = new PartitionBottomViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.wallet_partition_item, viewGroup, false);
            viewHolder = new PartitionItemViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PartitionBottomViewHolder) {
            PartitionBottomViewHolder holder = (PartitionBottomViewHolder) viewHolder;
            holder.name.setText(mAssetsModel.getName());
            String symble = mAssetsModel.getSymbol();
            holder.symbol.setText(symble);
            String node = WalletOtherInfoSharpreference.getInstance().getNode();
            String contraceAddress = "";
            if (WalletNodeManager.MAINNET.equals(node)) {
                contraceAddress = mAssetsModel.getMainContractAddress();
            } else if (WalletNodeManager.ROPSTEN.equals(node)) {
                contraceAddress = mAssetsModel.getRopstenContractAddress();
            }
            Web3Api.searchERC1400TotalSupply(contraceAddress, new DisposableSubscriber<BigInteger>() {
                @Override
                public void onNext(BigInteger bigInteger) {
                    String balanceStr = NumericUtil.ethBigIntegerToNumberViewPointAfterFour(bigInteger);
                    holder.totalSupply.setText(balanceStr);
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onComplete() {

                }
            });
        } else if (viewHolder instanceof PartitionItemViewHolder) {
            PartitionEntity partitionEntity = mData.get(i);
            PartitionItemViewHolder holder = (PartitionItemViewHolder) viewHolder;
            holder.name.setText(NumericUtil.prependHexPrefix(NumericUtil.bytesToHex(partitionEntity.name)));
            String balance = partitionEntity.balance;
            if (!TextUtils.isEmpty(balance)) {
                holder.balance.setText(NumericUtil.ethBigIntegerToNumberViewPointAfterFour(new BigInteger(balance)));
            }
            //余额
            String node = WalletOtherInfoSharpreference.getInstance().getNode();
            String contraceAddress = "";
            if (WalletNodeManager.MAINNET.equals(node)) {
                contraceAddress = mAssetsModel.getMainContractAddress();
            } else if (WalletNodeManager.ROPSTEN.equals(node)) {
                contraceAddress = mAssetsModel.getRopstenContractAddress();
            }
            Web3Api.searchERC1400Balance(contraceAddress, partitionEntity.name, WalletManager.getAddress(), new DisposableSubscriber<BigInteger>() {
                @Override
                public void onNext(BigInteger bigInteger) {
                    String balance = String.valueOf(bigInteger);
                    partitionEntity.balance = balance;
                    String balanceStr = NumericUtil.ethBigIntegerToNumberViewPointAfterFour(bigInteger);
                    holder.balance.setText(balanceStr);
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onComplete() {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    private class PartitionItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView balance;

        public PartitionItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            balance = itemView.findViewById(R.id.balance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PartitionEntity entity = mData.get(getLayoutPosition());
            TransactionActivity.srartAction(mContext, mAssetsModel,entity);
        }
    }

    private class PartitionBottomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView totalSupply;
        private TextView name;
        private TextView symbol;

        public PartitionBottomViewHolder(@NonNull View itemView) {
            super(itemView);
            totalSupply = itemView.findViewById(R.id.total_supply);
            name = itemView.findViewById(R.id.name);
            symbol = itemView.findViewById(R.id.symbol);
            itemView.findViewById(R.id.operate).setOnClickListener(this);
            itemView.findViewById(R.id.controller).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.controller:

                    break;
                case R.id.operate:
                    break;
            }
        }
    }


}
