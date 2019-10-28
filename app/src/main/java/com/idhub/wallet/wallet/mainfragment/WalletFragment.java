package com.idhub.wallet.wallet.mainfragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.base.node.WalletNodeSelectedObservable;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.base.node.AssetsModel;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.wallet.wallet.mainfragment.view.WalletAddressTopView;
import com.idhub.wallet.wallet.mainfragment.view.WalletFragmentBottomView;
import com.idhub.wallet.wallet.token.TokenManagerActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends MainBaseFragment implements View.OnClickListener {


    private WalletFragmentBottomView mWalletBottomView;
    private WalletKeystore mDidHubMnemonicKeyStore;

    private Observer nodeObervable = (o, arg) -> searchAssetmodelData();
    private Observer addAssetsOberver = (o, arg) -> searchAssetmodelData();
    private Observer selectWalletObsever = (o, arg) -> initData();
    private LoadingAndErrorView mLoadingAndErrorView;
    private WalletAddressTopView mWalletAddressTopView;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_wallet, container, false);
        initView(view);
        initData();
        WalletSelectedObservable.getInstance().addObserver(selectWalletObsever);
        WalletAddAssetsObservable.getInstance().addObserver(addAssetsOberver);
        WalletNodeSelectedObservable.getInstance().addObserver(nodeObervable);
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    private void searchAssetmodelData() {
        //查询数据库资产数，先检查是否有ETH
        new AssetsModelDbManager().queryAll(operation -> {
            if (operation.isCompletedSucessfully()) {
                List<AssetsModel> result = (List<AssetsModel>) operation.getResult();
                String node = WalletNoteSharedPreferences.getInstance().getNode();
                ArrayList<AssetsModel> list = new ArrayList<>();
                //过滤 显示对应ropsten或mainnet上的contractAddress
                if (WalletNodeManager.ROPSTEN.equals(node)) {
                    for (AssetsModel assetsModel : result) {
                        if (assetsModel.getType().equals(AssetsDefaultType.ETH_NAME)) {
                            list.add(assetsModel);
                        }
                        if (!TextUtils.isEmpty(assetsModel.getRopstenContractAddress())) {
                            list.add(assetsModel);
                        }
                    }
                } else if (WalletNodeManager.MAINNET.equals(node)) {
                    for (AssetsModel assetsModel : result) {
                        if (assetsModel.getType().equals(AssetsDefaultType.ETH_NAME)) {
                            list.add(assetsModel);
                        }
                        if (!TextUtils.isEmpty(assetsModel.getMainContractAddress())) {
                            list.add(assetsModel);
                        }
                    }
                }
                mWalletBottomView.setData(list);
            }
        });
    }

    private void initData() {
        mDidHubMnemonicKeyStore = WalletManager.getCurrentKeyStore();
        mWalletAddressTopView.setData(mDidHubMnemonicKeyStore);
        searchAssetmodelData();
    }

    private void initView(View view) {
        view.findViewById(R.id.add_token).setOnClickListener(this);
        mWalletAddressTopView = view.findViewById(R.id.wallet_card);
        mWalletBottomView = view.findViewById(R.id.bottom_view);
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_token:
                //go to add token
                TokenManagerActivity.startAction(getContext());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WalletSelectedObservable.getInstance().deleteObserver(selectWalletObsever);
        WalletNodeSelectedObservable.getInstance().deleteObserver(nodeObervable);
        WalletAddAssetsObservable.getInstance().deleteObserver(addAssetsOberver);
    }
}
