package com.idhub.base.node;

import java.util.Observable;

public class WalletChangeContractObservable extends Observable {

    private static WalletChangeContractObservable selectedObservable;

    private WalletChangeContractObservable() {
    }

    public synchronized static WalletChangeContractObservable getInstance(){
        if (selectedObservable == null) {
            selectedObservable = new WalletChangeContractObservable();
        }
        return selectedObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
