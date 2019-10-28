package com.idhub.wallet.net;


import android.text.TextUtils;

import com.idhub.base.App;
import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.sharepreference.WalletTransactionSharpreference;
import com.idhub.base.node.WalletNodeSelectedObservable;
import com.idhub.wallet.contract.EIP20Interface;
import com.idhub.wallet.contract.ERC1400;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.base.node.AssetsModel;
import com.idhub.wallet.net.parameter.ERC1400TransactionParam;
import com.idhub.wallet.net.parameter.EthTransactionParam;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.wallet.utils.LogUtils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import io.api.etherscan.model.EthNetwork;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import com.idhub.magic.clientlib.etherscan.Etherscan;


public class Web3Api {
    public static Web3j mWeb3j;
    public static String sNode;
    public static EthNetwork ethNetwork;

    static {
        initWeb3();
        WalletNodeSelectedObservable.getInstance().addObserver((o, arg) -> initWeb3());
    }

    private static void initWeb3() {
        sNode = WalletNoteSharedPreferences.getInstance().getNode();
        if (sNode.equals(WalletNodeManager.ROPSTEN)) {
            ethNetwork = EthNetwork.ROPSTEN;
        } else {
            ethNetwork = EthNetwork.MAINNET;
        }
        ((Etherscan) Etherscan.getInstance()).setCurrentApi(ethNetwork);
        mWeb3j = Web3j.build(new HttpService(sNode));
    }

    public static void searchERC1400Controllers(String contractAddress, DisposableSubscriber<List> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        Credentials credentials = Credentials.create("0");
        ERC1400 erc1400 = ERC1400.load(contractAddress, mWeb3j, credentials, defaultGasProvider);
        erc1400.controllers().flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void searchERC1400Balance(String contractAddress, byte[] partition, String address, DisposableSubscriber<BigInteger> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        Credentials credentials = Credentials.create("0");
        ERC1400 erc1400 = ERC1400.load(contractAddress, mWeb3j, credentials, defaultGasProvider);
        erc1400.balanceOfByPartition(partition, address).flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void searchERC1400Partition(String contractAddress, DisposableSubscriber<List> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        Credentials credentials = Credentials.create("0");
        ERC1400 erc1400 = ERC1400.load(contractAddress, mWeb3j, credentials, defaultGasProvider);
        erc1400.totalPartitions().flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void searchERC1400TotalSupply(String contractAddress, DisposableSubscriber<BigInteger> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        Credentials credentials = Credentials.create("0");
        ERC1400 erc1400 = ERC1400.load(contractAddress, mWeb3j, credentials, defaultGasProvider);
        erc1400.totalSupply().flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void searchERC1400ContractAddress(String contractAddress, DisposableObserver<AssetsModel> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        Credentials credentials = Credentials.create("0");
        Observable.create((ObservableOnSubscribe<AssetsModel>) emitter -> {
            ERC1400 erc1400 = ERC1400.load(contractAddress, mWeb3j, credentials, defaultGasProvider);
            List list = erc1400.totalPartitions().send();
            String name = erc1400.name().send();
            BigInteger decimal = erc1400.decimals().send();
            String symbol = erc1400.symbol().send();
            if (TextUtils.isEmpty(symbol)) {
                emitter.onError(new Throwable(App.getInstance().getString(R.string.wallet_assets_unkonw)));
                return;
            }
            String node = WalletNoteSharedPreferences.getInstance().getNode();
            AssetsModelDbManager assetsModelDbManager = new AssetsModelDbManager();
            AssetsModel assetsModel = new AssetsModel();
            assetsModel.setType(AssetsDefaultType.ERC1400);
            assetsModel.setName(name);
            assetsModel.setDecimals(decimal.toString());
            assetsModel.setSymbol(symbol);
            if (WalletNodeManager.ROPSTEN.equals(node)) {
                assetsModel.setRopstenContractAddress(contractAddress);
            } else if (WalletNodeManager.MAINNET.equals(node)) {
                assetsModel.setMainContractAddress(contractAddress);
            }
            assetsModelDbManager.insertDatasync(assetsModel);
            emitter.onNext(assetsModel);
            emitter.onComplete();

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void searchERC20ContractAddressAssets(String contractAddress, DisposableObserver<AssetsModel> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        Credentials credentials = Credentials.create("0");
        Observable.create(new ObservableOnSubscribe<AssetsModel>() {
            @Override
            public void subscribe(ObservableEmitter<AssetsModel> emitter) throws Exception {
                EIP20Interface ierc20 = EIP20Interface.load(contractAddress, mWeb3j, credentials, defaultGasProvider);
                AssetsModelDbManager assetsModelDbManager = new AssetsModelDbManager();
                String node = WalletNoteSharedPreferences.getInstance().getNode();
                //过滤 存储对应ropsten或mainnet上的contractAddress
                String name = ierc20.name().send();
                BigInteger decimal = ierc20.decimals().send();
                String symbol = ierc20.symbol().send();
                if (TextUtils.isEmpty(symbol)) {
                    emitter.onError(new Throwable(App.getInstance().getString(R.string.wallet_assets_unkonw)));
                    return;
                }
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.setType(AssetsDefaultType.ERC20);
                assetsModel.setName(name);
                assetsModel.setDecimals(decimal.toString());
                assetsModel.setSymbol(symbol);
                if (WalletNodeManager.ROPSTEN.equals(node)) {
                    assetsModel.setRopstenContractAddress(contractAddress);
                } else if (WalletNodeManager.MAINNET.equals(node)) {
                    assetsModel.setMainContractAddress(contractAddress);
                }
                assetsModelDbManager.insertDatasync(assetsModel);
                emitter.onNext(assetsModel);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public static void searchBalance(String address, String token, DisposableSubscriber<BigInteger> observer) {
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
        Credentials credentials = Credentials.create("0");
        EIP20Interface ierc20 = EIP20Interface.load(token, mWeb3j, credentials, defaultGasProvider);
        ierc20.balanceOf(address).flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void searchBalance(String address, DisposableSubscriber<EthGetBalance> observer) {
        mWeb3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .flowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void sendETHTransaction(EthTransactionParam param, DisposableObserver<EthSendTransaction> observer) {
        Observable.create((ObservableOnSubscribe<EthSendTransaction>) emitter -> {
            EthGetTransactionCount send = mWeb3j.ethGetTransactionCount(param.fromAddress, DefaultBlockParameterName.LATEST).send();//获取noce
            BigInteger transactionCount = send.getTransactionCount();
            BigInteger value = Convert.toWei(param.value, Convert.Unit.ETHER).toBigInteger();
            RawTransaction etherTransaction = RawTransaction.createEtherTransaction(transactionCount, new BigInteger(param.gasPrice), new BigInteger(param.gasLimit), param.toAddress, value);
            byte[] signedMessage = TransactionEncoder.signMessage(etherTransaction, getCredentials(param.password));
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = mWeb3j.ethSendRawTransaction(hexValue).send();
            emitter.onNext(ethSendTransaction);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void getGasPrice() {
        //交易默认请求。保存在sharepreference。请求成功更新sharepreference。
        Observable.create((ObservableOnSubscribe<EthGasPrice>) emitter -> {
            EthGasPrice ethGasPrice = mWeb3j.ethGasPrice().send();
            emitter.onNext(ethGasPrice);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<EthGasPrice>() {
            @Override
            public void onNext(EthGasPrice ethGasPrice) {
                BigInteger gasPrice = ethGasPrice.getGasPrice();
                WalletTransactionSharpreference.getInstance().setGasPrice(String.valueOf(gasPrice));
                LogUtils.e("did", "gasPrice  " + String.valueOf(gasPrice));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void sendERC20Transaction(String password, String decimals, String contractAddress, String gasPrice, String gasLimit, String toAddress, String value, DisposableSubscriber<TransactionReceipt> web3jSubscriber) {
        Credentials credentials = getCredentials(password);
        StaticGasProvider staticGasProvider = new StaticGasProvider(new BigInteger(gasPrice), new BigInteger(gasLimit));
        EIP20Interface ierc20 = EIP20Interface.load(contractAddress, mWeb3j, credentials, staticGasProvider);
        BigDecimal value1 = NumericUtil.valueFormatByDecimal(value, Integer.valueOf(decimals));
        ierc20.transfer(toAddress, value1.toBigInteger()).flowable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(web3jSubscriber);
    }

    public static void sendERC1400Transaction(ERC1400TransactionParam param, DisposableObserver<ERC1400.TransferByPartitionEventResponse> web3jSubscriber) {
        Observable.create((ObservableOnSubscribe<ERC1400.TransferByPartitionEventResponse>) emitter -> {
            Credentials credentials = getCredentials(param.password);
            StaticGasProvider staticGasProvider = new StaticGasProvider(new BigInteger(param.gasPrice), new BigInteger(param.gasLimit));
            ERC1400 erc1400 = ERC1400.load(param.contratAddress, mWeb3j, credentials, staticGasProvider);
            BigDecimal decimal = NumericUtil.valueFormatByDecimal(param.value, Integer.valueOf(param.decimals));

            Tuple3<byte[], byte[], byte[]> send = erc1400.canTransferByPartition(param.paratition, param.fromAddress, param.toAddress, decimal.toBigInteger(), param.data).send();
            byte[] value1 = send.getValue1();
            String s = Numeric.toHexString(value1);
            //判断
            if ("0xa2".equals(s)) {
                //交易
                TransactionReceipt transactionReceipt = erc1400.transferByPartition(param.paratition, param.toAddress, decimal.toBigInteger(), param.data).send();
                List<ERC1400.TransferByPartitionEventResponse> transferByPartitionEvents = erc1400.getTransferByPartitionEvents(transactionReceipt);
                ERC1400.TransferByPartitionEventResponse transferByPartitionEventResponse = transferByPartitionEvents.get(0);
                emitter.onNext(transferByPartitionEventResponse);
                emitter.onComplete();
            } else {
                emitter.onError(new Throwable(App.getInstance().getString(R.string.wallet_no_transaction) + s));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(web3jSubscriber);
    }

    static Credentials getCredentials(String password) {
        WalletInfo walletInfo = new WalletInfo(WalletManager.getCurrentKeyStore());
        String privateKey = walletInfo.exportPrivateKey(password);
        return Credentials.create(privateKey);
    }

}
