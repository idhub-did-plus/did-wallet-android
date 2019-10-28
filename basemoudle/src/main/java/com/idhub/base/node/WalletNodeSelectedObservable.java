package com.idhub.base.node;

import java.util.Observable;

public class WalletNodeSelectedObservable extends Observable {

    private static WalletNodeSelectedObservable selectedObservable;

    private WalletNodeSelectedObservable() {
    }

    public synchronized static WalletNodeSelectedObservable getInstance(){
        if (selectedObservable == null) {
            selectedObservable = new WalletNodeSelectedObservable();
        }
        return selectedObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
