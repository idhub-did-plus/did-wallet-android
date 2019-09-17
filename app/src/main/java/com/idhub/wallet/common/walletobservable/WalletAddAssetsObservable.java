package com.idhub.wallet.common.walletobservable;

import java.util.Observable;

public class WalletAddAssetsObservable extends Observable {

    private static WalletAddAssetsObservable selectedObservable;

    private WalletAddAssetsObservable() {
    }

    public synchronized static WalletAddAssetsObservable getInstance(){
        if (selectedObservable == null) {
            selectedObservable = new WalletAddAssetsObservable();
        }
        return selectedObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
