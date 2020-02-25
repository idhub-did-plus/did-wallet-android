package com.idhub.wallet.net.collectiables;


import com.idhub.base.net.ApiServiceFactory;
import com.idhub.base.net.BaseRtHttpMethod;
import com.idhub.wallet.net.collectiables.model.Collection;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;


public class CollectionHttpMethod extends BaseRtHttpMethod {

    CollectionService loginService;

    public CollectionHttpMethod() {
        this.loginService = ApiServiceFactory.createAPIService(CollectionContent.BASE_URL, CollectionService.class);
    }

    private static class LoginMethodHolder {
        private static final CollectionHttpMethod INSTANCE = new CollectionHttpMethod();
    }

    //获取单例
    public static CollectionHttpMethod getInstance() {
        return LoginMethodHolder.INSTANCE;
    }

    //藏品列表
    public ResourceSubscriber collections(ResourceSubscriber<List<Collection>> subscriber, String asset_owner) {
        tocompose(loginService.collections(asset_owner), subscriber);
        return subscriber;
    }


}
