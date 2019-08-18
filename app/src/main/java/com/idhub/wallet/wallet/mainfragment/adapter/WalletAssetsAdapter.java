package com.idhub.wallet.wallet.mainfragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.network.Web3Api;
import com.idhub.wallet.network.Web3jSubscriber;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.wallet.mainfragment.view.ERCItemView;
import com.idhub.wallet.wallet.transaction.TransactionActivity;

import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class WalletAssetsAdapter extends RecyclerView.Adapter<WalletAssetsAdapter.WalletAssetsAdapterViewHolder> {

    private LayoutInflater mInflater;
    private List<AssetsModel> mAssetsModels;
    private Context mContext;
    String address;

    public WalletAssetsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mAssetsModels = new ArrayList<>();
    }

    public void addDatas(List<AssetsModel> assetsModels) {
        mAssetsModels = assetsModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletAssetsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.wallet_recyclerview_assets_erc20_item, viewGroup, false);
        return new WalletAssetsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAssetsAdapterViewHolder walletAssetsAdapterViewHolder, int i) {
        ERCItemView itemView = (ERCItemView) walletAssetsAdapterViewHolder.itemView;
        AssetsModel model = mAssetsModels.get(i);
        itemView.setName(model.getName());
        String token = model.getToken();
        address = WalletManager.getAddress();
        if (!TextUtils.isEmpty(token)) {
            Web3Api.searchBalance(address, token, new Web3jSubscriber<BigInteger>() {
                @Override
                public void onNext(BigInteger bigInteger) {
                    String balance = String.valueOf(bigInteger);
                    model.setBalance(balance);
                    String balanceStr = NumericUtil.ethBigIntegerToNumberViewPointAfterFour(bigInteger);
                    itemView.setBalance(balanceStr);
                }
            });
        } else {
            Web3Api.searchBalance(address, new Web3jSubscriber<EthGetBalance>() {
                @Override
                public void onNext(EthGetBalance o) {
                    super.onNext(o);
                    BigInteger balance1 = o.getBalance();
                    String balance = String.valueOf(balance1);
                    model.setBalance(balance);
                    itemView.setBalance(NumericUtil.ethBigIntegerToNumberViewPointAfterFour(balance1));
                }
            });
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
            TransactionActivity.srartAction(mContext, model);
        }
    }
}
