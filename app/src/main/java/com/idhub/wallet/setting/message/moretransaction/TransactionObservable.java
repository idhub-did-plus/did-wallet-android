package com.idhub.wallet.setting.message.moretransaction;

import java.util.Observable;

public class TransactionObservable extends Observable {

    private static TransactionObservable transactionObservable;

    private TransactionObservable() {
    }

    public synchronized static TransactionObservable getInstance(){
        if (transactionObservable == null) {
            transactionObservable = new TransactionObservable();
        }
        return transactionObservable;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
