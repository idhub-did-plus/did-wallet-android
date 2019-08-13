package com.idhub.wallet.wallet.mainfragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.qrCode.QRCodeScanActivity;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.WalletOtherSharpreference;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.identitymanager.IdentityManagerActivity;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;
import com.idhub.wallet.wallet.token.TokenManagerActivity;

import java.util.Hashtable;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends MainBaseFragment implements View.OnClickListener {

    private WalletItemView mWalletItem;
    private Hashtable<String, DidHubKeyStore> mWalletKeystores;
    private WalletFragmentBottomView mWalletBottomView;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_wallet, container, false);
        Hashtable<String, DidHubKeyStore> walletKeystores = WalletManager.getWalletKeystores();
        if (walletKeystores.size() > 0) {
            mWalletKeystores = walletKeystores;
            initView(view);
            initData();
        } else {
            Context context = getContext();
            Activity activity = (Activity) context;
            if (activity != null) {
                activity.finish();
            }
            IdentityManagerActivity.startAction(context);
        }
        return view;
    }

    private void initData() {
        String selectedId = WalletOtherSharpreference.getInstance().getSelectedId();
        DidHubKeyStore didHubKeyStore = mWalletKeystores.get(selectedId);
        if (didHubKeyStore == null) {
            String key = mWalletKeystores.keySet().iterator().next();
            didHubKeyStore = mWalletKeystores.get(key);
            WalletOtherSharpreference.getInstance().setSelectedId(didHubKeyStore.getId());
        }
        mWalletItem.setData(didHubKeyStore);
    }

    private void initView(View view) {
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_wallet));
        titleLayout.setBackImg(R.mipmap.wallet_list_menu);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //展示钱包列表
                WalletListDialogFragment walletListDialog = new WalletListDialogFragment();
                if (getFragmentManager() != null) {
                    walletListDialog.setTargetFragment(WalletFragment.this,100);
                    walletListDialog.show(getFragmentManager(), "wallet_dialog_fragment");
                }
            }
        });
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_qrcode_scan, new TitleLayout.OnImageClickCallbackListener() {
            @Override
            public void onImageClick() {
                QRCodeScanActivity.startAction(getContext());
            }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK){
               initData();
        }
    }
}
