package com.idhub.wallet.wallet.token.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.didhub.address.ETHAddressValidator;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.base.node.AssetsModel;
import com.idhub.wallet.net.Web3Api;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.token.adapter.TokenManagerAdapter;
import com.idhub.wallet.wallet.token.TokenTypeManager;

import org.greenrobot.greendao.async.AsyncOperationListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class TokenManagerActivity extends BaseActivity implements TokenManagerAdapter.AssetsManagerListener {

    private LoadingAndErrorView mLoadingAndErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_token_manager);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, TokenManagerActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.token_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TokenManagerAdapter adapter = new TokenManagerAdapter(this);
        recyclerView.setAdapter(adapter);
        List<AssetsModel> assetsList = TokenTypeManager.getAssetsList();
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        ArrayList<AssetsModel> list = new ArrayList<>();
        //过滤 显示对应ropsten或mainnet上的contractAddress
       if (WalletNodeManager.MAINNET.equals(node)) {
            for (AssetsModel assetsModel : assetsList) {
                if (!TextUtils.isEmpty(assetsModel.getMainContractAddress())) {
                    list.add(assetsModel);
                }
            }
        }else {
            for (AssetsModel assetsModel : assetsList) {
                if (!TextUtils.isEmpty(assetsModel.getRopstenContractAddress())) {
                    list.add(assetsModel);
                }
            }
        }
        adapter.addAll(list);
        adapter.setAssetsListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }

    @Override
    public void addAssets(String contractAddress) {
        boolean validAddress = ETHAddressValidator.isValidAddress(contractAddress);
        if (!validAddress) {
            ToastUtils.showShortToast(getString(R.string.wallet_assets_address_fail));
            return;
        }
        //添加资产 1先查询数据库是否有已存在的token 2.判断是否是erc1400合约地址  3.判断是否是erc20合约

        DisposableObserver<AssetsModel> erc20observer = new DisposableObserver<AssetsModel>() {
            @Override
            public void onNext(AssetsModel assetsModel) {
                ToastUtils.showShortToast(getString(R.string.wallet_assets_add_success));
                WalletAddAssetsObservable.getInstance().update();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                String message = e.getMessage();
                if (message != null)
                    ToastUtils.showShortToast(getString(R.string.wallet_assets_add_fail) + " " + message);
                else
                    ToastUtils.showShortToast(getString(R.string.wallet_assets_add_fail));
                mLoadingAndErrorView.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        };

        DisposableObserver<AssetsModel> erc1400observer = new DisposableObserver<AssetsModel>() {
            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(AssetsModel assetsModel) {
                ToastUtils.showShortToast(getString(R.string.wallet_assets_add_success));
                WalletAddAssetsObservable.getInstance().update();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                String message = e.getMessage();
                if (message.contains("Empty value (0x) returned from contract")) {
                    Web3Api.searchERC20ContractAddressAssets(contractAddress,erc20observer);
                }else {
                    Toast.makeText(TokenManagerActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                Log.e("LYW1", "onError: " + message);
            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        };
        AssetsModelDbManager assetsModelDbManager = new AssetsModelDbManager();
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        AsyncOperationListener listener = operation -> {
            List<AssetsModel> o = (List<AssetsModel>) operation.getResult();
            if (o != null && o.size() > 0) {
                //已存在
                ToastUtils.showShortToast(getString(R.string.wallet_assets_has));
            } else {
               Web3Api.searchERC1400ContractAddress(contractAddress,erc1400observer);
            }
        };

        //过滤 显示对应ropsten或mainnet上的contractAddress
        if (WalletNodeManager.MAINNET.equals(node)) {
            assetsModelDbManager.queryByMainContractAddressKey(contractAddress,listener);
        }else {
            assetsModelDbManager.queryByRopstenContractAddressKey(contractAddress, listener);
        }


    }
}
