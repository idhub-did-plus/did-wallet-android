package com.idhub.wallet.didhub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.keystore.EncMnemonicKeystore;
import com.idhub.wallet.didhub.model.DidHubExportKeystoreIgnore;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.MnemonicAndPath;
import com.idhub.wallet.didhub.model.TokenException;
import com.idhub.wallet.didhub.util.NumericUtil;

public class WalletInfo {
    private DidHubKeyStore keystore;

    public WalletInfo(DidHubKeyStore keyStore) {
        this.keystore = keyStore;
    }

    public DidHubKeyStore getKeyStore() {
        return keystore;
    }

    public String getId() {
        return this.keystore.getId();
    }

    public String getAddress() {
        return this.keystore.getAddress();
    }

    public MnemonicAndPath exportMnemonic(String password) {
        if (keystore instanceof EncMnemonicKeystore) {
            EncMnemonicKeystore encMnemonicKeystore = (EncMnemonicKeystore) keystore;
            String mnemonic = encMnemonicKeystore.decryptMnemonic(password);
            String path = encMnemonicKeystore.getMnemonicPath();
            return new MnemonicAndPath(mnemonic, path);
        }
        return null;
    }

    String exportKeystore(String password) {
        if (!keystore.verifyPassword(password)) {
            throw new TokenException(Messages.WALLET_INVALID_PASSWORD);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixIn(DidHubKeyStore.class, DidHubExportKeystoreIgnore.class);
            return mapper.writeValueAsString(keystore);
        } catch (Exception ex) {
            throw new TokenException(Messages.WALLET_INVALID, ex);
        }

    }

    public String exportPrivateKey(String password) {
        if (keystore instanceof EncMnemonicKeystore) {
            byte[] decrypted = keystore.decryptCiphertext(password);
            return NumericUtil.bytesToHex(decrypted);
        }
        throw new TokenException(Messages.ILLEGAL_OPERATION);
    }

    boolean verifyPassword(String password) {
        return keystore.verifyPassword(password);
    }

    boolean delete(String password) {
        return keystore.verifyPassword(password) && WalletManager.generateWalletFile(keystore.getId()).delete();
    }
}
