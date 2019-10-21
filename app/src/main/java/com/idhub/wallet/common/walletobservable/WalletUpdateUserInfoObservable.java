package com.idhub.wallet.common.walletobservable;

import java.util.Observable;

public class WalletUpdateUserInfoObservable extends Observable {

    private static WalletUpdateUserInfoObservable selectedObservable;

    private WalletUpdateUserInfoObservable() {
    }

    public synchronized static WalletUpdateUserInfoObservable getInstance(){
        if (selectedObservable == null) {
            selectedObservable = new WalletUpdateUserInfoObservable();
        }
        return selectedObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
