package com.idhub.wallet.wallet.mainfragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.common.zxinglib.widget.zing.MipcaActivityCapture;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.wallet.mainfragment.view.WalletFragmentBottomView;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;
import com.idhub.wallet.wallet.token.TokenManagerActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends MainBaseFragment implements View.OnClickListener, Observer {

    private WalletItemView mWalletItem;

    private WalletFragmentBottomView mWalletBottomView;
    private DidHubKeyStore mDidHubKeyStore;

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
        WalletSelectedObservable.getInstance().addObserver(this);
        return view;
    }

    private void searchAssetmodelData() {
        //查询数据库资产数，先检查是否有ETH和IDHUB
        new AssetsModelDbManager().queryAll(operation -> {
            if (operation.isCompletedSucessfully()) {
                List<AssetsModel> result = (List<AssetsModel>) operation.getResult();
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.setName(AssetsDefaultType.ETH_NAME);
                AssetsModel assetsModel1 = new AssetsModel();
                assetsModel1.setName(AssetsDefaultType.IDHUB_NAME);
                assetsModel1.setToken("0x4ede434043c47e9774cd7d28a040c28dd757ddfa");
                AssetsModel assetsModel2 = new AssetsModel();
                assetsModel.setName(AssetsDefaultType.ETH_NAME);
                AssetsModel assetsModel3 = new AssetsModel();
                assetsModel.setName(AssetsDefaultType.ETH_NAME);
                AssetsModel assetsModel5 = new AssetsModel();
                assetsModel5.setName(AssetsDefaultType.ETH_NAME);
                AssetsModel assetsModel6 = new AssetsModel();
                assetsModel6.setName(AssetsDefaultType.ETH_NAME);
                AssetsModel assetsModel7 = new AssetsModel();
                assetsModel7.setName(AssetsDefaultType.ETH_NAME);
                AssetsModel assetsModel4 = new AssetsModel();
                assetsModel4.setName(AssetsDefaultType.IDHUB_NAME);
                assetsModel4.setToken("0x4ede434043c47e9774cd7d28a040c28dd757ddfa");
                result.add(assetsModel);
                result.add(assetsModel1);
                result.add(assetsModel2);
                result.add(assetsModel3);
                result.add(assetsModel4);
                result.add(assetsModel5);
                result.add(assetsModel6);
                result.add(assetsModel7);
                mWalletBottomView.setData(result);
            }
        });
    }

    private void initData() {
        mDidHubKeyStore = WalletManager.getCurrentKeyStore();
        mWalletItem.setData(mDidHubKeyStore);
        searchAssetmodelData();
    }

    private void initView(View view) {
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_wallet));
        titleLayout.setBackImg(R.mipmap.wallet_list_menu);
        titleLayout.setOnClickListener(v -> {
            //展示钱包列表
            WalletListDialogFragment walletListDialog = new WalletListDialogFragment();
            if (getFragmentManager() != null) {
//                walletListDialog.setTargetFragment(WalletFragment.this, 100);
                walletListDialog.show(getFragmentManager(), "wallet_dialog_fragment");
            }
        });
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_qrcode_scan, () -> {
            MipcaActivityCapture.startAction(getContext());
        });
        view.findViewById(R.id.add_token).setOnClickListener(this);
        mWalletItem = view.findViewById(R.id.wallet_card);
        mWalletBottomView = view.findViewById(R.id.bottom_view);

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
        WalletSelectedObservable.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        initData();
    }

    private void updateData() {
        mDidHubKeyStore = WalletManager.getCurrentKeyStore();
        mWalletItem.setData(mDidHubKeyStore);
        mWalletBottomView.onRefresh();
    }
}
