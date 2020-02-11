package com.idhub.wallet.wallet.mainfragment.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.wallet.net.Web3Api;
import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.net.Web3jSubscriber;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.wallet.wallet.token.TokenTypeManager;
import com.idhub.wallet.wallet.mainfragment.view.AssetsErc1400ItemView;
import com.idhub.wallet.wallet.mainfragment.view.AssetsItemView;
import com.idhub.wallet.wallet.token.activity.Erc1400DetailActivity;
import com.idhub.wallet.wallet.transaction.TransactionActivity;

import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class WalletAssetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<AssetsModel> mAssetsModels;
    private Context mContext;
    private static final int ERC20_ITEM = 0;
    private static final int ERC1400_ITEM = 1;
    String address;

    public WalletAssetsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mAssetsModels = new ArrayList<>();
    }

    public void addDatas(List<AssetsModel> assetsModels) {
        mAssetsModels.clear();
        mAssetsModels.addAll(assetsModels);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (TransactionTokenType.ERC1400.equals(mAssetsModels.get(position).getType())) {
            return ERC1400_ITEM;
        } else {
            return ERC20_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        if (i == ERC1400_ITEM) {
            View view = mInflater.inflate(R.layout.wallet_recyclerview_assets_st_item, viewGroup, false);
            viewHolder = new WalletAssetsErc1400AdapterViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.wallet_recyclerview_assets_item, viewGroup, false);
            viewHolder = new WalletAssetsAdapterViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AssetsModel model = mAssetsModels.get(i);
        String symble = model.getSymbol();
        Integer integer = TokenTypeManager.assetsMipmap.get(symble);
        if (viewHolder instanceof WalletAssetsAdapterViewHolder) {
            AssetsItemView itemView = (AssetsItemView) ((WalletAssetsAdapterViewHolder) viewHolder).itemView;
            if (integer != null) {
                itemView.setAssetsImage(integer);
            } else {
                itemView.setAssetsImage(R.mipmap.wallet_eth_icon);
            }
            itemView.setName(symble);
            String contractAddress = model.getCurrentContractAddress();
            address = WalletManager.getAddress();
            String balance = model.getBalance();
            if (TextUtils.isEmpty(balance)) {
                itemView.setBalance("-");
            } else {
                itemView.setBalance(NumericUtil.ethBigIntegerToNumberViewPointAfterFour(new BigInteger(balance), String.valueOf(Math.pow(10, Double.parseDouble(model.getDecimals())))));
            }
            if (TransactionTokenType.ERC20.equals(model.getType())) {
                Web3Api.searchBalance(address, contractAddress, new Web3jSubscriber<BigInteger>() {
                    @Override
                    public void onNext(BigInteger bigInteger) {
                        String balance = String.valueOf(bigInteger);
                        model.setBalance(balance);
                        String balanceStr = NumericUtil.ethBigIntegerToNumberViewPointAfterFour(bigInteger, String.valueOf(Math.pow(10, Double.parseDouble(model.getDecimals()))));
                        itemView.setBalance(balanceStr);
                    }
                });
            } else if (TransactionTokenType.ETH_NAME.equals(model.getType())) {
                Web3Api.searchBalance(address, new Web3jSubscriber<EthGetBalance>() {
                    @Override
                    public void onNext(EthGetBalance o) {
                        super.onNext(o);
                        BigInteger balance1 = o.getBalance();
                        String balance = String.valueOf(balance1);
                        model.setBalance(balance);
                        itemView.setBalance(NumericUtil.ethBigIntegerToNumberViewPointAfterFour(balance1, String.valueOf(Math.pow(10, Double.parseDouble(model.getDecimals())))));
                    }
                });
            }
        } else if (viewHolder instanceof WalletAssetsErc1400AdapterViewHolder) {
            AssetsErc1400ItemView itemView = (AssetsErc1400ItemView) ((WalletAssetsErc1400AdapterViewHolder) viewHolder).itemView;
            if (integer != null)
                itemView.setAssetsImage(integer);
            itemView.setName(symble);
        }
    }

    @Override
    public int getItemCount() {
        return mAssetsModels.size();
    }

    public class WalletAssetsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public WalletAssetsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AssetsModel model = mAssetsModels.get(getLayoutPosition());
            if (!TextUtils.isEmpty(model.getBalance())) {
                TransactionActivity.srartAction(mContext, model);
            }
        }
    }

    public class WalletAssetsErc1400AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public WalletAssetsErc1400AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AssetsModel model = mAssetsModels.get(getLayoutPosition());
            Erc1400DetailActivity.startAction(mContext, model);
        }
    }
}
