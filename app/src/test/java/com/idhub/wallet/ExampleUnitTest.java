package com.idhub.wallet;

import android.util.Log;

import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.network.C;
import com.idhub.wallet.network.DIDApiUseCase;
import com.idhub.wallet.network.Web3Api;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import io.api.etherscan.core.impl.EtherScanApi;
import io.api.etherscan.model.EthNetwork;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws ExecutionException, InterruptedException {
//        assertEquals(4, 2 + 2);
//        BigInteger bigInteger = new BigInteger("152000010000000000000");
//        float v = NumericUtil.bigInteger18ToFloat(bigInteger);
//        System.out.println(v);
//        Web3j web3j = Web3j.build(new HttpService(C.API_BASE));
//        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
//        System.out.println(ethGasPrice.getGasPrice());
//        BigDecimal bigDecimal = new BigDecimal(ethGasPrice.getGasPrice());
//        BigDecimal divide = bigDecimal.divide(new BigDecimal("1000000000000000000"));
//        System.out.println(divide);

//        try {
//            Web3Api.getNoce("","");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String gasLimit = "72000";
//        String gasPrice = "1062856930";
//        BigInteger bigIntegerGasLimit = new BigInteger(gasLimit);
//        System.out.println(bigIntegerGasLimit);
//        BigInteger bigIntegerGasPrice = new BigInteger(gasPrice);
//        System.out.println(bigIntegerGasPrice);
//
//        BigInteger gasEthAmount = bigIntegerGasLimit.multiply(bigIntegerGasPrice);
//        System.out.println(gasEthAmount);
//
//        String s = NumericUtil.ethBigIntegerToNumberView(gasEthAmount);
//        System.out.println(s);
//        EtherScanApi apiRopsten = new EtherScanApi(EthNetwork.ROPSTEN);
//        apiRopsten.account().
    }
}