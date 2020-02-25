package com.idhub.base.net;

import com.google.gson.Gson;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public abstract class BaseRtHttpMethod {


    protected RequestBody convertToJson(HashMap<String, String> paramsMap) {
        Gson gson = new Gson();
        String strEntity = gson.toJson(paramsMap);
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
    }

    protected <T> void tocompose(Flowable<T> o, ResourceSubscriber<T> s) {
        o.compose(SimpleRxUtils.toMain(Schedulers.io()))
                .subscribeWith(s);
    }

    protected <T> void toComposeForTokenResult(Flowable<HttpResult<T>> o, ResourceSubscriber<T> s) {
        o.compose(SimpleRxUtils.handleTokenResult())
                .subscribeWith(s);
    }

}
