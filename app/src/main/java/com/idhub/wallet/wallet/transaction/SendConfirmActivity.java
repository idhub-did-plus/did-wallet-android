package com.idhub.wallet.wallet.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.main.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletTransactionSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.contract.ERC1400;
import com.idhub.wallet.dapp.entity.Transaction;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;
import com.idhub.wallet.net.parameter.ERC1400TransactionParam;
import com.idhub.wallet.net.parameter.EthTransactionParam;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Keys;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class SendConfirmActivity extends BaseActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener, InputGasDialogFragment.InputGasDialogFragmentListener {

    private TextView mSendAmountView;
    private TextView mFromAddressView;
    private TextView mToAddressView;
    private TextView mGasAmountView;

    private View mConfirmView;
    private LoadingAndErrorView mLoadingAndErrorView;
    private String mGasLimit;
    private String mGasPrice;

    private View mPartitionName;
    private TextView mPartitionView;
    private View mLineView;


    private TransactionParam mTransactionParam;
    private TransactionDialog dialog;

    private View mTokenIdBottomLineView;
    private View mTokenIdNameView;
    private TextView mTokenIdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_send_confirm);
        Parcelable data = getIntent().getParcelableExtra("data");
        if (!(data instanceof TransactionParam)) {
            finish();
            return;
        }
        mTransactionParam = (TransactionParam) data;
        if (TextUtils.isEmpty(mTransactionParam.type)) {
            finish();
            return;
        }
        initView();
        initData();
    }

    public static void startAction(Activity activity, TransactionParam transactionParam, int requestCode) {
        Intent intent = new Intent(activity, SendConfirmActivity.class);
        intent.putExtra("data", transactionParam);
        activity.startActivityForResult(intent, requestCode);
    }

    private void initData() {
        mToAddressView.setText(mTransactionParam.toAddress);
        AssetsModel assetsModel = mTransactionParam.assetsModel;
        if (assetsModel != null) {
            String value = mTransactionParam.value;
            if (mTransactionParam.type.equals(TransactionTokenType.WEB3_DAPP)) {
                BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(value));
                String decimals = assetsModel.getDecimals();
                BigDecimal divisor = new BigDecimal(Math.pow(10, Double.valueOf(decimals)));
                BigDecimal divide = bigDecimal.divide(divisor);
                value = divide.toString();
            }
            mSendAmountView.setText(value + " " + assetsModel.getSymbol());
            mFromAddressView.setText(Keys.toChecksumAddress(assetsModel.getAddress()));
            byte[] partition = assetsModel.partition;
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
        mGasPrice = WalletTransactionSharpreference.getInstance().getGasPrice();
        switch (mTransactionParam.type) {
            case TransactionTokenType.ERC20:
                mGasLimit = WalletTransactionSharpreference.getInstance().getERC20GasLimit();
                break;
            case TransactionTokenType.ERC1400:
                mGasLimit = WalletTransactionSharpreference.getInstance().getSTGasLimit();
                break;
            case TransactionTokenType.ETH_NAME:
                mGasLimit = WalletTransactionSharpreference.getInstance().getEthGasLimit();
                break;
            case TransactionTokenType.WEB3_DAPP:
                Transaction transaction = mTransactionParam.transaction;
                mGasLimit = String.valueOf(transaction.gasLimit);
                mGasPrice = transaction.gasPrice.toString();
                break;
            case TransactionTokenType.ERC721:
                AssetsCollectionItem collectionItem = mTransactionParam.assetsCollectionItem;
                if (collectionItem != null) {
                    mFromAddressView.setText(Keys.toChecksumAddress(WalletManager.getCurrentKeyStore().getAddress()));
                    mSendAmountView.setText(collectionItem.name);
                    mTokenIdBottomLineView.setVisibility(View.VISIBLE);
                    mTokenIdNameView.setVisibility(View.VISIBLE);
                    mTokenIdView.setVisibility(View.VISIBLE);
                    mTokenIdView.setText(collectionItem.token_id);
                } else {
                    finish();
                    return;
                }
                mGasLimit = WalletTransactionSharpreference.getInstance().getERC721GasLimit();
                break;
        }
        setGasAmount();

    }

    private void setGasAmount() {
        BigInteger bigIntegerGasLimit = new BigInteger(mGasLimit);
        BigInteger bigIntegerGasPrice = new BigInteger(mGasPrice);
        BigInteger gasEthAmount = bigIntegerGasLimit.multiply(bigIntegerGasPrice);
        String s = NumericUtil.ethBigIntegerToNumberViewPointAfterEight(gasEthAmount, String.valueOf(Math.pow(10, 18)));
        mGasAmountView.setText(s + " ETH");
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


        mTokenIdBottomLineView = findViewById(R.id.line6);
        mTokenIdNameView = findViewById(R.id.token_id_name);
        mTokenIdView = findViewById(R.id.token_id);

    }


    @Override
    public void onClick(View v) {
        if (v == mGasAmountView) {
            InputGasDialogFragment inputGasDialogFragment = InputGasDialogFragment.getInstance(mGasPrice, mGasLimit);
            inputGasDialogFragment.show(getSupportFragmentManager(), "input_gas_dialog_fragment");
        } else if (v == mConfirmView) {
            InputDialogFragment instance = InputDialogFragment.getInstance("send", getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
        walletInfo.verifyPassword(data, observer);
    }

    @Override
    public void inputGasConfirm(String gasPrice, String gasLimit) {
        mGasPrice = gasPrice;
        mGasLimit = gasLimit;
        setGasAmount();
    }

    private void transaction(String data) {
        String type = mTransactionParam.type;
        AssetsModel assetsModel = mTransactionParam.assetsModel;
        AssetsCollectionItem assetsCollectionItem = mTransactionParam.assetsCollectionItem;
        String toAddress = mTransactionParam.toAddress;
        String value = mTransactionParam.value;
        switch (type) {
            case TransactionTokenType.ERC20:
                Web3Api.sendERC20Transaction(data, assetsModel.getDecimals(),assetsModel.getCurrentContractAddress(), mGasPrice, mGasLimit, toAddress, value, new DisposableSubscriber<TransactionReceipt>() {

                    @Override
                    public void onNext(TransactionReceipt o) {
                        Log.e("LYW", "onNext: " + o.getTransactionHash());
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                        MainActivity.startAction(SendConfirmActivity.this, "transaction");
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                        showErrorDoalog(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
                break;
            case TransactionTokenType.ERC1400:
                byte[] bytes = new byte[]{0x0};
                ERC1400TransactionParam transactionParam = new ERC1400TransactionParam(data, assetsModel.getCurrentContractAddress(), mGasPrice, mGasLimit, assetsModel.partition, assetsModel.getAddress(), toAddress, value, assetsModel.getDecimals(), bytes);
                Web3Api.sendERC1400Transaction(transactionParam, new DisposableObserver<ERC1400.TransferByPartitionEventResponse>() {
                    @Override
                    public void onNext(ERC1400.TransferByPartitionEventResponse transferByPartitionEventResponse) {
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                        MainActivity.startAction(SendConfirmActivity.this, "transaction");
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorDoalog(e.getMessage());
                        mLoadingAndErrorView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }
                });
                break;
            case TransactionTokenType.ETH_NAME:
                EthTransactionParam param = new EthTransactionParam(data, assetsModel.getAddress(), toAddress, value, mGasLimit, mGasPrice);
                Web3Api.sendETHTransaction(param, new DisposableObserver<EthSendTransaction>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onNext(EthSendTransaction ethSendTransaction) {
                        boolean b = ethSendTransaction.hasError();
                        mLoadingAndErrorView.setVisibility(View.GONE);
                        if (!b) {
                            ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                            MainActivity.startAction(SendConfirmActivity.this, "transaction");
                        } else {
                            showErrorDoalog(ethSendTransaction.getError().getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                        showErrorDoalog(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }
                });
                break;
            case TransactionTokenType.WEB3_DAPP:
                signWeb3DAppTransaction(data);
                break;
            case TransactionTokenType.ERC721:
                Web3Api.sendERC721Transaction(data, assetsCollectionItem.asset_contract.address,mFromAddressView.getText().toString(),toAddress, mGasPrice, mGasLimit, assetsCollectionItem.token_id, new DisposableSubscriber<TransactionReceipt>() {
                    @Override
                    public void onNext(TransactionReceipt transactionReceipt) {
                        String status = transactionReceipt.getStatus();
                        if ("0x1".equals(status)) {
                            //成功
                            ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                            MainActivity.startAction(SendConfirmActivity.this, "transaction");
                        } else {
                            String transactionHash = transactionReceipt.getTransactionHash();
                            Log.e("LYW", "onNext: " + transactionHash );
                            ToastUtils.showShortToast(getString(R.string.wallet_transaction_fail));
                        }
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        ToastUtils.showShortToast(t.getMessage());
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
        }

    }

    private void signWeb3DAppTransaction(String password) {
        BigInteger addr = Numeric.toBigInt(mTransactionParam.transaction.recipient.toString());

        //constructor
        if (addr.equals(BigInteger.ZERO)) {
            Web3Api.sendTransactionWithSig(password, mTransactionParam.assetsModel.getAddress(), mGasPrice, mGasLimit, mTransactionParam.transaction.payload, new DisposableObserver<EthSendTransaction>() {
                @Override
                public void onNext(EthSendTransaction ethSendTransaction) {
                    boolean b = ethSendTransaction.hasError();
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    if (!b) {
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                        Intent intent = new Intent();
                        intent.putExtra("transaction", mTransactionParam.transaction);
                        intent.putExtra("hashData", ethSendTransaction.getTransactionHash());
                        setResult(RESULT_OK,intent);
                        finish();
                    } else {
                        showErrorDoalog(ethSendTransaction.getError().getMessage());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    showErrorDoalog(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            Web3Api.sendTransactionWithSig(password, mTransactionParam.assetsModel.getAddress(),mTransactionParam.transaction.recipient.toString(),new BigInteger(mTransactionParam.value), mGasPrice, mGasLimit, mTransactionParam.transaction.payload, new DisposableObserver<EthSendTransaction>() {
                @Override
                public void onNext(EthSendTransaction ethSendTransaction) {
                    boolean b = ethSendTransaction.hasError();
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    if (!b) {
                        ToastUtils.showShortToast(getString(R.string.wallet_transaction_success));
                        Intent intent = new Intent();
                        intent.putExtra("transaction", mTransactionParam.transaction);
                        intent.putExtra("hashData", ethSendTransaction.getTransactionHash());
                        setResult(RESULT_OK,intent);
                        finish();
                    } else {
                     showErrorDoalog(ethSendTransaction.getError().getMessage());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    showErrorDoalog(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    private void showErrorDoalog(String message) {
        hideDialog();
        dialog = new TransactionDialog(this,message);
        dialog.show();
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
