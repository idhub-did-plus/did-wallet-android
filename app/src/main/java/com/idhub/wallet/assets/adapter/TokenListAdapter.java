package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.view.TokenItemView;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.net.Web3jSubscriber;
import com.idhub.wallet.wallet.token.TokenTypeManager;
import com.idhub.wallet.wallet.transaction.TransactionActivity;

import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;

public class TokenListAdapter extends BaseRecyclerAdapter<AssetsModel> {

    public TokenListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, AssetsModel model) {
        TokenItemView itemView = (TokenItemView) holder.itemView;
        String symble = model.getSymbol();
        Integer integer = TokenTypeManager.assetsMipmap.get(symble);
        if (integer != null) {
            itemView.setAssetsImage(integer);
        } else {
            itemView.setAssetsImage(R.mipmap.wallet_eth_icon);
        }
        itemView.setName(symble);
        String contractAddress = model.getCurrentContractAddress();
        String address = WalletManager.getAddress();
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

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                String balance1 = model.getBalance();

                if (!TextUtils.isEmpty(balance1)) {
                    TransactionActivity.srartAction(itemView.getContext(), model);
                }
            }
        });
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_token_item;
    }
}
