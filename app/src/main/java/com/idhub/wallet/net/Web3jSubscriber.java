package com.idhub.wallet.net;

import com.idhub.wallet.utils.LogUtils;

import io.reactivex.subscribers.DisposableSubscriber;

public class Web3jSubscriber<T> extends DisposableSubscriber<T> {
    @Override
    public void onNext(T o) {

    }

    @Override
    public void onError(Throwable t) {
        String message = t.getMessage();
        LogUtils.e("did", message);
    }

    @Override
    public void onComplete() {

    }
}
