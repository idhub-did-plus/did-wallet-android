package com.idhub.wallet.network;

/**
 * Created by testp11 on 2017/4/12.
 */


import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 获取API数据的封装
 */
public class GetApiUseCase {

    public static <T> Observable<T> get(String url, final Class<T> clazz) {
        Observable<T> observable = ApiRequest.getInstance().getMxApiData(url, clazz)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    public static <T> Observable<T> post(String url, final Class<T> clazz) {
        Observable<T> observable = ApiRequest.getInstance().postMxApiData(url, clazz)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    //带参数的
    public static <T> Observable<T> get(String url, Map<String, String> params, final Class<T> clazz) {
        Observable<T> observable = ApiRequest.getInstance().getMxApiData(url, params, clazz)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    public static <T> Observable<T> post(String url, Map<String, String> params, final Class<T> clazz) {
        Observable<T> observable = ApiRequest.getInstance().postMxApiData(url, params, clazz)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }


}
