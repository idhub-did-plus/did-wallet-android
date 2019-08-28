package com.idhub.wallet.greendao;

import com.idhub.wallet.App;
import com.idhub.wallet.greendao.db.DaoSession;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.me.information.entity.UploadFileEntity;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.util.List;

public class UploadFileDbManager implements ModelDbManager<UploadFileEntity> {

    @Override
    public void insertData(UploadFileEntity uploadFileEntity, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(asyncOperationListener);
        asyncSession.insertOrReplace(uploadFileEntity);
    }

    @Override
    public void insertListData(List<UploadFileEntity> list, AsyncOperationListener listener) {
        if (list != null && list.size() > 0) {
            AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
            asyncSession.setListenerMainThread(listener);
            asyncSession.insertInTx(UploadFileEntity.class, list);
        }
    }

    @Override
    public void queryById(long id, AsyncOperationListener listener) {

    }

    @Override
    public void queryByKey(String key, AsyncOperationListener listener) {

    }

    @Override
    public void queryAll(AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        asyncSession.loadAll(UploadFileEntity.class);
    }
}
