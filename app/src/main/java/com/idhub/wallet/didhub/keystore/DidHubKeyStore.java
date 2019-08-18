package com.idhub.wallet.didhub.keystore;


import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.idhub.wallet.common.sharepreference.WalletOtherSharpreference;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.address.EthereumAddressCreator;
import com.idhub.wallet.didhub.crypto.Crypto;
import com.idhub.wallet.didhub.crypto.EncPair;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.DateUtil;
import com.idhub.wallet.didhub.util.MnemonicUtil;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.List;
import java.util.UUID;


public class DidHubKeyStore extends WalletKeystore implements EncMnemonicKeystore {

    private static final int VERSION = 3;
    private EncPair encMnemonic;
    private String mnemonicPath;

    public DidHubKeyStore() {
    }

    public void setMnemonicPath(String mnemonicPath) {
        this.mnemonicPath = mnemonicPath;
    }

    public DidHubKeyStore(Wallet wallet, List<String> mnemonicCodes, String password, String path) {
        MnemonicUtil.validateMnemonics(mnemonicCodes);
        DeterministicSeed seed = new DeterministicSeed(mnemonicCodes, null, "", 0L);
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
        this.mnemonicPath = path;
        List<ChildNumber> zeroPath = BIP44Util.generatePath(path);
        byte[] prvKeyBytes = keyChain.getKeyByPath(zeroPath, true).getPrivKeyBytes();
        this.crypto = Crypto.createPBKDF2CryptoWithKDFCached(password, prvKeyBytes);
        this.encMnemonic = crypto.deriveEncPair(password, Joiner.on(" ").join(mnemonicCodes).getBytes());
        this.crypto.clearCachedDerivedKey();
        this.address = new EthereumAddressCreator().fromPrivateKey(prvKeyBytes);
        wallet.setTimestamp(DateUtil.getUTCTime());
        this.wallet = wallet;
        this.version = VERSION;
        this.id = Strings.isNullOrEmpty(id) ? UUID.randomUUID().toString() : id;
        WalletOtherSharpreference.getInstance().setSelectedId(id);
    }

    @Override
    public WalletKeystore changePassword(String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public EncPair getEncMnemonic() {
        return encMnemonic;
    }

    @Override
    public void setEncMnemonic(EncPair encMnemonic) {
        this.encMnemonic = encMnemonic;
    }

    @Override
    public String getMnemonicPath() {
        return mnemonicPath;
    }

}
