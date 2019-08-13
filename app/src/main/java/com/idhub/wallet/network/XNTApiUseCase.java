package com.idhub.wallet.network;


import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by fanwenhao on 2018/4/8.
 */

public class XNTApiUseCase {



//    public static Observable<PojoSecurityCode> postSecurityPhone(String phone, String businessType) {
//        Map map = new HashMap();
//        map.put("phone", phone);
//        map.put("businessType", businessType);
//        return ApiRequest.observableHandle(api().postSecurityPhone(map));
//    }


    private static class XNTApiSingletonHolder {
        static final XNTApi instance = ApiRequest.getInstance().create(XNTApi.class);
    }

    public static XNTApi api() {
        return XNTApiSingletonHolder.instance;
    }

    public interface XNTApi {
//        @Headers("Content-Type:application/json")
//        @POST(C.API_CONFIG)
//        Observable<ApiResultEntity<POJOConfig>> postConfig();
    }
}
