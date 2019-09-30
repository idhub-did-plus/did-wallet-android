package com.idhub.wallet.wallet.token;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.address.ETHAddressValidator;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.assets.AssetsType;

import org.greenrobot.greendao.async.AsyncOperation;
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
        List<AssetsModel> assetsList = AssetsType.getAssetsList();
        String node = WalletOtherInfoSharpreference.getInstance().getNode();
        ArrayList<AssetsModel> list = new ArrayList<>();
        //过滤 显示对应ropsten或mainnet上的contractAddress
        if (WalletNodeManager.ROPSTEN.equals(node)){
            for (AssetsModel assetsModel : assetsList) {
                if (!TextUtils.isEmpty(assetsModel.getRopstenContractAddress())) {
                    list.add(assetsModel);
                }
            }
        } else if (WalletNodeManager.MAINNET.equals(node)) {
            for (AssetsModel assetsModel : assetsList) {
                if (!TextUtils.isEmpty(assetsModel.getMainContractAddress())) {
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
            }

            @Override
            public void onError(Throwable e) {
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
            }

            @Override
            public void onError(Throwable e) {
                Web3Api.searchERC20ContractAddressAssets(contractAddress,erc20observer);
            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        };
        AssetsModelDbManager assetsModelDbManager = new AssetsModelDbManager();
        String node = WalletOtherInfoSharpreference.getInstance().getNode();
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
        if (WalletNodeManager.ROPSTEN.equals(node)) {
            assetsModelDbManager.queryByRopstenContractAddressKey(contractAddress, listener);
        } else if (WalletNodeManager.MAINNET.equals(node)) {
            assetsModelDbManager.queryByMainContractAddressKey(contractAddress,listener);
        }


    }
}
