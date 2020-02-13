package com.idhub.wallet.wallet.mainfragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.base.node.WalletNodeSelectedObservable;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.common.zxinglib.QrCodeActivity;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.wallet.info.WalletInfoActivity;
import com.idhub.wallet.wallet.mainfragment.adapter.SelectWalletAdapter;
import com.idhub.wallet.wallet.mainfragment.view.WalletAddressTopView;
import com.idhub.wallet.wallet.mainfragment.view.WalletFragmentBottomView;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;
import com.idhub.wallet.wallet.token.activity.TokenManagerActivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
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
    private Observer selectWalletObsever = (o, arg) -> {
        initData();
        initDrawerlayoutData();
    };
    private LoadingAndErrorView mLoadingAndErrorView;
    private WalletAddressTopView mWalletAddressTopView;
    private DrawerLayout drawerLayout;
    private View leftDrawerView;
    private SelectWalletAdapter selectWalletAdapter;

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
                for (AssetsModel assetsModel : result) {
                    assetsModel.getContractAddresses();
                }
                ArrayList<AssetsModel> list = new ArrayList<>();
                //过滤 显示对应ropsten或mainnet上的contractAddress
                for (AssetsModel assetsModel : result) {
                    if (assetsModel.getType().equals(TransactionTokenType.ETH_NAME)) {
                        list.add(assetsModel);
                    }
                    if (!TextUtils.isEmpty(assetsModel.getCurrentContractAddress())) {
                        list.add(assetsModel);
                    }
                }
                mWalletBottomView.setData(list);
            }
        });
    }

    private void initData() {
        mDidHubMnemonicKeyStore = WalletManager.getCurrentKeyStore();
        if (mDidHubMnemonicKeyStore != null)
            mWalletAddressTopView.setData(mDidHubMnemonicKeyStore);
        searchAssetmodelData();
    }

    private void initView(View view) {
        drawerLayout = view.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        leftDrawerView = view.findViewById(R.id.left_drawer);
        view.findViewById(R.id.drawer_wallet_manage).setOnClickListener(this);
        RecyclerView drawerRecyclerView = view.findViewById(R.id.wallet_recycler_view);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        selectWalletAdapter = new SelectWalletAdapter(getContext());
        initDrawerlayoutData();
        drawerRecyclerView.setAdapter(selectWalletAdapter);

        view.findViewById(R.id.add_token).setOnClickListener(this);
        mWalletAddressTopView = view.findViewById(R.id.wallet_card);
        mWalletBottomView = view.findViewById(R.id.bottom_view);
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setBackImg(R.mipmap.wallet_nav);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //钱包选择
                if (mDidHubMnemonicKeyStore != null) {
                    if (!drawerLayout.isDrawerOpen(leftDrawerView)) {
                        drawerLayout.openDrawer(leftDrawerView);
                    } else {
                        drawerLayout.closeDrawer(leftDrawerView);
                    }
                }
            }
        });
        titleLayout.setSecondImageAndClickCallBack(R.mipmap.wallet_manage, new TitleLayout.OnImageClickCallbackListener() {
            @Override
            public void onImageClick() {
                if (mDidHubMnemonicKeyStore != null) {
                    WalletInfoActivity.startAction(getContext(), mDidHubMnemonicKeyStore.getId());
                }
            }
        });
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_qrcode_scan, new TitleLayout.OnImageClickCallbackListener() {
            @Override
            public void onImageClick() {
                QrCodeActivity.startAction(((Activity) getContext()), 100);
            }
        });
    }

    private void initDrawerlayoutData() {
        Hashtable<String, WalletKeystore> walletKeystores = WalletManager.getWalletKeystores();
        LinkedList<WalletKeystore> didHubMnemonicKeyStores = new LinkedList<>();
        for (Iterator<String> iterator = walletKeystores.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            didHubMnemonicKeyStores.add(walletKeystores.get(key));
        }
        selectWalletAdapter.setOnItemClickListener(id -> {
            closeDrawerLayout();
            boolean b = WalletOtherInfoSharpreference.getInstance().setSelectedId(id);
            if (b)
                WalletSelectedObservable.getInstance().update();
        });
        selectWalletAdapter.addDatas(didHubMnemonicKeyStores);
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
            case R.id.drawer_wallet_manage:
                WalletManagerActivity.startAction(getContext());
                closeDrawerLayout();
                break;
        }
    }

    private void closeDrawerLayout() {
        if (drawerLayout.isDrawerOpen(leftDrawerView)) {
            drawerLayout.closeDrawer(leftDrawerView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WalletSelectedObservable.getInstance().deleteObserver(selectWalletObsever);
        WalletNodeSelectedObservable.getInstance().deleteObserver(nodeObervable);
        WalletAddAssetsObservable.getInstance().deleteObserver(addAssetsOberver);
    }

    public boolean onBackPress() {
        if (drawerLayout.isDrawerOpen(leftDrawerView)) {
            drawerLayout.closeDrawer(leftDrawerView);
            return true;
        } else {
            return false;
        }
    }
}
