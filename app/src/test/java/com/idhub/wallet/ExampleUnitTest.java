package com.idhub.wallet;


import android.util.Log;

import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.net.IDHubCredentialProvider;

import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.interfaces.ExceptionListener;
import wallet.idhub.com.clientlib.interfaces.ResultListener;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        try {
            Credentials credentials = Credentials.create("0");
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
            IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
            BigInteger big = ApiFactory.getIdentityChainLocal().getEINSync(NumericUtil.prependHexPrefix("f6de4a68a56ed1e858caf089666c8a4e396dedf1"));
            System.out.println(big.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}