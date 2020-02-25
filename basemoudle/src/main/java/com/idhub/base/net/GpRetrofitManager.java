package com.idhub.base.net;


import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Gary on 2019/3/23.
 */
public class GpRetrofitManager {

    private static final int DEFAULT_TIMEOUT = 30;

    private HashMap<String, Retrofit> mRetrofits = new HashMap<>();

    public Retrofit getRetrofit(String baseUrl){
        Retrofit retrofit = mRetrofits.get(baseUrl);
        if(retrofit == null){
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("X-API-KEY", getAPIKey())
                            .build();
                    return chain.proceed(request);
                }
            });
            okBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder().client(okBuilder.build())
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(ResponseConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        mRetrofits.put(baseUrl, retrofit);
        return retrofit;
    }

    private String getAPIKey() {
        String apiKey = "";
        return apiKey;
    }

    private static class SingletonHolder {
        private static final GpRetrofitManager INSTANCE = new GpRetrofitManager();
    }

    //获取单例
    public static GpRetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
