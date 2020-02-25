package com.idhub.base.net;

/**
 * Created by gary on 2018/12/28.
 */

public class ApiServiceFactory {


    public static <T> T createAPIService(String baseUrl,Class<T> clz) {
        return GpRetrofitManager.getInstance().getRetrofit(baseUrl).create(clz);
    }

}
