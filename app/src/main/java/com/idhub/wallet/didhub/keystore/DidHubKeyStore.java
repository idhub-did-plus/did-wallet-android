package com.idhub.wallet.didhub.keystore;

import com.google.common.base.Strings;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.address.EthereumAddressCreator;
import com.idhub.wallet.didhub.crypto.Crypto;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.DateUtil;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.didhub.util.PrivateKeyValidator;

import java.util.UUID;

public class DidHubKeyStore extends WalletKeystore {
    public static final int VERSION = 3;

    public DidHubKeyStore() {
    }

    public static DidHubKeyStore create(Wallet wallet, String password, String prvKeyHex) {
        return new DidHubKeyStore(wallet, password, prvKeyHex,"");
    }

    public DidHubKeyStore(Wallet wallet, String password, String privateKeyHex,String id) {
        byte[] prvKeyBytes = NumericUtil.hexToBytes(privateKeyHex);
        new PrivateKeyValidator(privateKeyHex).validate();
        this.address = new EthereumAddressCreator().fromPrivateKey(prvKeyBytes);
        this.crypto = Crypto.createPBKDF2Crypto(password, prvKeyBytes);
        wallet.setTimestamp(DateUtil.getUTCTime());
        this.wallet = wallet;
        this.version = VERSION;
        this.id = Strings.isNullOrEmpty(id) ? UUID.randomUUID().toString() : id;
        WalletOtherInfoSharpreference.getInstance().setSelectedId(this.id);
    }

    @Override
    public WalletKeystore changePassword(String oldPassword, String newPassword) {
        byte[] decrypted = this.crypto.decrypt(oldPassword);
        String prvKeyHex = NumericUtil.bytesToHex(decrypted);
        return new DidHubKeyStore(wallet, newPassword, prvKeyHex,this.id);
    }
}
