package com.idhub.wallet.me;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.Identity1484To1056BindSharedPreferences;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.common.walletobservable.WalletUpdateUserInfoObservable;
import com.idhub.wallet.common.walletobservable.WalletUpgradeObservable;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.common.zxinglib.QrCodeActivity;
import com.idhub.wallet.common.zxinglib.widget.zing.MipcaActivityCapture;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Wallet;
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

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends MainBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private MeTopView mTopView;
    private Observer userInfoObserver = (o, arg) -> {
        mTopView.setUserInfo();
    };
    private Observer upgradeObserver = (o, arg) -> {
        initTopViewData();
    };
    private Handler handler = new NetHandler(this);
    private MeBottomItemView mIDHubVipView;
    private MeBottomItemView mIDHubSuperVipView;
    private MeBottomItemView mQualifiedInvestorView;
    private MeBottomItemView mQualifiedPurchaserView;
    private MeBottomItemView mStComplianceInvestorView;

    private Observer observer = (o, arg) -> initVipState();

    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler eventHandler = new EventListenerHandler();

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_fragment_me, container, false);
        initView(view);
        initData();
        initTopViewData();
        return view;
    }

    private void initData() {
        WalletVipStateObservable.getInstance().addObserver(observer);
        WalletUpdateUserInfoObservable.getInstance().addObserver(userInfoObserver);
        WalletUpgradeObservable.getInstance().addObserver(upgradeObserver);
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
        IDHubCredentialProvider.setsDefaultAddress(NumericUtil.prependHexPrefix(defaultAddress));
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
        mTopView = view.findViewById(R.id.top_view);
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
                        initVipState();
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
                            initVipState();
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
        WalletUpdateUserInfoObservable.getInstance().deleteObserver(userInfoObserver);
        WalletVipStateObservable.getInstance().deleteObserver(observer);
        WalletUpgradeObservable.getInstance().deleteObserver(upgradeObserver);
        handler.removeCallbacksAndMessages(null);
    }


    private void initTopViewData() {
        //设置ein和recoverAddress
        String defaultAddress = WalletManager.getDefaultAddress();
        if (TextUtils.isEmpty(defaultAddress)) {
            //显示1056
            mTopView.setEIN1056(WalletManager.getCurrentKeyStore().getAddress());
            mTopView.setRecoverAddressViewVisible(View.GONE);
        } else {
            //先获取sp里是否有存储，没有则进行网络请求
            String ein = WalletOtherInfoSharpreference.getInstance().getEIN();
            if (TextUtils.isEmpty(ein)) {
                checkHasIdentity(defaultAddress);
            } else {
                setEIN1484View(ein);
                setRecoverAddress(ein);
            }
        }
        //解决升级身份成功本地未记录的情况，
        boolean isUpgradeAction = Identity1484To1056BindSharedPreferences.getInstance().getIsUpgradeAction();
        if (isUpgradeAction && TextUtils.isEmpty(defaultAddress)) {
            defaultAddress = WalletManager.getCurrentKeyStore().getAddress();
            checkHasIdentity(defaultAddress);
        }

    }

    private void getEIN(String defaultAddress) {
        //TODO:暂时这么先写 判断当前节点没有合约地址
        String identityRegistryInterface = DeployedContractAddress.IdentityRegistryInterface;
        if (TextUtils.isEmpty(identityRegistryInterface)) {
            return;
        }
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
    }


    private void checkHasIdentity(String defaultAddress) {
        //TODO:暂时这么先写 判断当前节点没有合约地址
        String identityRegistryInterface = DeployedContractAddress.IdentityRegistryInterface;
        if (TextUtils.isEmpty(identityRegistryInterface)) {
            return;
        }
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            Credentials credentials = Credentials.create("0");
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
            IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
            Boolean hasIdentity = ApiFactory.getIdentityChainLocal().hasIdentity(defaultAddress);
            emitter.onNext(hasIdentity);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                WalletKeystore defaultKeystore = WalletManager.getDefaultKeystore();
                if (defaultKeystore == null) {
                    defaultKeystore = WalletManager.getCurrentKeyStore();
                }
                if (aBoolean) {
                    Wallet wallet = defaultKeystore.getWallet();
                    if (!wallet.isDefaultAddress()) {
                        wallet.setAssociate(true);
                        wallet.setDefaultAddress(true);
                        WalletManager.flushWallet(defaultKeystore, true);
                        WalletSelectedObservable.getInstance().update();
                    }
                    getEIN(defaultAddress);
                }else {
                    Wallet wallet = defaultKeystore.getWallet();
                    if (wallet.isDefaultAddress()) {
                        wallet.setAssociate(false);
                        wallet.setDefaultAddress(false);
                        WalletManager.flushWallet(defaultKeystore, true);
                        WalletSelectedObservable.getInstance().update();
                    }
                    mTopView.setEIN1056(WalletManager.getCurrentKeyStore().getAddress());
                    mTopView.setRecoverAddressViewVisible(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("LYW", "onNext:checkHasIdentityonError " + e.getMessage() );
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void setRecoverAddress(String ein) {
        //recoverAddress
        String recoverAddress = WalletOtherInfoSharpreference.getInstance().getRecoverAddress();
        if (TextUtils.isEmpty(recoverAddress)) {
            if (TextUtils.isEmpty(ein)) {
                mTopView.setRecoverAddressViewVisible(View.GONE);
            } else {
                //TODO:暂时这么先写 判断当前节点没有合约地址
                String identityRegistryInterface = DeployedContractAddress.IdentityRegistryInterface;
                if (TextUtils.isEmpty(identityRegistryInterface)) {
                    return;
                }
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
                        meFragment.mTopView.setRecoverAddressViewVisible(View.GONE);
                        break;
                }

            }
        }
    }
}
