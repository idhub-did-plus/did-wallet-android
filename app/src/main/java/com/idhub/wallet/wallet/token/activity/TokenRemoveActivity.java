package com.idhub.wallet.wallet.token.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.wallet.token.adapter.TokenRemoveAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class TokenRemoveActivity extends BaseActivity {

    private TokenRemoveAdapter mTokenRemoveAdapter;
    private Observer mAssetsObserver = (o, arg) -> searchAssetmodelData();

    public static void startAction(Context context){
        Intent intent = new Intent(context, TokenRemoveActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_remove);
        initView();
       searchAssetmodelData();
        WalletAddAssetsObservable.getInstance().addObserver(mAssetsObserver);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_main_asserts_manager));
        RecyclerView recyclerView = findViewById(R.id.token_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTokenRemoveAdapter = new TokenRemoveAdapter();
        recyclerView.setAdapter(mTokenRemoveAdapter);
    }

    private void searchAssetmodelData() {
        //查询数据库资产数，先检查是否有ETH
        new AssetsModelDbManager().queryAll(operation -> {
            if (operation.isCompletedSucessfully()) {
                List<AssetsModel> result = (List<AssetsModel>) operation.getResult();
                String node = WalletNoteSharedPreferences.getInstance().getNode();
                ArrayList<AssetsModel> list = new ArrayList<>();
                //过滤 显示对应ropsten或mainnet上的contractAddress
                if (WalletNodeManager.MAINNET.equals(node)) {
                    for (AssetsModel assetsModel : result) {
                        if (!TextUtils.isEmpty(assetsModel.getMainContractAddress())) {
                            list.add(assetsModel);
                        }
                    }
                }else {
                    for (AssetsModel assetsModel : result) {
                        if (!TextUtils.isEmpty(assetsModel.getRopstenContractAddress())) {
                            list.add(assetsModel);
                        }
                    }
                }
                mTokenRemoveAdapter.addItems(list);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        WalletAddAssetsObservable.getInstance().deleteObserver(mAssetsObserver);
    }
}
