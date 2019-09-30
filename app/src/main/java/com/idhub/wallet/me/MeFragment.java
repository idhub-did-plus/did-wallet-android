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
import android.widget.ImageView;

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
import com.idhub.wallet.common.zxinglib.QrCodeActivity;
import com.idhub.wallet.common.zxinglib.widget.zing.MipcaActivityCapture;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.me.information.Level1Activity;
import com.idhub.wallet.me.information.Level2Activity;
import com.idhub.wallet.me.information.Level3Activity;
import com.idhub.wallet.me.information.Level4Activity;
import com.idhub.wallet.me.information.Level5Activity;
import com.idhub.wallet.me.information.UploadFileActivity;
import com.idhub.wallet.me.information.UploadInformationTypeActivity;
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


    private MeBottomItemView mIDHubVipView;
    private MeBottomItemView mIDHubSuperVipView;
    private MeBottomItemView mQualifiedInvestorView;
    private MeBottomItemView mQualifiedPurchaserView;
    private MeBottomItemView mStComplianceInvestorView;

    private Observer observer = (o, arg) -> initVipState();

    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler eventHandler = new EventListenerHandler();
    private ImageView mUploadView;
    private ImageView mQRCodeView;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_fragment_me, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        WalletVipStateObservable.getInstance().addObserver(observer);
        mIDHubVipView.setData(getString(R.string.wallet_idhub_vip), R.mipmap.wallet_idhub_vip_success, R.mipmap.wallet_idhub_vip_fail);
        mIDHubSuperVipView.setData(getString(R.string.wallet_idhub_super_vip), R.mipmap.wallet_idhub_svip_success, R.mipmap.wallet_idhub_svip_fail);
        mQualifiedInvestorView.setData(getString(R.string.wallet_qualified_investor), R.mipmap.wallet_sec_accredited_investor_success, R.mipmap.wallet_sec_accredited_investor_fail);
        mQualifiedPurchaserView.setData(getString(R.string.wallet_qualified_purchaser), R.mipmap.wallet_sec_accredited_purchaser_success, R.mipmap.wallet_sec_accredited_purchaser_fail);
        mStComplianceInvestorView.setData(getString(R.string.wallet_st_compliance_investor), R.mipmap.wallet_stcompliant_investor_success, R.mipmap.wallet_stcompliant_investor_fail);
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
        swipeRefreshLayout = view.findViewById(R.id.srl_level);
        swipeRefreshLayout.setOnRefreshListener(this);

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

        mUploadView = view.findViewById(R.id.upload);
        mUploadView.setOnClickListener(this);
        mQRCodeView = view.findViewById(R.id.qrcode_scan);
        mQRCodeView.setOnClickListener(this);
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
        } else if (mUploadView == v) {
            UploadInformationTypeActivity.startAction(getContext());
        } else if (v == mQRCodeView) {
            QrCodeActivity.startAction(getActivity(),100);
        }
    }

    @Override
    protected void loadData() {

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


    private class EventListenerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                MagicEvent magicEvent = (MagicEvent) msg.obj;
                String event = magicEvent.event;
                String eventClass = magicEvent.eventClass;
                String eventType = magicEvent.eventType;
                if (eventType.equals("claim_issued_event")) {
                    if (event.startsWith("refused")) {
                        String[] events = event.split("@");
                        String claimType = events[1];
                        if (ClaimType.IDHub_VIP.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubVipState(VipStateType.REFUSED_APPLY_FOR);
                        } else if (ClaimType.IDHub_SVIP.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubSuperVipState(VipStateType.REFUSED_APPLY_FOR);
                        } else if (ClaimType.SEC_Accredited_Investor.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.REFUSED_APPLY_FOR);
                        } else if (ClaimType.SEC_Accredited_Purchaser.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipState(VipStateType.REFUSED_APPLY_FOR);
                        } else if (ClaimType.ST_Compliant_Investor.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setComplianceInvestorVipState(VipStateType.REFUSED_APPLY_FOR);
                        }
                        WalletVipStateObservable.getInstance().update();
                    } else {
                        JSONObject jsonObject = null;
                        try {
                            String replace = event.replace("\\", "");
                            String s = replace.substring(replace.indexOf("{"), replace.lastIndexOf("}") + 1);
                            jsonObject = new JSONObject(s);
                            JSONObject claim = jsonObject.getJSONObject("claim");
                            String claimType = claim.getString("claimType");
                            if (ClaimType.IDHub_VIP.name().equals(claimType)) {
                                WalletVipSharedPreferences.getInstance().setIdhubVipState(VipStateType.HAVE_APPLY_FOR);
                                WalletVipSharedPreferences.getInstance().setIdHubVipClaim(event);
                            } else if (ClaimType.IDHub_SVIP.name().equals(claimType)) {
                                WalletVipSharedPreferences.getInstance().setIdhubSuperVipState(VipStateType.HAVE_APPLY_FOR);
                                WalletVipSharedPreferences.getInstance().setIdHubSVipClaim(event);
                            } else if (ClaimType.SEC_Accredited_Investor.name().equals(claimType)) {
                                WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.HAVE_APPLY_FOR);
                                WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipClaim(event);
                            } else if (ClaimType.SEC_Accredited_Purchaser.name().equals(claimType)) {
                                WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipState(VipStateType.HAVE_APPLY_FOR);
                                WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipClaim(event);
                            } else if (ClaimType.ST_Compliant_Investor.name().equals(claimType)) {
                                WalletVipSharedPreferences.getInstance().setComplianceInvestorVipState(VipStateType.HAVE_APPLY_FOR);
                                WalletVipSharedPreferences.getInstance().setComplianceInvestorVipClaim(event);
                            }
                            WalletVipStateObservable.getInstance().update();
                        } catch (JSONException ex) {
                            LogUtils.e("did", "requestNetData:message " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WalletVipStateObservable.getInstance().deleteObserver(observer);
    }

}
