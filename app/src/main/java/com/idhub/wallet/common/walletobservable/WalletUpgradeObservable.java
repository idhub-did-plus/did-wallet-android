package com.idhub.wallet.common.walletobservable;

import java.util.Observable;

public class WalletUpgradeObservable extends Observable {

    private static WalletUpgradeObservable selectedObservable;

    private WalletUpgradeObservable() {
    }

    public synchronized static WalletUpgradeObservable getInstance(){
        if (selectedObservable == null) {
            selectedObservable = new WalletUpgradeObservable();
        }
        return selectedObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
