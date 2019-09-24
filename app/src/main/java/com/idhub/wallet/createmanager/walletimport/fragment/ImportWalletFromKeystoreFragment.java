package com.idhub.wallet.createmanager.walletimport.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.idhub.wallet.R;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.createmanager.walletimport.ImportWalletActivity;
import com.idhub.wallet.createmanager.walletimport.ImportWalletTopView;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.greendao.IdHubMessageDbManager;
import com.idhub.wallet.greendao.IdHubMessageType;
import com.idhub.wallet.greendao.entity.IdHubMessageEntity;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.DateUtils;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Credentials;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import com.idhub.magic.clientlib.ApiFactory;

import static com.idhub.wallet.createmanager.walletimport.ImportWalletActivity.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportWalletFromKeystoreFragment extends Fragment implements View.OnClickListener {


    private EditText mPasswordView;
    private ImportWalletTopView mTopView;
    private LoadingAndErrorView mLoadingAndErrorView;

    public ImportWalletFromKeystoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_import_wallet_from_keystore, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);
        view.findViewById(R.id.tv_import).setOnClickListener(this);
        mPasswordView = view.findViewById(R.id.password);
        mTopView = view.findViewById(R.id.top_view);
        mTopView.setData(getString(R.string.wallet_import_wallet_from_keystore_tip), getString(R.string.wallet_edit_keystore_tip));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_import:
                submit();
                break;
        }
    }

    private void submit() {
        String key = mTopView.getKey();
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showShortToast(getString(R.string.wallet_no_empty_keystore));
            return;
        }
        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast(getString(R.string.wallet_no_empty_password));
            return;
        }

        checkWalletFromKeyStore(key, password, new DisposableObserver<String>() {
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
                        importWallet(wallet, key, password,msg);
                        break;
                    case IMPORT_FIRST_ASSOCIATION:
                        wallet.setName("ETH-Wallet");
                        wallet.setAssociate(true);
                        wallet.setDefaultAddress(true);
                        importWallet(wallet, key, password,msg);
                        break;
                    case IMPORT_EQUAL_ASSOCIATION:
                        wallet.setName("ETH-Wallet");
                        wallet.setAssociate(true);
                        importWallet(wallet, key, password,msg);
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

    private void importWallet(Wallet wallet,String key, String password,String importType) {
        Observable.create((ObservableOnSubscribe<WalletInfo>) emitter -> {
            WalletInfo walletInfo = WalletManager.importWalletFromKeystore(wallet, key, password, true);
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
                        switch (importType){
                            case IMPORT:
                                IdHubMessageEntity idHubMessageEntity = new IdHubMessageEntity();
                                idHubMessageEntity.setType(IdHubMessageType.IMPORT_1056_IDENTITY);
                                idHubMessageEntity.setTime(DateUtils.getCurrentDate());
                                idHubMessageEntity.setAddress(walletInfo.getAddress());
                                new IdHubMessageDbManager().insertData(idHubMessageEntity,null);
                                break;
                            case IMPORT_FIRST_ASSOCIATION:
                                IdHubMessageEntity idHubMessageEntity1 = new IdHubMessageEntity();
                                idHubMessageEntity1.setType(IdHubMessageType.IMPORT_1484_IDENTITY);
                                idHubMessageEntity1.setTime(DateUtils.getCurrentDate());
                                idHubMessageEntity1.setAddress(walletInfo.getAddress());
                                idHubMessageEntity1.setDefaultAddress(walletInfo.getAddress());
                                new IdHubMessageDbManager().insertData(idHubMessageEntity1,null);
                                break;
                            case IMPORT_EQUAL_ASSOCIATION:

                                break;
                        }
                        mLoadingAndErrorView.setVisibility(View.GONE);
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
                    }
                });
    }

    private void checkWalletFromKeyStore(String key,String password, DisposableObserver<String> disposableObserver) {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String address = WalletManager.findWalletAddressByKeystore(key,password);
            WalletInfo wallet = WalletManager.findWalletByAddress(address);
            if (wallet != null) {
                emitter.onNext(NO_IMPORT_EQUAL_ADDRESS);
            } else {
                //检查是否已经注册身份，已注册进行ein判断
                Credentials credentials = Credentials.create("0");
                BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
                IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
                Boolean hasIdentity = ApiFactory.getIdentityChainLocal().hasIdentity(address);
                if (!hasIdentity) {
                    //导入一个没有注册身份的钱包
                    emitter.onNext(IMPORT);
                } else {
                    //已经注册身份
                    String defaultAddress = WalletManager.getDefaultAddress();
                    if (TextUtils.isEmpty(defaultAddress)) {
                        //导入第一个注册身份的wallet
                        emitter.onNext(IMPORT_FIRST_ASSOCIATION);
                    } else {
                        //判断ein是否和已有身份的ein相同
                        BigInteger einSync = ApiFactory.getIdentityChainLocal().getEINSync(address);
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
