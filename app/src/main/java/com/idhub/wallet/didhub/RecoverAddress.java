package com.idhub.wallet.didhub;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.address.EthereumAddressCreator;
import com.idhub.wallet.didhub.crypto.Crypto;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.DateUtil;
import com.idhub.wallet.didhub.util.MnemonicUtil;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.List;
import java.util.UUID;

public class RecoverAddress {
    private final String path = BIP44Util.ETHEREUM_PATH;
    private String address;

    public RecoverAddress(List<String> mnemonicCodes) {
        MnemonicUtil.validateMnemonics(mnemonicCodes);
        DeterministicSeed seed = new DeterministicSeed(mnemonicCodes, null, "", 0L);
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
        List<ChildNumber> zeroPath = BIP44Util.generatePath(path);
        byte[] prvKeyBytes = keyChain.getKeyByPath(zeroPath, true).getPrivKeyBytes();
        this.address = new EthereumAddressCreator().fromPrivateKey(prvKeyBytes);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
