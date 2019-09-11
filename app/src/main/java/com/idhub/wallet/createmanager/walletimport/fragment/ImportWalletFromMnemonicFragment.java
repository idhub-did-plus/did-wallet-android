package com.idhub.wallet.createmanager.walletimport.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.createmanager.walletimport.ImportWalletActivity;
import com.idhub.wallet.createmanager.walletimport.ImportWalletInputPasswordView;
import com.idhub.wallet.createmanager.walletimport.ImportWalletTopView;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Credentials;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.interfaces.Listen;

import static com.idhub.wallet.createmanager.walletimport.ImportWalletActivity.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportWalletFromMnemonicFragment extends Fragment implements View.OnClickListener {


    private LoadingAndErrorView mLoadingAndErrorView;
    private ImportWalletTopView mTopView;
    private ImportWalletInputPasswordView mPasswordView;
    private static final String sMneMonicPath = BIP44Util.ETHEREUM_PATH;

    public ImportWalletFromMnemonicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_import_wallet_from_mnemonic, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);
        view.findViewById(R.id.tv_import).setOnClickListener(this);
        mTopView = view.findViewById(R.id.top_view);
        mPasswordView = view.findViewById(R.id.input_password);
        mTopView.setData(getString(R.string.wallet_import_wallet_from_mnemonic_tip), getString(R.string.wallet_edit_mnemonic_tip));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_import:
                submit();
                break;
        }
    }

    private void submit() {
        String key = mTopView.getKey();
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showShortToast(getString(R.string.wallet_no_empty_mnemonic));
            return;
        }
        if (mPasswordView.verifyPassword()) {
            String password = mPasswordView.getPassword();
            String passwordTip = mPasswordView.getPasswordTip();
            checkWalletFromMnemonic(key, new DisposableObserver<String>() {
                @Override
                protected void onStart() {
                    super.onStart();
                    mLoadingAndErrorView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNext(String msg) {
                    Wallet wallet = new Wallet();
                    switch (msg) {
                        case NO_IMPORT_EQUAL_ADDRESS:
                            ToastUtils.showShortToast(getString(R.string.wallet_has_wallet));
                            break;
                        case IMPORT:
                            wallet.setName("ETH-Wallet");
                            wallet.setAssociate(false);
                            wallet.setPasswordHint(passwordTip);
                            importWallet(wallet, key, password, passwordTip);
                            break;
                        case IMPORT_FIRST_ASSOCIATION:
                            wallet.setName("ETH-Wallet");
                            wallet.setAssociate(true);
                            wallet.setDefaultAddress(true);
                            wallet.setPasswordHint(passwordTip);
                            importWallet(wallet, key, password, passwordTip);
                            break;
                        case IMPORT_EQUAL_ASSOCIATION:
                            wallet.setName("ETH-Wallet");
                            wallet.setAssociate(true);
                            wallet.setPasswordHint(passwordTip);
                            importWallet(wallet, key, password, passwordTip);
                            break;
                        case NO_IMPORT_OTHER_IDENTIFY:
                             ToastUtils.showLongToast(getString(R.string.wallet_other_identify_wallet));
                            break;
                    }
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShortToast(e.getMessage());
                    mLoadingAndErrorView.setVisibility(View.GONE);
                }

                @Override
                public void onComplete() {
                    mLoadingAndErrorView.setVisibility(View.GONE);
                }
            });
        }
    }

    private void importWallet(Wallet wallet, String key, String password, String passwordTip) {
        Observable.create((ObservableOnSubscribe<WalletInfo>) emitter -> {
            WalletInfo walletInfo = WalletManager.importWalletFromMnemonic(wallet, key, sMneMonicPath, password, true);
            emitter.onNext(walletInfo);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WalletInfo>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        mLoadingAndErrorView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(WalletInfo walletInfo) {
                        Activity activity = (Activity) getContext();
                        if (activity instanceof ImportWalletActivity) {
                            activity.setResult(Activity.RESULT_OK);
                            activity.finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(e.getMessage());
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }
                });
    }

    private void checkWalletFromMnemonic(String key, DisposableObserver<String> disposableObserver) {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String address = WalletManager.findWalletAddressByMnemonic(key, sMneMonicPath);
            Log.e("LYW", "checkWalletFromMnemonic: " + address);
            WalletInfo wallet = WalletManager.findWalletByAddress(address);
            if (wallet != null) {
                emitter.onNext(NO_IMPORT_EQUAL_ADDRESS);
            } else {
                //检查ein
                Credentials credentials = Credentials.create("0");
                BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
                IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
                BigInteger einSync = ApiFactory.getIdentityChainLocal().getEINSync(NumericUtil.prependHexPrefix(address));
                Log.e("LYW", "checkWalletFromKeyStore: " + einSync);
                if ("0".equals(einSync.toString())) {
                    //导入一个没有关联地址的钱包
                    emitter.onNext(IMPORT);
                } else {
                    //获取当前ein
                    String defaultAddress = WalletManager.getDefaultAddress();
                    if (TextUtils.isEmpty(defaultAddress)) {
                        //导入第一个有关联地址的wallet
                        WalletOtherInfoSharpreference.getInstance().setEIN(einSync.toString());
                        emitter.onNext(IMPORT_FIRST_ASSOCIATION);
                    } else {
                        String ein = WalletOtherInfoSharpreference.getInstance().getEIN();
                        if (TextUtils.isEmpty(ein)) {
                            ein = ApiFactory.getIdentityChainLocal().getEINSync(defaultAddress).toString();
                        }
                        if (ein.equals(einSync.toString())) {
                            //导入相同身份的钱包
                            emitter.onNext(IMPORT_EQUAL_ASSOCIATION);
                        } else {
                            //禁止导入不相同身份的钱包
                            emitter.onNext(NO_IMPORT_OTHER_IDENTIFY);
                        }
                    }
                }
            }
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
}
