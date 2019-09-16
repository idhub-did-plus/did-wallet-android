package com.idhub.wallet.me;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.me.view.MeBottomItemView;
import com.idhub.wallet.me.view.MeTopView;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Credentials;

import java.lang.ref.WeakReference;
import java.math.BigInteger;

import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.interfaces.ExceptionListener;
import wallet.idhub.com.clientlib.interfaces.Identity;
import wallet.idhub.com.clientlib.interfaces.ResultListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends MainBaseFragment {


    private MeTopView mTopView;
    private MeBottomItemView mIDHubVipView;
    private MeBottomItemView mQualifiedInvestorView;
    private MeBottomItemView mQualifiedPurchaserView;
    private MeBottomItemView mStComplianceInvestorView;
    private Handler handler = new NetHandler(this);

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_me, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
    }

    private void initView(View view) {
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_profile));
        titleLayout.setBackImgVisible(View.INVISIBLE);
        mTopView = view.findViewById(R.id.top_view);
        mIDHubVipView = view.findViewById(R.id.id_hub_vip);
        mQualifiedInvestorView = view.findViewById(R.id.qualified_investor);
        mQualifiedPurchaserView = view.findViewById(R.id.qualified_purchaser);
        mStComplianceInvestorView = view.findViewById(R.id.st_compliance_investor);
    }

    @Override
    protected void loadData() {
        //ein
        String defaultAddress = WalletManager.getDefaultAddress();
        if (TextUtils.isEmpty(defaultAddress)) {
            //显示1056
            mTopView.setEIN1056(WalletManager.getCurrentKeyStore().getAddress());
            mTopView.setRecoverAddressViewVisible(View.INVISIBLE);
        } else {
            //先获取sp里是否有存储，没有则进行网络请求
            String ein = WalletOtherInfoSharpreference.getInstance().getEIN();
            if (TextUtils.isEmpty(ein)) {
                Credentials credentials = Credentials.create("0");
                BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
                IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
                ApiFactory.getIdentityChainLocal().getEIN(defaultAddress).listen(aLong -> {
                    String einStr = aLong.toString();
                    WalletOtherInfoSharpreference.getInstance().setEIN(einStr);
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = einStr;
                    handler.sendMessage(message);
                    Log.e("LYW", "loadDataein: " );

                }, s -> {
                    Log.e("LYW", "loadDataein:error " );
                    Message message = Message.obtain();
                    message.what = 2;
                    handler.sendMessage(message);
                });
            } else {

                setEIN1484View(ein);
                setRecoverAddress(ein);
            }
        }
    }

    private void setRecoverAddress(String ein) {
        //recoverAddress
        String recoverAddress = WalletOtherInfoSharpreference.getInstance().getRecoverAddress();
        Log.e("LYW", "setRecoverAddress: " + recoverAddress );
        if (TextUtils.isEmpty(recoverAddress)) {
            if (TextUtils.isEmpty(ein)) {
                mTopView.setRecoverAddressViewVisible(View.INVISIBLE);
            } else {
                Credentials credentials = Credentials.create("0");
                BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
                IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
                ApiFactory.getIdentityChainLocal().getIdentity(Long.parseLong(ein)).listen(rst -> {
                    String recoveryAddress = rst.getRecoveryAddress();
                    WalletOtherInfoSharpreference.getInstance().setRecoverAddress(recoveryAddress);
                    Log.e("LYW", "setRecoverAddress:1 " + recoveryAddress );
                    Message message = Message.obtain();
                    message.what = 3;
                    message.obj = recoveryAddress;
                    handler.sendMessage(message);
                }, msg -> {
                    Log.e("LYW", "setRecoverAddress:2 "  +msg);
                    Message message = Message.obtain();
                    message.what = 4;
                    handler.sendMessage(message);
                });
            }
        } else {
            mTopView.setRecoverAddress(recoverAddress);
        }
    }

    private void setEIN1484View(String ein) {
        mTopView.setEIN1484(NumericUtil.bigIntegerToHexWithZeroPadded(new BigInteger(ein), 64));
    }

    private static class NetHandler extends Handler{
        private final WeakReference<Fragment> mFragmentReference;

        private NetHandler(Fragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Fragment fragment = mFragmentReference.get();
            if ((fragment instanceof MeFragment)) {
                MeFragment  meFragment = (MeFragment)fragment;
                switch (msg.what) {
                    case 1:
                        Log.e("LYW", "handleMessage: 1");
                        String ein = ((String) msg.obj);
                        meFragment.setEIN1484View(ein);
                        meFragment.setRecoverAddress(ein);
                        break;
                    case 2:
                        Log.e("LYW", "handleMessage: 2");
                        meFragment.mTopView.setEINVisible(View.INVISIBLE);
                        meFragment.setRecoverAddress("");
                        break;
                    case 3:
                        Log.e("LYW", "handleMessage: 3");
                        String recoveryAddress = ((String) msg.obj);
                        meFragment.mTopView.setRecoverAddress(recoveryAddress);
                        break;
                    case 4:
                        Log.e("LYW", "handleMessage: 4");

                        meFragment.mTopView.setRecoverAddressViewVisible(View.INVISIBLE);
                        break;
                }

            }
        }
    }
}
