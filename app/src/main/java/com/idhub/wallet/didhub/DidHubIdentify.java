package com.idhub.wallet.didhub;

import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.MnemonicUtil;

import java.util.ArrayList;
import java.util.List;

public class DidHubIdentify {
    private static DidHubIdentify identify;
    private final DidHubKeyStore mDidHubKeyStore;

    public static DidHubIdentify getCurrentIdentity() {
        synchronized (DidHubIdentify.class) {
            if (identify == null) {
                identify = tryLoadFromFile();
            }
            return identify;
        }
    }

    private DidHubIdentify(Wallet wallet, List<String> mnemonicCodes, String password) {
        mDidHubKeyStore = new DidHubKeyStore(wallet, mnemonicCodes, password, BIP44Util.ETHEREUM_PATH);
        WalletManager.createWallet(mDidHubKeyStore);
        identify = this;
    }

    public static DidHubIdentify createIdentity(String name, String password, String passwordHit) {

    List<String> mnemonicCodes = MnemonicUtil.randomMnemonicCodes();
//        List<String> mnemonicCodes = new ArrayList<>();
//        mnemonicCodes.add("promote");
//        mnemonicCodes.add("coach");
//        mnemonicCodes.add("trophy");
//        mnemonicCodes.add("term");
//        mnemonicCodes.add("camera");
//        mnemonicCodes.add("bean");
//        mnemonicCodes.add("rotate");
//        mnemonicCodes.add("project");
//        mnemonicCodes.add("wave");
//        mnemonicCodes.add("gentle");
//        mnemonicCodes.add("amused");
//        mnemonicCodes.add("prefer");

        Wallet wallet = new Wallet();
        wallet.setName(name);
        wallet.setPasswordHint(passwordHit);
        DidHubIdentify identity = new DidHubIdentify(wallet, mnemonicCodes, password);
        identify = identity;
        return identity;
    }

    private static DidHubIdentify tryLoadFromFile() {
//        try {
//            File file = new File(WalletManager.getDefaultKeyDirectory(), FILE_NAME);
//            ObjectMapper mapper = new ObjectMapper()
//                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
//            DidHubKeyStore keystore = mapper.readValue(file, DidHubKeyStore.class);
//            return new DidHubIdentify(keystore);
//        } catch (IOException ignored) {
            return null;
//        }
    }

    public DidHubKeyStore getKeyStore(){
        return mDidHubKeyStore;
    }
}
