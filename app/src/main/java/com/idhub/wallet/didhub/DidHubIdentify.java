package com.idhub.wallet.didhub;

import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.MnemonicUtil;

import java.util.List;

public class DidHubIdentify {
    private static DidHubIdentify identify;
    public static DidHubMnemonicKeyStore mDidHubMnemonicKeyStore;

    public DidHubIdentify(Wallet wallet, List<String> mnemonicCodes, String password,boolean isSave) {
        mDidHubMnemonicKeyStore = new DidHubMnemonicKeyStore(wallet, mnemonicCodes, password, BIP44Util.ETHEREUM_PATH,"");
        if (isSave) {
            WalletManager.createWallet(mDidHubMnemonicKeyStore);
        }
        identify = this;
    }

    private static DidHubIdentify createIdentity(String name, String password, String passwordHit, List<String> mnemonicCodes,boolean isSave) {
        Wallet wallet = new Wallet();
        wallet.setName(name);
        wallet.setPasswordHint(passwordHit);
        DidHubIdentify identity = new DidHubIdentify(wallet, mnemonicCodes, password,isSave);
        identify = identity;
        return identity;
    }

    public static DidHubIdentify createIdentity(String name, String password, String passwordHit,boolean isSave) {
        List<String> mnemonicCodes = MnemonicUtil.randomMnemonicCodes();
        return createIdentity(name, password, passwordHit, mnemonicCodes,isSave);
    }

    public static DidHubIdentify createIdentity(String name, String password, String passwordHit) {
        List<String> mnemonicCodes = MnemonicUtil.randomMnemonicCodes();
        return createIdentity(name, password, passwordHit, mnemonicCodes,true);
    }


}
