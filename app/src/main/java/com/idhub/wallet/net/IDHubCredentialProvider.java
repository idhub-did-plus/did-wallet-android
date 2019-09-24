package com.idhub.wallet.net;

import com.idhub.wallet.common.sharepreference.BlockNumbersSharedPreferences;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import com.idhub.magic.clientlib.interfaces.CredentialProvider;


public class IDHubCredentialProvider implements CredentialProvider {

    //    private static Web3j mWeb3j = Web3j.build(new HttpService(C.API_BASE));


    private static Credentials client;
    private static String sRecoverAddress;
    private static String sDefaultAddress;

    public static void setDefaultCredentials(String privateKey) {
        client = Credentials.create(privateKey);
    }

    public static void setsDefaultAddress(String defaultAddress) {
        sDefaultAddress = defaultAddress;
    }

    public static void setRecoverAddress(String recoverAddress) {
        sRecoverAddress = recoverAddress;
    }

    @Override
    public Credentials getByAddress(String s) {

        return client;
    }

    @Override
    public Credentials getDefaultCredentials() {
        //associateAddress
        return client;
    }

    @Override
    public String getRecoverAddress() {
        return sRecoverAddress;
    }


    public String getDefaultAddress() {
        return sDefaultAddress;
    }


    @Override
    public Web3j web3j() {
        return Web3Api.mWeb3j;
    }

    @Override
    public long getLastEndBlockNumber() {
        return BlockNumbersSharedPreferences.getInstance().getBlockNumber();
    }

    @Override
    public void storeLastEndBlockNumber(long l) {
        BlockNumbersSharedPreferences.getInstance().setBlockNumber(l);
    }
}
