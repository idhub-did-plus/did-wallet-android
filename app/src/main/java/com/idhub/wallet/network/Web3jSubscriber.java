package com.idhub.wallet.network;

import com.idhub.wallet.network.exception.NetworkException;
import com.idhub.wallet.utils.LogUtils;

import io.reactivex.subscribers.DisposableSubscriber;

public class Web3jSubscriber<T> extends DisposableSubscriber<T> {
    @Override
    public void onNext(T o) {

    }

    @Override
    public void onError(Throwable t) {
        NetworkException networkException = new NetworkException(t);
        String message = networkException.getMessage();
        LogUtils.e("did", message);
    }

    @Override
    public void onComplete() {

    }
}
