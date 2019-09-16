package com.idhub.wallet.wallet.mainfragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletNodeSelectedObservable;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.common.zxinglib.widget.zing.MipcaActivityCapture;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.wallet.mainfragment.view.WalletFragmentBottomView;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;
import com.idhub.wallet.wallet.token.TokenManagerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends MainBaseFragment implements View.OnClickListener, Observer, WalletListDialogFragment.WalletListSelectItemListener {

    private WalletItemView mWalletItem;

    private WalletFragmentBottomView mWalletBottomView;
    private WalletKeystore mDidHubMnemonicKeyStore;

    private NodeOberver nodeObervable = new NodeOberver();

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
        WalletNodeSelectedObservable.getInstance().addObserver(nodeObervable);
        return view;
    }

    private void searchAssetmodelData() {
        //查询数据库资产数，先检查是否有ETH
        new AssetsModelDbManager().queryAll(operation -> {
            if (operation.isCompletedSucessfully()) {
                List<AssetsModel> result = (List<AssetsModel>) operation.getResult();
                String node = WalletOtherInfoSharpreference.getInstance().getNode();
                ArrayList<AssetsModel> list = new ArrayList<>();
                Log.e("LYW", "searchAssetmodelData: " + node);
                //过滤 显示对应ropsten或mainnet上的contractAddress
                if (WalletNodeManager.ROPSTEN.equals(node)){
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
                        if (assetsModel.getType().equals(AssetsDefaultType.ETH_NAME) ) {
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
        mWalletItem.setData(mDidHubMnemonicKeyStore);
        searchAssetmodelData();
    }

    private void initView(View view) {
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_wallet));
        titleLayout.setBackImg(R.mipmap.wallet_list_menu);
        titleLayout.setOnClickListener(v -> {
            //展示钱包列表
            WalletListDialogFragment dialogFragment = WalletListDialogFragment.getInstance(mDidHubMnemonicKeyStore.getAddress());
            if (getFragmentManager() != null) {
                dialogFragment.show(getFragmentManager(), "wallet_dialog_fragment");
            }
            dialogFragment.setWalletListSelectItemListener(this);
        });
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_qrcode_scan, () -> {
            MipcaActivityCapture.startAction(getContext());
        });
        view.findViewById(R.id.add_token).setOnClickListener(this);
        mWalletItem = view.findViewById(R.id.wallet_card);
        mWalletItem.setAddressTextDrawable();
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
        WalletNodeSelectedObservable.getInstance().deleteObserver(nodeObervable);

    }

    public class NodeOberver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            searchAssetmodelData();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        initData();
    }


    @Override
    public void selectItem(String id) {
        //selectWallet
        boolean b = WalletOtherInfoSharpreference.getInstance().setSelectedId(id);
        if (b)
            WalletSelectedObservable.getInstance().update();
    }
}
