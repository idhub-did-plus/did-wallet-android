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

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.dialog.SignMessageDialogFragment;
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
import com.idhub.wallet.utils.QRcodeAnalysisUtils;
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
public class WalletFragment extends MainBaseFragment implements View.OnClickListener, WalletListDialogFragment.WalletListSelectItemListener, SignMessageDialogFragment.SignMessageDialogFragmentListener {

    private WalletItemView mWalletItem;

    private WalletFragmentBottomView mWalletBottomView;
    private WalletKeystore mDidHubMnemonicKeyStore;

    private Observer nodeObervable = (o, arg) -> searchAssetmodelData();
    private Observer addAssetsOberver = (o, arg) -> searchAssetmodelData();
    private Observer selectWalletObsever = (o, arg) -> initData();
    private LoadingAndErrorView mLoadingAndErrorView;
    private JSONObject mIdHubLoginJwtJsonObject;
    private String signMessage;

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
        WalletSelectedObservable.getInstance().addObserver(selectWalletObsever);
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
//                String tipContent = getString(R.string.wallet_login_sign_tip) +" "+ url;
                String signTitle = getString(R.string.wallet_login_sign_message);
                Log.e("LYW", "handleQrCodeStr:signMessage "  + signMessage);
//                Log.e("LYW", "handleQrCodeStr:tipContent "  + tipContent);
                Log.e("LYW", "handleQrCodeStr:signTitle " +signTitle);
                SignMessageDialogFragment fragment = SignMessageDialogFragment.getInstance(getString(R.string.wallet_login_sign_tip),url, signTitle, signMessage);
                AppCompatActivity context = (AppCompatActivity) getContext();
                fragment.show(context.getSupportFragmentManager(),"sign_message_dialog_fragment");
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
        WalletSelectedObservable.getInstance().deleteObserver(selectWalletObsever);
        WalletNodeSelectedObservable.getInstance().deleteObserver(nodeObervable);
        WalletAddAssetsObservable.getInstance().deleteObserver(addAssetsOberver);

    }


    @Override
    public void selectItem(String id) {
        //selectWallet
        boolean b = WalletOtherInfoSharpreference.getInstance().setSelectedId(id);
        if (b)
            WalletSelectedObservable.getInstance().update();
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
                    Response result = signJWTLogin( walletInfo, password);
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
}
