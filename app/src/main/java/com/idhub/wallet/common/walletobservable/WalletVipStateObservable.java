package com.idhub.wallet.common.walletobservable;

import java.util.Observable;

public class WalletVipStateObservable extends Observable {

    private static WalletVipStateObservable selectedObservable;

    private WalletVipStateObservable() {
    }

    public synchronized static WalletVipStateObservable getInstance(){
        if (selectedObservable == null) {
            selectedObservable = new WalletVipStateObservable();
        }
        return selectedObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
