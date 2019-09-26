package com.idhub.wallet.me;

import android.app.Activity;
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

import com.idhub.magic.clientlib.http.RetrofitAccessor;
import com.idhub.magic.common.event.MagicEvent;
import com.idhub.magic.common.kvc.entity.ClaimType;
import com.idhub.magic.common.parameter.MagicResponse;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.List;
import java.util.Observer;

import com.idhub.magic.clientlib.ApiFactory;
import com.idhub.magic.clientlib.event.EventListener;
import com.idhub.wallet.utils.LogUtils;
import com.idhub.wallet.utils.ToastUtils;
import com.subgraph.orchid.events.EventHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Handler eventHandler = new EventListenerHandler();
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
        String defaultAddress = WalletManager.getDefaultAddress();
        if (TextUtils.isEmpty(defaultAddress)) {
            defaultAddress = WalletManager.getCurrentKeyStore().getAddress();
        }
        IDHubCredentialProvider.setsDefaultAddress(defaultAddress);
        ApiFactory.getEventListenerService().listen(e -> {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = e;
            eventHandler.sendMessage(obtain);
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
        mTopView.setEIN1484(ein);
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

    private static class NetHandler extends Handler {
        private final WeakReference<Fragment> mFragmentReference;

        private NetHandler(Fragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Fragment fragment = mFragmentReference.get();
            if ((fragment instanceof MeFragment)) {
                MeFragment meFragment = (MeFragment) fragment;
                switch (msg.what) {
                    case 1:
                        String ein = ((String) msg.obj);
                        meFragment.setEIN1484View(ein);
                        meFragment.setRecoverAddress(ein);
                        break;
                    case 2:
                        meFragment.mTopView.setEINVisible(View.INVISIBLE);
                        meFragment.setRecoverAddress("");
                        break;
                    case 3:
                        String recoveryAddress = ((String) msg.obj);
                        meFragment.mTopView.setRecoverAddress(recoveryAddress);
                        break;
                    case 4:
                        meFragment.mTopView.setRecoverAddressViewVisible(View.INVISIBLE);
                        break;
                }

            }
        }
    }

    private class EventListenerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                MagicEvent magicEvent = (MagicEvent) msg.obj;
                String event = magicEvent.event;
                String eventClass = magicEvent.eventClass;
                String eventType = magicEvent.eventType;
                if (eventType.equals("claim_issued_event")) {
                    JSONObject jsonObject = null;
                    try {
                        String replace = event.replace("\\", "");
                        String s = replace.substring(replace.indexOf("{"), replace.lastIndexOf("}")+1);
                        jsonObject = new JSONObject(s);
                        JSONObject claim = jsonObject.getJSONObject("claim");
                        String claimType = claim.getString("claimType");
                        if (ClaimType.idhub_vip.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setIdHubVipClaim(event);
                        } else if (ClaimType.idhub_svip.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubSuperVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setIdHubSVipClaim(event);
                        } else if (ClaimType.qualified_investor.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipClaim(event);
                        } else if (ClaimType.qualified_buyer.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipClaim(event);
                        } else if (ClaimType.investor_compliance.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setComplianceInvestorVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setComplianceInvestorVipClaim(event);
                        }
                        WalletVipStateObservable.getInstance().update();
                    } catch (JSONException ex) {
                        LogUtils.e("did", "requestNetData:message "+ ex.getMessage());
                        ex.printStackTrace();
                    }
                } else if (eventType.equals("claim_refused_event")) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(event);
                        String claimType = jsonObject.getString("claimType");
                        if (ClaimType.idhub_vip.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubVipState(VipStateType.REFUSED_APPLY_FOR);
                        } else if (ClaimType.idhub_svip.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubSuperVipState(VipStateType.REFUSED_APPLY_FOR);
                        } else if (ClaimType.qualified_investor.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.REFUSED_APPLY_FOR);
                        }else if (ClaimType.qualified_buyer.name().equals(claimType)){
                            WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipState(VipStateType.REFUSED_APPLY_FOR);
                        } else if (ClaimType.investor_compliance.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setComplianceInvestorVipState(VipStateType.REFUSED_APPLY_FOR);
                        }
                        WalletVipStateObservable.getInstance().update();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
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
