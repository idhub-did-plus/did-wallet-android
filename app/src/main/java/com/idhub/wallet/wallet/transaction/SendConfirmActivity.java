package com.idhub.wallet.wallet.transaction;

import android.content.Intent;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletTransactionSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.contract.ERC1400;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.net.parameter.ERC1400TransactionParam;
import com.idhub.wallet.net.parameter.EthTransactionParam;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

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
    private View mPartitionName;
    private TextView mPartitionView;
    private View mLineView;

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
            mSendAmountView.setText(mAmount + " " + mAssetsModel.getSymbol());
            mFromAddressView.setText(mAssetsModel.getAddress());
        }
        mGasPrice = WalletTransactionSharpreference.getInstance().getGasPrice();
        String type = mAssetsModel.getType();
        switch (type){
            case AssetsDefaultType.ERC20:
                mGasLimit = WalletTransactionSharpreference.getInstance().getERC20GasLimit();
                break;
            case AssetsDefaultType.ERC1400:
                mGasLimit = WalletTransactionSharpreference.getInstance().getSTGasLimit();
                break;
            case AssetsDefaultType.ETH_NAME:
                mGasLimit = WalletTransactionSharpreference.getInstance().getEthGasLimit();
                break;
        }
        setGasAmount();
        byte[] partition = mAssetsModel.partition;
        if (partition != null) {
            mPartitionName.setVisibility(View.VISIBLE);
            mPartitionView.setVisibility(View.VISIBLE);
            mLineView.setVisibility(View.VISIBLE);
            mPartitionView.setText(NumericUtil.prependHexPrefix(NumericUtil.bytesToHex(partition)));
        } else {
            mPartitionName.setVisibility(View.GONE);
            mPartitionView.setVisibility(View.GONE);
            mLineView.setVisibility(View.GONE);
        }
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
        mPartitionName = findViewById(R.id.partition_name);
        mPartitionView = findViewById(R.id.partition);
        mLineView = findViewById(R.id.line3);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }


    @Override
    public void onClick(View v) {
        if (v == mGasAmountView) {
            InputGasDialogFragment inputGasDialogFragment = InputGasDialogFragment.getInstance(mGasPrice, mGasLimit);
            inputGasDialogFragment.show(getSupportFragmentManager(), "input_gas_dialog_fragment");
        } else if (v == mConfirmView) {
            InputDialogFragment instance = InputDialogFragment.getInstance("send", getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            instance.show(getSupportFragmentManager(), "input_dialog_fragment");
            instance.setInputDialogFragmentListener(this);
        }
    }

    @Override
    public void inputConfirm(String data, String source) {
        WalletInfo walletInfo = new WalletInfo(WalletManager.getCurrentKeyStore());
        DisposableObserver<Boolean> observer = new DisposableObserver<Boolean>() {
            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    transaction(data);
                } else {
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
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
        };
        walletInfo.verifyPassword(data,observer);
    }

    @Override
    public void inputGasConfirm(String gasPrice, String gasLimit) {
        mGasPrice = gasPrice;
        mGasLimit = gasLimit;
        setGasAmount();
    }

    private void transaction(String data) {
        String type = mAssetsModel.getType();
        switch (type) {
            case AssetsDefaultType.ERC20:
                Web3Api.sendERC20Transaction(data,mAssetsModel.getDecimals(), WalletNodeManager.assetsGetContractAddressToNode(mAssetsModel),mGasPrice,mGasLimit,mToAddress,mAmount,new DisposableSubscriber<TransactionReceipt>(){

                    @Override
                    public void onNext(TransactionReceipt o) {
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                        MainActivity.startAction(SendConfirmActivity.this,"transaction");
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_fail) + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
                break;
            case AssetsDefaultType.ERC1400:
                byte[] bytes = new byte[]{0x0};
                ERC1400TransactionParam transactionParam = new ERC1400TransactionParam(data, WalletNodeManager.assetsGetContractAddressToNode(mAssetsModel), mGasPrice, mGasLimit, mAssetsModel.partition, mAssetsModel.getAddress(), mToAddress, mAmount, mAssetsModel.getDecimals(), bytes);
                Web3Api.sendERC1400Transaction(transactionParam, new DisposableObserver<ERC1400.TransferByPartitionEventResponse>() {
                    @Override
                    public void onNext(ERC1400.TransferByPartitionEventResponse transferByPartitionEventResponse) {
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                        MainActivity.startAction(SendConfirmActivity.this,"transaction");
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("LYW", "onError: " +e.getMessage());
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_fail) + e.getMessage());
                        mLoadingAndErrorView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }
                });
                break;
            case AssetsDefaultType.ETH_NAME:
                EthTransactionParam param = new EthTransactionParam(data, mAssetsModel.getAddress(), mToAddress, mAmount, mGasLimit, mGasPrice);
                Web3Api.sendETHTransaction(param, new DisposableObserver<EthSendTransaction>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onNext(EthSendTransaction ethSendTransaction) {
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                        MainActivity.startAction(SendConfirmActivity.this, "transaction");
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_fail) + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }
                });
                break;
        }

    }
}
