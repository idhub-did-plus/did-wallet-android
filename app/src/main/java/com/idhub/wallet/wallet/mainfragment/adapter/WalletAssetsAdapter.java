package com.idhub.wallet.wallet.mainfragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.network.DIDApiUseCase;
import com.idhub.wallet.network.exception.NetworkException;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.mainfragment.model.AssetsModel;
import com.idhub.wallet.wallet.mainfragment.view.ERCItemView;
import com.idhub.wallet.wallet.transaction.TransactionActivity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class WalletAssetsAdapter extends RecyclerView.Adapter<WalletAssetsAdapter.WalletAssetsAdapterViewHolder> {

    private LayoutInflater mInflater;
    private List<AssetsModel> mAssetsModels;
    private Context mContext;

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
        DIDApiUseCase.searchBalance(model.getAddress(), model.getToken()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                NetworkException networkException = new NetworkException(e);
                String message = networkException.getMessage();
                ToastUtils.showShortToast(message);
            }

            @Override
            public void onNext(String s) {
                BigInteger bigInteger = NumericUtil.hexToBigInteger(s);
                Log.e("LYW", "onNext: " + bigInteger);
                String balance = String.valueOf(bigInteger);
                model.setBalance(balance);
                itemView.setBalance(String.valueOf(NumericUtil.bigInteger18ToFloat(bigInteger)));
            }
        });
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
