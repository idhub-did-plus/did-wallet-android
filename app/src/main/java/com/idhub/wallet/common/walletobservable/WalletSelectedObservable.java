package com.idhub.wallet.common.walletobservable;

import java.util.Observable;

public class WalletSelectedObservable extends Observable {

    private static WalletSelectedObservable selectedObservable;

    private WalletSelectedObservable() {
    }

    public synchronized static WalletSelectedObservable getInstance(){
        if (selectedObservable == null) {
            selectedObservable = new WalletSelectedObservable();
        }
        return selectedObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
