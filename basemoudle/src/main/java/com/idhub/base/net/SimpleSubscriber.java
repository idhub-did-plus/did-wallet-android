package com.idhub.base.net;



import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;


public abstract class SimpleSubscriber<T> extends ResourceSubscriber<T> {
//    private BaseView baseView;
//    private boolean showLoading = false;
//
//
//    public SimpleSubscriber(BaseView baseView) {
//        this.baseView = baseView;
//    }
//
//    public SimpleSubscriber(BaseView baseView, boolean showLoading) {
//        this.baseView = baseView;
//        this.showLoading = showLoading;
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (null != baseView && showLoading) {
//            baseView.showLoading();
//        }
//    }
//
//    @Override
//    public void onError(Throwable t) {
//        LogUtils.E("loginResultHttpResult.Throwable"+t.getMessage());
//        if (null == baseView) {
//            return;
//        }
//        baseView.hideLoading();
//        if (t instanceof UnknownHostException) {
//            baseView.handError(HttpCode.NET_ERRRO);
//        } else if (t instanceof SocketTimeoutException) {
//            baseView.handError(HttpCode.NET_ERRRO);
//        } else if (t instanceof ConnectException) {
//            baseView.handError(HttpCode.NET_ERRRO);
//        } else if (t instanceof ApiException) {
//            ApiException apiException = (ApiException) t;
//            switch (apiException.getCode()) {
//                case HttpCode.NO_PARAMETER:
//                    baseView.handError(HttpCode.NO_PARAMETER);
//                    break;
//                case HttpCode.SERVER_ERR:
//                    baseView.handError(HttpCode.SERVER_ERR);
//                    break;
//                default:
//                    break;
//            }
//        } else {
//            baseView.handError(HttpCode.LOAD_FAILD);
//        }
//    }
//
//    @Override
//    public void onNext(T t) {
//        HttpResult resut = (HttpResult) t;
//        LogUtils.E("HttpResult) t).getResCode()="+((HttpResult) t).getStatus());
//        if(((HttpResult) t).getStatus() == HttpCode.TOKEN_INVALID){
//            ActivityPageManager.getInstance().finishAllActivity();
//            GrapelibApplication.getInstance().toLogin();
//        }
//    }
//
//
//
//    @Override
//    public void onComplete() {
//        baseView.hideLoading();
//    }

}
