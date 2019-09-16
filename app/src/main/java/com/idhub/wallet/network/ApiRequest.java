package com.idhub.wallet.network;

/**
 * Created by testp11 on 2017/4/12.
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.idhub.wallet.net.C;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiRequest {
    private static final String TAG = ApiRequest.class.getName();

    private static final int CONNECT_TIMEOUT = 5;

    private static ApiRequest mInstance;
    private static ApiService mService;

    private Retrofit retrofit;
    private ApiRequest() {
        OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder()
                .addInterceptor(ApiInterceptor.getInstance())
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(C.API_BASE)
                .build();
    }

    public static ApiRequest getInstance() {
        if (mInstance == null) {
            synchronized (ApiRequest.class) {
                if (mInstance == null) {
                    mInstance = new ApiRequest();
                }
            }
        }
        return mInstance;
    }

    //api service接口的代理
    public static ApiService apiService() {
        if (mService == null) {
            synchronized (ApiRequest.class) {
                if (mService == null) {
                    mService = ApiRequest.getInstance().create(ApiService.class);
                }
            }
        }
        return mService;
    }

    //retrofit.create 方法提取
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    //最常用的请求封装 直接请求url
    public <T> Observable<T> getMxApiData(String url, final Class<T> clazz) {
        return apiService().getMxApiData(url)
                .map(new ApiDefaultMapFunc<JsonElement>())
                .map(new Function<JsonElement, T>() {
                    @Override
                    public T apply(JsonElement jsonElement) throws Exception {
                        return new Gson().fromJson(jsonElement, clazz);
                    }
                });
    }

    //最常用的请求封装 带get参数的
    public <T> Observable<T> getMxApiData(String url, Map<String, String> params, final Class<T> clazz) {
        return apiService().getMxApiData(url, params)
                .map(new ApiDefaultMapFunc<JsonElement>())
                .map(new Function<JsonElement, T>() {
                    @Override
                    public T apply(JsonElement jsonElement) throws Exception {
                        return new Gson().fromJson(jsonElement, clazz);
                    }

                });
    }

    //最常用的请求封装
    public <T> Observable<T> postMxApiData(String url, final Class<T> clazz) {
        return apiService().postMxApiData(url)
                .map(new ApiDefaultMapFunc<JsonElement>())
                .map(new Function<JsonElement, T>() {
                    @Override
                    public T apply(JsonElement jsonElement) throws Exception {
                        return new Gson().fromJson(jsonElement, clazz);
                    }
                });
    }

    //最常用的请求封装 带post参数的
    public <T> Observable<T> postMxApiData(String url, Map<String, String> params, final Class<T> clazz) {
        return apiService().postMxApiData(url, params)
                .map(new ApiDefaultMapFunc<JsonElement>())
                .map(new Function<JsonElement, T>() {
                    @Override
                    public T apply(JsonElement jsonElement) throws Exception {
                        return new Gson().fromJson(jsonElement, clazz);
                    }
                });
    }


    //默认的观察者 基本处理
    public static <T> Observable<T> observableHandle(Observable<ApiResultEntity<T>> observable) {
        return observable
                .map(new ApiDefaultMapFunc<T>())
//                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Observable<? extends Throwable> observable) {
//                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
//                            @Override
//                            public Observable<?> call(Throwable throwable) {
//                                if(throwable instanceof TokenInvalidException){
//                                    return refreshTokenWhenTokenInvalid();
//                                }
//                                return Observable.error(throwable);
//                            }
//                        });
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static long tokenChangedTime = 0;

    private final static int REFRESH_TOKEN_VALID_TIME = 30;

    private static Throwable mRefreshTokenError = null;

    private static synchronized Observable<?> refreshTokenWhenTokenInvalid() {
        if (System.currentTimeMillis() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
            return Observable.just(true);
        } else {
            //TODO:
//            XNTApiUseCase.postUsersFresh(AccountManager.getInstance().getLoginInfo().account).subscribe(new Subscriber<POJOAccount>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    mRefreshTokenError = e;
//                }
//
//                @Override
//                public void onNext(POJOAccount pojoAccount) {
//                    if(pojoAccount != null) {
//                        tokenChangedTime = System.currentTimeMillis();
//                        AccountManager.getInstance().setLoginInfo(pojoAccount);
//                    }
//                }
//            });
//            if(mRefreshTokenError != null){
//                return Observable.error(mRefreshTokenError);
//            }else{
//                return Observable.just(true);
//            }
        }
        return null;
    }
}
