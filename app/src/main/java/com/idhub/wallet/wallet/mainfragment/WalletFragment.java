package com.idhub.wallet.wallet.mainfragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.TextUtils;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.common.walletobservable.WalletNodeSelectedObservable;
import com.idhub.wallet.common.walletobservable.WalletSelectedObservable;
import com.idhub.wallet.common.zxinglib.widget.zing.MipcaActivityCapture;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.transaction.EthereumSign;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.mainfragment.view.WalletFragmentBottomView;
import com.idhub.wallet.wallet.mainfragment.view.WalletItemView;
import com.idhub.wallet.wallet.token.TokenManagerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends MainBaseFragment implements View.OnClickListener, Observer, WalletListDialogFragment.WalletListSelectItemListener, InputDialogFragment.InputDialogFragmentListener {

    private WalletItemView mWalletItem;

    private WalletFragmentBottomView mWalletBottomView;
    private WalletKeystore mDidHubMnemonicKeyStore;

    private Observer nodeObervable = (o, arg) -> searchAssetmodelData();
    private Observer addAssetsOberver = (o, arg) -> searchAssetmodelData();
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mIdhubLoginCode;

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
                String node = WalletOtherInfoSharpreference.getInstance().getNode();
                ArrayList<AssetsModel> list = new ArrayList<>();
                //过滤 显示对应ropsten或mainnet上的contractAddress
                if (WalletNodeManager.ROPSTEN.equals(node)) {
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
                        if (assetsModel.getType().equals(AssetsDefaultType.ETH_NAME)) {
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
            MipcaActivityCapture.startAction(this, 100);
        });
        view.findViewById(R.id.add_token).setOnClickListener(this);
        mWalletItem = view.findViewById(R.id.wallet_card);
        mWalletItem.setAddressTextDrawable();
        mWalletBottomView = view.findViewById(R.id.bottom_view);
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String qrcode = data.getStringExtra("qrcode");
                Log.e("LYW", "onActivityResult: " + qrcode);
                handleQrCodeStr(qrcode);
            }

        }
    }

    private void handleQrCodeStr(String qrcode) {
        if (qrcode.startsWith(QRCodeType.IDHUB_LOGIN_HEADER)) {
            //login
            //输入密码
            mIdhubLoginCode = qrcode;
            InputDialogFragment instance = InputDialogFragment.getInstance("idhub_login", getString(R.string.wallet_default_address_password), InputType.TYPE_CLASS_TEXT);
            FragmentManager fragmentManager = ((AppCompatActivity) this.getContext()).getSupportFragmentManager();
            instance.show(fragmentManager, "input_dialog_fragment");
            instance.setInputDialogFragmentListener(this);
        }
    }

    private String signJWTLogin(String qrcode, WalletInfo walletInfo, String password) throws JSONException, IOException {
        String jwtStr = qrcode.substring(QRCodeType.IDHUB_LOGIN_HEADER.length());
        Log.e("LYW", "signJWTLogin: " + jwtStr);
        String decode = new String(Base64.decode(jwtStr, Base64.URL_SAFE | Base64.NO_WRAP));
        Log.e("LYW", "signJWTLogin: " + decode);
        JSONObject jsonObject = null;
        jsonObject = new JSONObject(decode);
        long l = System.currentTimeMillis() / 1000 + 30;
        jsonObject.put("exp", l);
        jsonObject.put("iss", NumericUtil.prependHexPrefix(walletInfo.getAddress()));
        String middleJson = jsonObject.toString();
        Log.e("LYW", "signJWTLogin: " + middleJson);
        JSONObject startJsonObject = new JSONObject();
        startJsonObject.put("alg", "ES256k");
        startJsonObject.put("typ", "JWT");
        String payload = Base64.encodeToString(middleJson.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        String head = Base64.encodeToString(startJsonObject.toString().getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        String message = head + "." + payload;
        System.out.println(message);
        //签名消息
        String sign = EthereumSign.sign(message, walletInfo.exportPrivateKey(password));
        Log.e("LYW", "signJWTLogin:sign " + sign);
        byte[] signBytes = NumericUtil.hexToBytes(sign);
        String url = jsonObject.optString("rdt");
        String signMessage = message + "." + Base64.encodeToString(signBytes, Base64.URL_SAFE | Base64.NO_WRAP);
        Log.e("LYW", "signJWTLogin: " + signMessage);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        //创建RequestBody对象，将参数按照指定的MediaType封装
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request
                .Builder()
                .post(requestBody)//Post请求的参数传递
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody body = response.body();
        if (body != null) {
            String result = body.string();
            body.close();
            return result;
        } else {
            return "";
        }
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
        WalletAddAssetsObservable.getInstance().deleteObserver(addAssetsOberver);

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

    @Override
    public void inputConfirm(String data, String source) {
        if ("idhub_login".equals(source)) {
            mLoadingAndErrorView.onLoading();
            io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    WalletKeystore walletKeystore = WalletManager.getDefaultKeystore();
                    if (walletKeystore == null) {
                        walletKeystore = WalletManager.getCurrentKeyStore();
                    }
                    WalletInfo walletInfo = new WalletInfo(walletKeystore);
                    boolean b = walletInfo.verifyPassword(data);
                    if (b) {
                        String result = signJWTLogin(mIdhubLoginCode, walletInfo, data);
                        emitter.onNext(result);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable(getString(R.string.wallet_input_password_false)));
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<String>() {
                @Override
                public void onNext(String s) {
                    mLoadingAndErrorView.onGone();
                    Log.e("LYW", "onNext: " + s );
                }

                @Override
                public void onError(Throwable e) {
                    mLoadingAndErrorView.onGone();
                    ToastUtils.showShortToast(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }
}
