package com.idhub.wallet.didhub.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idhub.wallet.didhub.crypto.EncPair;
import com.idhub.wallet.didhub.keystore.WalletKeystore;

public abstract class DidHubExportKeystoreIgnore {
    @JsonIgnore
    public abstract EncPair getEncMnemonic();

    @JsonIgnore
    public abstract WalletKeystore getWalletKeystore();

    @JsonIgnore
    public abstract String getMnemonicPath();

    @JsonIgnore
    public abstract String getEncXPub();

    @JsonIgnore
    public abstract int getMnemonicIndex();
}
