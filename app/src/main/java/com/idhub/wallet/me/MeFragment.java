package com.idhub.wallet.me;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.magic.common.event.MagicEvent;
import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletUpgradeObservable;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.me.information.Level1Activity;
import com.idhub.wallet.me.information.Level2Activity;
import com.idhub.wallet.me.information.Level3Activity;
import com.idhub.wallet.me.information.Level4Activity;
import com.idhub.wallet.me.information.Level5Activity;
import com.idhub.wallet.me.view.MeBottomItemView;
import com.idhub.wallet.me.view.MeTopView;
import com.idhub.wallet.net.IDHubCredentialProvider;

import org.java_websocket.util.Base64;
import org.web3j.crypto.Credentials;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.Observer;

import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.event.EventListener;
import wallet.idhub.com.clientlib.interfaces.ExceptionListener;
import wallet.idhub.com.clientlib.interfaces.Identity;
import wallet.idhub.com.clientlib.interfaces.ResultListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends MainBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private MeTopView mTopView;
    private MeBottomItemView mIDHubVipView;
    private MeBottomItemView mIDHubSuperVipView;
    private MeBottomItemView mQualifiedInvestorView;
    private MeBottomItemView mQualifiedPurchaserView;
    private MeBottomItemView mStComplianceInvestorView;
    private Handler handler = new NetHandler(this);

    private Observer observer = (o, arg) -> initVipState();
    private Observer upgradeObserver = (o, arg) -> loadData();
    private SwipeRefreshLayout swipeRefreshLayout;

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
        WalletVipStateObservable.getInstance().addObserver(observer);
        WalletUpgradeObservable.getInstance().addObserver(upgradeObserver);
        mIDHubVipView.setName(getString(R.string.wallet_idhub_vip));
        mIDHubSuperVipView.setName(getString(R.string.wallet_idhub_super_vip));
        mQualifiedInvestorView.setName(getString(R.string.wallet_qualified_investor));
        mQualifiedPurchaserView.setName(getString(R.string.wallet_qualified_purchaser));
        mStComplianceInvestorView.setName(getString(R.string.wallet_st_compliance_investor));
        //查询 会员状态
        initVipState();
        requestNetData();
    }

    private void requestNetData() {
        IDHubCredentialProvider.setsDefaultAddress(WalletManager.getDefaultAddress());
        ApiFactory.getEventListenerService().listen(new EventListener() {
            @Override
            public void onEvent(MagicEvent e) {
                String event = e.event;
                String eventClass = e.eventClass;
                String eventType = e.eventType;
                Log.e("LYW", "onEvent: " + event + " " + eventClass + " " + eventType);
//                try {

//                    Class type = Class.forName(e.eventClass);
//                    String encoded = e.event;
//                    byte[] json = Base64.decode(encoded);
//                    Object entity = mapper.readValue(json, type);

//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
            }
        });
    }

    private void initVipState() {
        WalletVipSharedPreferences vipSharedPreferences = WalletVipSharedPreferences.getInstance();
        mIDHubVipView.setState(vipSharedPreferences.getIdhubVipState());
        mIDHubSuperVipView.setState(vipSharedPreferences.getIdhubSuperVipState());
        mQualifiedInvestorView.setState(vipSharedPreferences.getQualifiedInvestorVipState());
        mQualifiedPurchaserView.setState(vipSharedPreferences.getQualifiedPurchaserVipState());
        mStComplianceInvestorView.setState(vipSharedPreferences.getComplianceInvestorVipState());
    }

    private void initView(View view) {
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_profile));
        titleLayout.setBackImgVisible(View.INVISIBLE);
        swipeRefreshLayout = view.findViewById(R.id.srl_level);
        swipeRefreshLayout.setOnRefreshListener(this);
        mTopView = view.findViewById(R.id.top_view);
        mIDHubVipView = view.findViewById(R.id.id_hub_vip);
        mIDHubVipView.setOnClickListener(this);
        mIDHubSuperVipView = view.findViewById(R.id.id_hub_super_vip);
        mIDHubSuperVipView.setOnClickListener(this);
        mQualifiedInvestorView = view.findViewById(R.id.qualified_investor);
        mQualifiedInvestorView.setOnClickListener(this);
        mQualifiedPurchaserView = view.findViewById(R.id.qualified_purchaser);
        mQualifiedPurchaserView.setOnClickListener(this);
        mStComplianceInvestorView = view.findViewById(R.id.st_compliance_investor);
        mStComplianceInvestorView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v == mIDHubVipView) {
            Level1Activity.startAction(getContext());
        } else if (v == mIDHubSuperVipView) {
            Level2Activity.startAction(getContext());
        } else if (v == mQualifiedInvestorView) {
            Level3Activity.startAction(getContext());
        } else if (v == mQualifiedPurchaserView) {
            Level4Activity.startAction(getContext());
        } else if (v == mStComplianceInvestorView) {
            Level5Activity.startAction(getContext());
        }
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
                }, s -> {
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
                    Message message = Message.obtain();
                    message.what = 3;
                    message.obj = recoveryAddress;
                    handler.sendMessage(message);
                }, msg -> {
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

    @Override
    public void onRefresh() {
        //刷新
        new Handler().postDelayed(() -> {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        WalletVipStateObservable.getInstance().deleteObserver(observer);
        WalletUpgradeObservable.getInstance().deleteObserver(upgradeObserver);
        handler.removeCallbacksAndMessages(null);
    }

}
