package com.idhub.wallet.assets.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.base.node.WalletNodeSelectedObservable;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.STListActivity;
import com.idhub.wallet.assets.view.STMainView;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.wallet.wallet.token.activity.TokenManagerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class STAssetsFragment extends Fragment implements View.OnClickListener {


    private View promptView;
    private STMainView stMainView;
    private View stMoreView;

    public STAssetsFragment() {
        // Required empty public constructor
    }

    private Observer obsever = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            initData();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_stassets, container, false);
        initView(view);
        initData();
        WalletAddAssetsObservable.getInstance().addObserver(obsever);
        WalletNodeSelectedObservable.getInstance().addObserver(obsever);
        WalletSelectedObservable.getInstance().addObserver(obsever);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.assets_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenManagerActivity.startAction(getContext());
            }
        });
        promptView = view.findViewById(R.id.assets_prompt);
        stMoreView = view.findViewById(R.id.more);
        stMainView = view.findViewById(R.id.st_content);
    }

    private void initData() {
        //查询数据库资产数，先检查是否有ETH
        List<AssetsModel> assetsModels = new AssetsModelDbManager().querySTAssets();
        if (assetsModels != null && assetsModels.size() > 0) {
            for (AssetsModel assetsModel : assetsModels) {
                assetsModel.getContractAddresses();
            }
            ArrayList<AssetsModel> list = new ArrayList<>();
            for (AssetsModel assetsModel : assetsModels) {
                if (!TextUtils.isEmpty(assetsModel.getCurrentContractAddress())) {
                    list.add(assetsModel);
                }
            }
            int size = list.size();
            if (size > 0) {
                promptView.setVisibility(View.GONE);
                stMainView.setVisibility(View.VISIBLE);
                stMoreView.setVisibility(View.VISIBLE);
                stMoreView.setOnClickListener(this);
//                if (size > 7) {
//                } else {
//                    stMoreView.setVisibility(View.GONE);
//                }
                stMainView.setData(list);
            } else {
                stMainView.setVisibility(View.GONE);
                stMoreView.setVisibility(View.GONE);
                promptView.setVisibility(View.VISIBLE);
            }
        } else {
            stMainView.setVisibility(View.GONE);
            stMoreView.setVisibility(View.GONE);
            promptView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == stMoreView) {
            STListActivity.startAction(getContext());
        }
    }
}
