package com.idhub.wallet.greendao;

import org.greenrobot.greendao.async.AsyncOperationListener;

import java.util.List;

public interface ModelDbManager<T> {
    void insertData(T t, AsyncOperationListener asyncOperationListener);

    void insertListData(List<T> list, AsyncOperationListener listener);

    void queryById(long id, AsyncOperationListener listener);

    void queryByKey(String key, AsyncOperationListener listener);

    void queryAll(AsyncOperationListener listener);
}
