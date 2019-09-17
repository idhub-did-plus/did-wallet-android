package com.idhub.wallet;

import android.util.Log;

import com.idhub.wallet.contract.ERC1400;

import org.junit.Test;

import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Web3j mWeb3j = Web3j.build(new HttpService("https://ropsten.infura.io"));
        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();

        Credentials credentials = Credentials.create("0");

        ERC1400 erc1400 = ERC1400.load("0x6cf0d301c00feab199757b616eae46f369c6e141", mWeb3j, credentials, defaultGasProvider);
        String send1 = erc1400.symbol().send();
        System.out.println(send1 +"11");
        List<String> list = erc1400.controllers().send();
        for (String o : list) {
            System.out.println(o);
        }
        List<byte[]> send = erc1400.totalPartitions().send();
        byte[] o = send.get(0);
        System.out.println(o.length);
    }
}