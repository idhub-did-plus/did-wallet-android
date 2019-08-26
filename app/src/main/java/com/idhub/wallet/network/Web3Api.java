package com.idhub.wallet.network;


import android.os.Environment;
import android.util.Log;

import com.idhub.wallet.common.sharepreference.WalletTransactionSharpreference;
import com.idhub.wallet.contract.EIP20Interface;
import com.idhub.wallet.didhub.DidHubIdentify;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.utils.LogUtils;

import org.web3j.contracts.token.ERC20Interface;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;


public class Web3Api {
    private static Web3j mWeb3j = Web3j.build(new HttpService(C.API_BASE));

    public static void searchBalance(String address, String token, DisposableSubscriber<BigInteger> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        BigInteger gasPrice = defaultGasProvider.getGasPrice();
        BigInteger gasLimit = defaultGasProvider.getGasLimit();
        Log.e("LYW", "searchBalance: " + gasPrice + "  gasLimit  " + gasLimit);
        Credentials credentials = Credentials.create("0");
        EIP20Interface ierc20 = EIP20Interface.load(token, mWeb3j, credentials, defaultGasProvider);
        ierc20.balanceOf(address).flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void searchBalance(String address, DisposableSubscriber<EthGetBalance> observer) {
//        DefaultBlockParameterNumber defaultBlockParameter = new DefaultBlockParameterNumber();
        mWeb3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .flowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getNoce(String address, String to) throws IOException {
        //获取noce
        EthGetTransactionCount send = mWeb3j.ethGetTransactionCount("0x4c000E507bE6663e264a1A21507a69Bfa5035D95", DefaultBlockParameterName.LATEST).send();
//        BigInteger gasPrice = new BigInteger(WalletTransactionSharpreference.getInstance().getGasPrice());
        BigInteger gasPrice = new BigInteger("1000000000");
        BigInteger transactionCount = send.getTransactionCount();
//        Log.e("LYW", "onNext:gasPrice " + gasPrice + " transactionCount " + transactionCount);
        System.out.println("onNext:gasPrice " + gasPrice + " transactionCount " + transactionCount);
        BigInteger value = Convert.toWei("0.1", Convert.Unit.ETHER).toBigInteger();

        Transaction etherTransaction1 = Transaction.createEtherTransaction("0x4c000E507bE6663e264a1A21507a69Bfa5035D95", transactionCount, gasPrice, null, "0x1B273c7B4e025877BCE40248F4f30Ca0641F7eCF", value);
        try {
            BigInteger amountUsed = mWeb3j.ethEstimateGas(etherTransaction1).send().getAmountUsed();
//            Log.e("LYW", "onNext:amountUsed " + amountUsed);
            System.out.println("onNext:amountUsed " + amountUsed);
            RawTransaction etherTransaction = RawTransaction.createEtherTransaction(transactionCount, gasPrice, amountUsed, "0x1B273c7B4e025877BCE40248F4f30Ca0641F7eCF", value);
//            byte[] signedMessage = TransactionEncoder.signMessage(etherTransaction, getCredentials());
//            String hexValue = Numeric.toHexString(signedMessage);
//
//            EthSendTransaction ethSendTransaction = mWeb3j.ethSendRawTransaction(hexValue).send();
//            Log.e("LYW", "onNext: " + ethSendTransaction.getTransactionHash());
//            System.out.println("onNext: " + ethSendTransaction.getTransactionHash());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getGasPrice(){
        //交易默认请求。保存在sharepreference。请求成功更新sharepreference。
        EthGasPrice ethGasPrice = null;
        try {
            ethGasPrice = mWeb3j.ethGasPrice().sendAsync().get();
            BigInteger gasPrice = ethGasPrice.getGasPrice();
            WalletTransactionSharpreference.getInstance().setGasPrice(String.valueOf(gasPrice));
            LogUtils.e("did", "gasPrice  " + String.valueOf(gasPrice));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static void getGasLimit() {

    }

    public static void sendTransaction(String address) {

    }

    public static void sendERC20Transaction(String password,String contractAddress,String gasPrice,String gasLimit,String toAddress,String value,Web3jSubscriber<TransactionReceipt> web3jSubscriber){
        Credentials credentials = getCredentials(password);
        StaticGasProvider staticGasProvider = new StaticGasProvider(new BigInteger(gasPrice),new BigInteger(gasLimit));
        EIP20Interface ierc20 = EIP20Interface.load(contractAddress, mWeb3j, credentials, staticGasProvider);
        BigInteger value1 = Convert.toWei(value, Convert.Unit.ETHER).toBigInteger();
        Log.e("did", "sendERC20Transaction: " + value1 );
        Log.e("did", "sendERC20Transaction: " + WalletManager.getAddress() );
        Log.e("did", "sendERC20Transaction: " + gasPrice );
        Log.e("did", "sendERC20Transaction: " + gasLimit );
        ierc20.transfer(toAddress, value1).flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(web3jSubscriber);
    }

     static Credentials getCredentials(String password) {
        WalletInfo walletInfo = new WalletInfo(WalletManager.getCurrentKeyStore());
        String privateKey = walletInfo.exportPrivateKey(password);
        return Credentials.create(privateKey);
    }
}
