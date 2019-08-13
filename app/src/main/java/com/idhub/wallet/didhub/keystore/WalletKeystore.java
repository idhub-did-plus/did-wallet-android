package com.idhub.wallet.didhub.keystore;

import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.crypto.Crypto;


public abstract class WalletKeystore {
    Wallet wallet;
    String address;
    String id;
    int version;
    Crypto crypto;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public boolean verifyPassword(String password) {
        return getCrypto().verifyPassword(password);
    }

    public byte[] decryptCiphertext(String password) {
        return getCrypto().decrypt(password);
    }

    public abstract WalletKeystore changePassword(String oldPassword, String newPassword);

    @Override
    public String toString() {
        return "WalletKeystore{" +
                "wallet=" + wallet +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", version=" + version +
                ", crypto=" + crypto +
                '}';
    }
}
