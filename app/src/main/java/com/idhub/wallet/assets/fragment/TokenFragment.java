package com.idhub.wallet.assets.fragment;


import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.adapter.TokenListAdapter;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.wallet.wallet.transaction.TransactionActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class TokenFragment extends Fragment {


    private TokenListAdapter adapter;

    public TokenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_token, container, false);
        initView(view);
        initData();
        WalletAddAssetsObservable.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                initData();
            }
        });
        return view;
    }


    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TokenListAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        //查询数据库资产数，先检查是否有ETH
        new AssetsModelDbManager().queryAll(operation -> {
            if (operation.isCompletedSucessfully()) {
                List<AssetsModel> result = (List<AssetsModel>) operation.getResult();
                for (AssetsModel assetsModel : result) {
                    assetsModel.getContractAddresses();
                }
                ArrayList<AssetsModel> list = new ArrayList<>();
                //过滤 显示对应ropsten或mainnet上的contractAddress
                for (AssetsModel assetsModel : result) {
                    if (assetsModel.getType().equals(TransactionTokenType.ETH_NAME)) {
                        list.add(assetsModel);
                    }
                    if (!TextUtils.isEmpty(assetsModel.getCurrentContractAddress()) && assetsModel.getType().equals(TransactionTokenType.ERC20)) {
                        list.add(assetsModel);
                    }
                }
                adapter.addAll(list);
            }
        });
    }
}
