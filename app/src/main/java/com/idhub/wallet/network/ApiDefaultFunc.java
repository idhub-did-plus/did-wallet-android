package com.idhub.wallet.network;

import retrofit2.Response;
import rx.functions.Func1;

/**
 * Created by testp11 on 2017/4/12.
 */
public class ApiDefaultFunc<T> implements Func1<Response<T>, ApiResultEntity<T>>{
    @Override
    public ApiResultEntity<T> call(Response<T> response) {
        return (ApiResultEntity<T>) response.body();
    }

}
