package com.idhub.wallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.SignMessageDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.createmanager.IdentityManagerActivity;
import com.idhub.wallet.createmanager.UploadUserBasicInfoActivity;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.transaction.EthereumSign;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.TransactionRecordDbManager;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;
import com.idhub.wallet.me.view.MeTopView;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.setting.NotificationUtils;
import com.idhub.wallet.utils.LocalUtils;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.mainfragment.QRCodeType;
import com.idhub.wallet.wallet.mainfragment.WalletFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.api.etherscan.model.Tx;
import io.api.etherscan.model.TxToken;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.idhub.magic.clientlib.ApiFactory;
import com.idhub.magic.clientlib.interfaces.IncomingListener;
import com.idhub.magic.clientlib.interfaces.IncomingService;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

public class MainActivity extends BaseActivity implements SignMessageDialogFragment.SignMessageDialogFragmentListener {

    private MainFragmentPagerAdapter adapter;
    private MeTopView mTopView;
    private Handler handler = new NetHandler(this);
    private int[] tabIcons = {
            R.drawable.wallet_me_normal,
            R.drawable.wallet_wallet_normal,
            R.drawable.wallet_dapp_normal
    };
    private int[] tabIconsPressed = {
            R.drawable.wallet_me_selected,
            R.drawable.wallet_wallet_selected,
            R.drawable.wallet_dapp_selected
    };
    private long mFirstTime;
    private String[] mItems;
    private JSONObject mIdHubLoginJwtJsonObject;
    private String signMessage;
    private LoadingAndErrorView mLoadingAndErrorView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalUtils.setLocal(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalUtils.updateLocale(this);
        Configuration config = getResources().getConfiguration();
        Log.e("LYW", "onCreate: " + config.locale.getLanguage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        init();
        setContentView(R.layout.wallet_activity_main);
        initView();
        initData();
    }

    public static void reStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void init() {
        // 检查钱包数
        WalletManager.scanWallets();
        int walletNum = WalletManager.getWalletNum();
        if (walletNum <= 0) {
            IdentityManagerActivity.startAction(this);
            finish();
            return;
        }
        //检查是否上传头像和名字
        UserBasicInfoEntity userBasicInfo = UserBasicInfoSharpreference.getInstance().getUserBasicInfo();
        if (TextUtils.isEmpty(userBasicInfo.name)) {
            //检查姓名是否为空，为空则去填写，不为空meFragment加载
            UploadUserBasicInfoActivity.startAction(this);
            finish();
            return;
        }
        List<String> accounts = new ArrayList<>();
        for (WalletKeystore value : WalletManager.getWalletKeystores().values()) {
            accounts.add(NumericUtil.prependHexPrefix(value.getAddress()));
        }
        IncomingService incomingService = ApiFactory.getIncomingService();
        incomingService.setAccounts(accounts);
        incomingService.subscribeTransaction(new IncomingListener<Tx>() {
            @Override
            public void income(List<Tx> txes) {
                List<TransactionRecordEntity> transactionRecordEntities = new ArrayList<>();
                for (Tx tx : txes) {
                    TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
                    transactionRecordEntity.setTx(tx);
                    transactionRecordEntities.add(transactionRecordEntity);
                }
                if (txes.size() == 1) {
                    Tx tx = txes.get(0);
                    TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
                    transactionRecordEntity.setTx(tx);
                    NotificationUtils.sendTransactionNotification(MainActivity.this, transactionRecordEntity);
                    WalletAddAssetsObservable.getInstance().update();
                }
                new TransactionRecordDbManager().insertListDataTo50Datas(transactionRecordEntities);
            }
        });
        incomingService.subscribeTransfer(new IncomingListener<TxToken>() {
            @Override
            public void income(List<TxToken> txTokens) {
                List<TransactionRecordEntity> transactionRecordEntities = new ArrayList<>();
                for (TxToken txToken : txTokens) {
                    TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
                    transactionRecordEntity.setTxToken(txToken);
                    transactionRecordEntities.add(transactionRecordEntity);
                }
                if (txTokens.size() == 1) {
                    TxToken tx = txTokens.get(0);
                    TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
                    transactionRecordEntity.setTxToken(tx);
                    NotificationUtils.sendTransactionNotification(MainActivity.this, transactionRecordEntity);
                    WalletAddAssetsObservable.getInstance().update();
                }
                new TransactionRecordDbManager().insertListDataTo50Datas(transactionRecordEntities);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String data = intent.getStringExtra("data");
        if ("delete".equals(data)) {
            if (WalletManager.getWalletNum() <= 0) {
                IdentityManagerActivity.startAction(this);
                finish();
                return;
            }
            //删除如果是显示的address 需要通知更新
            WalletSelectedObservable.getInstance().update();
        } else if ("upgrade".equals(data)) {
            WalletSelectedObservable.getInstance().update();
            //upgrade
            initData();
        } else if ("transaction".equals(data)) {
            WalletAddAssetsObservable.getInstance().update();
        } else if ("add".equals(data)) {
            WalletSelectedObservable.getInstance().update();
        }
    }

    public static void startAction(Context context, String source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("data", source);
        context.startActivity(intent);
    }

    private void initView() {
        mTopView = findViewById(R.id.top_view);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = adapter.getCurrentFragment();
                if (fragment instanceof WalletFragment) {
                    View view = fragment.getView();
                    if (view != null) {
                        view.requestLayout();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
        mItems = new String[]{getResources().getString(R.string.wallet_me), getResources().getString(R.string.wallet_wallet),
                getResources().getString(R.string.wallet_dapp)};
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this, mItems);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = view.findViewById(R.id.tab_name);
        ImageView img_title = view.findViewById(R.id.tab_image);
        txt_title.setTextColor(getResources().getColor(R.color.wallet_main_tab_color_normal));
        img_title.setImageResource(tabIcons[tab.getPosition()]);
    }

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = view.findViewById(R.id.tab_name);
        ImageView img_title = view.findViewById(R.id.tab_image);
        txt_title.setTextColor(getResources().getColor(R.color.wallet_main_tab_color_select));
        img_title.setImageResource(tabIconsPressed[tab.getPosition()]);

    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.wallet_main_tab_item, null);
        TextView txt_title = view.findViewById(R.id.tab_name);
        txt_title.setText(mItems[position]);
        ImageView img_title = view.findViewById(R.id.tab_image);
        img_title.setImageResource(tabIcons[position]);

        if (position == 0) {
            txt_title.setTextColor(getResources().getColor(R.color.wallet_main_tab_color_select));
            img_title.setImageResource(tabIconsPressed[position]);
        } else {
            txt_title.setTextColor(getResources().getColor(R.color.wallet_main_tab_color_normal));
            img_title.setImageResource(tabIcons[position]);
        }
        return view;
    }

    private void initData() {
        //设置ein和recoverAddress
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


    private static class NetHandler extends Handler {
        private final WeakReference<AppCompatActivity> mFragmentReference;

        private NetHandler(AppCompatActivity activity) {
            mFragmentReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AppCompatActivity activity = mFragmentReference.get();
            if ((activity instanceof MainActivity)) {
                MainActivity mainActivity = (MainActivity) activity;
                switch (msg.what) {
                    case 1:
                        String ein = ((String) msg.obj);
                        mainActivity.setEIN1484View(ein);
                        mainActivity.setRecoverAddress(ein);
                        break;
                    case 2:
                        mainActivity.mTopView.setEINVisible(View.INVISIBLE);
                        mainActivity.setRecoverAddress("");
                        break;
                    case 3:
                        String recoveryAddress = ((String) msg.obj);
                        mainActivity.mTopView.setRecoverAddress(recoveryAddress);
                        break;
                    case 4:
                        mainActivity.mTopView.setRecoverAddressViewVisible(View.INVISIBLE);
                        break;
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String qrcode = data.getStringExtra("qrcode");
                handleQrCodeStr(qrcode);
            }
        }
    }

    private void handleQrCodeStr(String qrcode) {
        if (qrcode.startsWith(QRCodeType.IDHUB_LOGIN_HEADER)) {
            //login
            //先解析url和rdt域名是否一致
            String jwtCode = new String(Base64.decode(qrcode.substring(QRCodeType.IDHUB_LOGIN_HEADER.length()), Base64.URL_SAFE | Base64.NO_WRAP));
            try {
                mIdHubLoginJwtJsonObject = new JSONObject(jwtCode);
                String url = mIdHubLoginJwtJsonObject.getString("url");
                String rdtUrl = mIdHubLoginJwtJsonObject.getString("rdt");
                String urlSub = url.substring(0, url.lastIndexOf(":"));
                String rdtUrlSub = rdtUrl.substring(0, rdtUrl.lastIndexOf(":"));
                if (!rdtUrlSub.contains(urlSub)) {
                    ToastUtils.showLongToast(getString(R.string.wallet_login_code_fail));
                    return;
                }
                WalletKeystore walletKeystore = WalletManager.getDefaultKeystore();
                if (walletKeystore == null) {
                    walletKeystore = WalletManager.getCurrentKeyStore();
                }
                WalletInfo walletInfo = new WalletInfo(walletKeystore);
                long l = System.currentTimeMillis() / 1000 + 30;
                mIdHubLoginJwtJsonObject.put("exp", l);
                mIdHubLoginJwtJsonObject.put("iss", NumericUtil.prependHexPrefix(walletInfo.getAddress()));
                String middleJson = mIdHubLoginJwtJsonObject.toString();
                JSONObject startJsonObject = new JSONObject();
                startJsonObject.put("alg", "ES256k");
                startJsonObject.put("typ", "JWT");
                String payload = Base64.encodeToString(middleJson.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
                String head = Base64.encodeToString(startJsonObject.toString().getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
                signMessage = head + "." + payload;
                String signTitle = getString(R.string.wallet_login_sign_message);
                SignMessageDialogFragment fragment = SignMessageDialogFragment.getInstance(getString(R.string.wallet_login_sign_tip), url, signTitle, signMessage);
                fragment.show(getSupportFragmentManager(), "sign_message_dialog_fragment");
                fragment.setSignMessageListener(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showLongToast(qrcode);
        }
    }

    private Response signJWTLogin(WalletInfo walletInfo, String password) throws JSONException, IOException {
        //jwt 将base64解析，添加json字段Base64编码payload ，编码Base64 json  jwthead格式 ，head.payload 进行签名消息signMessage
        //Base64编码签名消息  head.payload.signMessage  进行http请求发送最终数据
        //签名消息
        String sign = EthereumSign.sign(signMessage, walletInfo.exportPrivateKey(password));
        byte[] signBytes = NumericUtil.hexToBytes(sign);
        String rdtUrl = mIdHubLoginJwtJsonObject.optString("rdt");
        String jwtSignMessage = signMessage + "." + Base64.encodeToString(signBytes, Base64.URL_SAFE | Base64.NO_WRAP);
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        //创建RequestBody对象，将参数按照指定的MediaType封装
        JSONObject jwtSendJson = new JSONObject();
        jwtSendJson.put("jwt", jwtSignMessage);
        RequestBody requestBody = RequestBody.create(mediaType, jwtSendJson.toString());
        Request request = new Request
                .Builder()
                .post(requestBody)//Post请求的参数传递
                .url(rdtUrl)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    @Override
    public void inputConfirm(String password) {
        mLoadingAndErrorView.onLoading();
        io.reactivex.Observable.create((ObservableOnSubscribe<Response>) emitter -> {
            WalletKeystore walletKeystore = WalletManager.getDefaultKeystore();
            if (walletKeystore == null) {
                walletKeystore = WalletManager.getCurrentKeyStore();
            }
            WalletInfo walletInfo = new WalletInfo(walletKeystore);
            boolean b = walletInfo.verifyPassword(password);
            if (b) {
                Response result = signJWTLogin(walletInfo, password);
                emitter.onNext(result);
                emitter.onComplete();
            } else {
                emitter.onError(new Throwable(getString(R.string.wallet_input_password_false)));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response s) {
                mLoadingAndErrorView.onGone();
                if (s.code() != 200) {
                    ToastUtils.showLongToast(getString(R.string.wallet_login_fail));
                }
            }

            @Override
            public void onError(Throwable e) {
                mLoadingAndErrorView.onGone();
                ToastUtils.showLongToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - mFirstTime > 2000) {
            ToastUtils.showShortToast(getString(R.string.wallet_main_back_double_click));
            mFirstTime = secondTime;
        } else {
            super.onBackPressed();
        }
    }
}
