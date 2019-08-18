package com.idhub.wallet.wallet.transaction;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletTransactionSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.DidHubIdentify;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.network.Web3Api;
import com.idhub.wallet.network.Web3jSubscriber;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.abi.datatypes.Bool;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SendConfirmActivity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener,InputGasDialogFragment.InputGasDialogFragmentListener {

    private TextView mSendAmountView;
    private TextView mFromAddressView;
    private TextView mToAddressView;
    private TextView mGasAmountView;
    private AssetsModel mAssetsModel;
    private View mConfirmView;
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mGasLimit;
    private String mGasPrice;
    private String mToAddress;
    private String mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_send_confirm);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Parcelable data = getIntent().getParcelableExtra("assets");
        mAssetsModel = (AssetsModel) data;
        mToAddress = intent.getStringExtra("toAddress");
        mAmount = intent.getStringExtra("amount");
        mToAddressView.setText(mToAddress);
        if (mAssetsModel != null) {
            mSendAmountView.setText(mAmount + " " + mAssetsModel.getName());
            mFromAddressView.setText(mAssetsModel.getAddress());
        }
        mGasPrice = WalletTransactionSharpreference.getInstance().getGasPrice();
        String token = mAssetsModel.getToken();
        if (TextUtils.isEmpty(token)) {
            mGasLimit = WalletTransactionSharpreference.getInstance().getEthGasLimit();
        } else {
            mGasLimit = WalletTransactionSharpreference.getInstance().getERC20GasLimit();
        }
        setGasAmount();
    }

    private void setGasAmount() {
        Log.e("LYW", "initData: " + mGasLimit + " " + mGasPrice);
        BigInteger bigIntegerGasLimit = new BigInteger(mGasLimit);
        BigInteger bigIntegerGasPrice = new BigInteger(mGasPrice);
        BigInteger gasEthAmount = bigIntegerGasLimit.multiply(bigIntegerGasPrice);
        String s = NumericUtil.ethBigIntegerToNumberViewPointAfterEight(gasEthAmount);
        mGasAmountView.setText(s +" ETH");
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_send_confirm));
        mSendAmountView = findViewById(R.id.send_amount);
        mFromAddressView = findViewById(R.id.from_address);
        mToAddressView = findViewById(R.id.to_address);
        mGasAmountView = findViewById(R.id.gas_amount);
        mConfirmView = findViewById(R.id.tv_confirm);
        mConfirmView.setOnClickListener(this);
        mGasAmountView.setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }


    @Override
    public void onClick(View v) {
        if (v == mGasAmountView) {
            InputGasDialogFragment inputGasDialogFragment = InputGasDialogFragment.getInstance(mGasPrice, mGasLimit);
            inputGasDialogFragment.show(getSupportFragmentManager(), "input_gas_dialog_fragment");
        } else if (v == mConfirmView) {
            InputDialogFragment instance = InputDialogFragment.getInstance("send", getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT);
            instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        }
    }

    @Override
    public void inputConfirm(String data, String source) {
        WalletInfo walletInfo = new WalletInfo(WalletManager.getCurrentKeyStore());
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            Boolean b = walletInfo.verifyPassword(data);
            emitter.onNext(b);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        mLoadingAndErrorView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Web3Api.sendERC20Transaction(data,mAssetsModel.getToken(),mGasPrice,mGasLimit,mToAddress,mAmount,new Web3jSubscriber<TransactionReceipt>(){
                                @Override
                                public void onNext(TransactionReceipt o) {
                                    super.onNext(o);
                                    BigInteger blockNumber = o.getBlockNumber();
                                    Log.e("LYW", "onNext: 交易成功" +blockNumber );
                                    mLoadingAndErrorView.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                    mLoadingAndErrorView.setVisibility(View.GONE);
                                    ToastUtils.showShortToast("交易失败");
                                }
                            });
                        } else {
                            mLoadingAndErrorView.setVisibility(View.GONE);
                            ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @Override
    public void inputGasConfirm(String gasPrice, String gasLimit) {
        mGasPrice = gasPrice;
        mGasLimit = gasLimit;
        setGasAmount();
    }
}
