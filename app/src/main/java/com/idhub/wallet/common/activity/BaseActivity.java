package com.idhub.wallet.common.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.idhub.magic.clientlib.ApiFactory;
import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.Identity1484To1056BindSharedPreferences;
import com.idhub.wallet.common.statusbar.StatusBarUtil;
import com.idhub.base.launage.LocalUtils;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.IdentityDbManager;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.utils.StringUtils;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Keys;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class BaseActivity extends AppCompatActivity {

    private final String INITIALIZE = "initialize";
    private final String RECOVERY = "recovery";
    private AlertDialog progressDialog;
    private AlertDialog alertDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalUtils.setApplicationLanguage(this);
        StatusBarUtil.setImmersiveStatusBar(this, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO:暂时这么先写 判断当前节点没有合约地址
        String identityRegistryInterface = DeployedContractAddress.ERC1056ResolverInterface;
        if (TextUtils.isEmpty(identityRegistryInterface)) {
            return;
        }
        checkIdentityIsBind();
    }

    /**
     * 检查创建身份之后1056身份和1484身份是否关联
     * initialize einToDID的值是空的需要调用
     * recovery einToDID的值不为空与当前默认地址的值不同调用
     */
    protected void checkIdentityIsBind() {
        //initialize或recovery成功 upgradeInitializeIsSuccess 是true 否则就是false
        //检查einToDID的值
        boolean upgradeInitializeIsSuccess = Identity1484To1056BindSharedPreferences.getInstance().getUpgradeInitializeIsSuccess();
        WalletKeystore defaultKeystore = WalletManager.getDefaultKeystore();
        if (defaultKeystore != null && !upgradeInitializeIsSuccess) {
            //表示身份创建成功，但是没有记录到initialize或recovery成功，通过einToDID来判断是哪个调用失败
            //查询einToDID
            String ein = new IdentityDbManager().getEIN(defaultKeystore.getAddress());
            if (!TextUtils.isEmpty(ein)) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        IDHubCredentialProvider.setDefaultCredentials("0");
                        String s = ApiFactory.getIdentityChainLocal().einToDID(ein);
                        emitter.onNext(s);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        BigInteger bigInteger = NumericUtil.hexToBigInteger(s);
                        int i = bigInteger.compareTo(new BigInteger("0"));
                        if (i == 0) {
                            //initialize
                            showDialog(defaultKeystore, getString(R.string.wallet_initialize_replace), INITIALIZE);
                        } else if (!NumericUtil.prependHexPrefix(defaultKeystore.getAddress()).equals(NumericUtil.prependHexPrefix(s))) {
                            //recovery
                            showDialog(defaultKeystore, getString(R.string.wallet_recovery_replace), RECOVERY);
                        } else {
                            //代表已经绑定
                            Identity1484To1056BindSharedPreferences.getInstance().setUpgradeInitializeIsSuccess(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("LYW", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }
    }

    private void showDialog(WalletKeystore defaultKeystore, String message, String type) {
        createProgressDialog();
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.wallet_identity_bind_dialog_view, null);
            TextView addressView = view.findViewById(R.id.address);
            addressView.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(defaultKeystore.getAddress())));
            addressView.setOnClickListener(v -> {
                String s = addressView.getText().toString();
                StringUtils.copy(BaseActivity.this, s);
                ToastUtils.showShortToast(getString(R.string.wallet_copy_address_success));
            });
            TextView msgView = view.findViewById(R.id.message);
            EditText passwordView = view.findViewById(R.id.et_password);
            view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = passwordView.getText().toString().trim();
                    if (TextUtils.isEmpty(password)) {
                        ToastUtils.showShortToast(getString(R.string.wallet_please_input_content));
                        return;
                    }
                    WalletInfo walletInfo = new WalletInfo(WalletManager.getDefaultKeystore());
                    walletInfo.verifyPassword(password, new DisposableObserver<Boolean>() {
                        @Override
                        protected void onStart() {
                            super.onStart();
                            showProgress();
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean) {
                                WalletInfo walletInfo = new WalletInfo(defaultKeystore);
                                String privateKey = walletInfo.exportPrivateKey(password);
                                //检查余额
                                Web3Api.searchBalance(walletInfo.getAddress(), new DisposableSubscriber<EthGetBalance>() {
                                    @Override
                                    public void onNext(EthGetBalance ethGetBalance) {
                                        BigInteger balance = ethGetBalance.getBalance();
                                        String number = NumericUtil.ethBigIntegerToNumberViewPointAfterFour(balance, String.valueOf(Math.pow(10, 18)));
                                        double v = Double.parseDouble(number);
                                        Log.e("LYW", "onNext:Double " + v);
                                        if (v >= 0.2) {
                                            //create wallet
                                            requestContract(privateKey, defaultKeystore, type);
                                        } else {
                                            dismissProgress();
                                            ToastUtils.showLongToast(getString(R.string.wallet_address_none_balance));
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable t) {
                                        ToastUtils.showShortToast(t.getMessage());
                                        dismissProgress();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                            } else {
                                dismissProgress();
                                ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dismissProgress();
                            ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            });
            msgView.setText(message);
            builder.setView(view);
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void createProgressDialog() {
        if (progressDialog == null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(LayoutInflater.from(this).inflate(R.layout.wallet_base_dialog_view, null));
            dialogBuilder.setCancelable(false);
            progressDialog = dialogBuilder.create();
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void requestContract(String privateKey, WalletKeystore defaultKeystore, String type) {
        IDHubCredentialProvider.setDefaultCredentials(privateKey);
        if (INITIALIZE.equals(type)) {
            ApiFactory.getIdentityChainLocal().initialize(defaultKeystore.getAddress()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ERC1056ResolverInterface.IdentityInitializedEventResponse>() {
                @Override
                public void onNext(ERC1056ResolverInterface.IdentityInitializedEventResponse identityInitializedEventResponse) {
                    dismissProgress();
                    String initiator = identityInitializedEventResponse.initiator;
                    String identity = identityInitializedEventResponse.indeitity;
                    BigInteger ein1 = identityInitializedEventResponse.ein;
                    Identity1484To1056BindSharedPreferences.getInstance().setUpgradeInitializeIsSuccess(true);
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    Log.e("LYW", "onNext:initialize " + identity + "  " + initiator + "  " + ein1);
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showLongToast(e.getMessage());
                    Log.e("LYW", "onNext:1 " + e.getMessage());
                    dismissProgress();
                }

                @Override
                public void onComplete() {
                    dismissProgress();
                }
            });
        } else if (RECOVERY.equals(type)) {
            ApiFactory.getIdentityChainLocal().reset(defaultKeystore.getAddress(), privateKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ERC1056ResolverInterface.IdentityResetedEventResponse>() {
                @Override
                public void onNext(ERC1056ResolverInterface.IdentityResetedEventResponse identityResetedEventResponse) {
                    String initiator = identityResetedEventResponse.initiator;
                    String indeitity = identityResetedEventResponse.newIndeitity;
                    String oldIdentity = identityResetedEventResponse.oldIdentity;
                    BigInteger ein1 = identityResetedEventResponse.ein;
                    Identity1484To1056BindSharedPreferences.getInstance().setUpgradeInitializeIsSuccess(true);
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    Log.e("LYW", "onNext: " + indeitity + "  " + initiator + "  " + ein1 + " " + oldIdentity);
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showLongToast(e.getMessage());
                    dismissProgress();
                    Log.e("LYW", "onNext:2 " + e.getMessage());

                }

                @Override
                public void onComplete() {
                    dismissProgress();
                }
            });
        }
    }

    private void showProgress() {
        if (isFinishing()) return;
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
