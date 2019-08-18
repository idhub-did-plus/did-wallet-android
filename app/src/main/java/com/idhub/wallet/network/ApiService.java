package com.idhub.wallet.network;

import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by testp11 on 2017/4/12.
 */
public interface ApiService {
    @GET
    Observable<ApiResultEntity<JsonElement>> getMxApiData(@Url String url);

    @GET
    Observable<ApiResultEntity<JsonElement>> getMxApiData(@Url String url, @QueryMap Map<String, String> params);

    @Headers("Content-Type:application/json")
    @POST
    Observable<ApiResultEntity<JsonElement>> postMxApiData(@Url String url);

    @Headers("Content-Type:application/json")
    @POST
    Observable<ApiResultEntity<JsonElement>> postMxApiData(@Url String url, @Body Map<String, String> params);
}
