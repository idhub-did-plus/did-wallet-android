package com.idhub.wallet.didhub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.keystore.EncMnemonicKeystore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.DidHubExportKeystoreIgnore;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.MnemonicAndPath;
import com.idhub.wallet.didhub.model.TokenException;
import com.idhub.wallet.didhub.util.NumericUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WalletInfo {
    private WalletKeystore keystore;

    public WalletInfo(WalletKeystore keyStore) {
        this.keystore = keyStore;
    }

    public WalletKeystore getKeyStore() {
        return keystore;
    }

    public String getId() {
        return this.keystore.getId();
    }

    public String getAddress() {
        return NumericUtil.prependHexPrefix(this.keystore.getAddress());
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

    public String exportKeystore(String password) {
        if (!keystore.verifyPassword(password)) {
            throw new TokenException(Messages.WALLET_INVALID_PASSWORD);
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixIn(DidHubMnemonicKeyStore.class, DidHubExportKeystoreIgnore.class);
            return mapper.writeValueAsString(keystore);
        } catch (Exception ex) {
            throw new TokenException(Messages.WALLET_INVALID, ex);
        }

    }

    public String exportPrivateKey(String password) {
        if (keystore instanceof EncMnemonicKeystore || keystore instanceof DidHubKeyStore) {
            byte[] decrypted = keystore.decryptCiphertext(password);
            return NumericUtil.bytesToHex(decrypted);
        }
        throw new TokenException(Messages.ILLEGAL_OPERATION);
    }

    public void verifyPassword(String password, DisposableObserver<Boolean> observer) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            Boolean b = keystore.verifyPassword(password);
            emitter.onNext(b);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public boolean verifyPassword(String password) {
            return keystore.verifyPassword(password);
    }

    boolean delete(String password) {
        return keystore.verifyPassword(password) && WalletManager.generateWalletFile(keystore.getId()).delete();
    }
}
